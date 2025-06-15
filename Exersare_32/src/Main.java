import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static Connection connect(){
        Connection conn=null;
        String url ="jdbc:sqlite:Date\\facultate.db";
        try{
            conn= DriverManager.getConnection(url);
            System.out.println("Conectat");
        } catch (SQLException e) {
            System.out.println("Eroare la conectare "+e.getMessage());
        }
        return conn;
    }
    public static void citireBD(Map<Integer,Specializari> spec){
        String comanda="SELECT * FROM specializari";
        try(Connection con=connect(); Statement statement=con.createStatement(); ResultSet rs=statement.executeQuery(comanda)){
            while (rs.next()){
                int cod=rs.getInt("cod");
                String denumire=rs.getString("denumire");
                int locuri=rs.getInt("locuri");
                spec.put(cod,new Specializari(cod,denumire,locuri));
            }
        }catch (SQLException e){
            System.out.println("Eroare la citirea tabelei "+e.getMessage());
        }
    }
    public static Map<Integer,Specializari> specializari=new HashMap<>();
    public static void main(String[] args)throws Exception {
        citireBD(specializari);
        try(BufferedReader br=new BufferedReader(new FileReader("Date\\inscrieri.txt"))){
            String linie;
            while ((linie=br.readLine())!=null){
                String[] tokens=linie.split(",");
                long cnp=Long.parseLong(tokens[0]);
                String nume=tokens[1];
                float medie=Float.parseFloat(tokens[2]);
                int cod=Integer.parseInt(tokens[3]);
                specializari.get(cod).adaugaStudent(new Student(cnp,nume,medie,cod));
            }
        }catch (IOException e){
            System.out.println("Eroare la citirea fisierului .txt "+e.getMessage());
        }
        int locuri=0;
        for(Specializari spec: specializari.values()){
            locuri+= spec.getLocuri();
        }
        System.out.println("Cerinta: Sa se afiseze la consola numarul total de locuri disponibile la facultate "+locuri);
        System.out.println("Cerinta: Sa se afiseze la consola lista specializarilor care, in urma inscrierilor, mai au cel putin 10 de locuri");
        for(Specializari spec: specializari.values()){
            if(spec.getLocuriDisponibile()>=10){
                System.out.println(spec);
            }
        }
        System.out.println("Cerinta: Sa se salveze in fisierul json numarul de inscrieri si nota medie pentru fiecare specializare");
        JSONArray jsonArray=new JSONArray();
        for(Specializari spec: specializari.values()){
            JSONObject obj=new JSONObject();
            obj.put("codSpecializare",spec.getCod());
            obj.put("denumire",spec.getDenumire());
            obj.put("numar_inscrieri",(spec.getLocuri()-spec.getLocuriDisponibile()));
            double medie=0.0;
            for (Student stud: spec.studenti){
                medie+=stud.notaBac;
            }
            medie=medie/spec.studenti.size();
            obj.put("medie",medie);
            jsonArray.put(obj);
        }
        try(FileWriter fw=new FileWriter("Date\\inscrieri_specializari.json")){
            fw.write(jsonArray.toString(4));

        } catch (IOException e) {
            System.out.println("Eroare la scrierea fisierului .json "+e.getMessage());
        }
        System.out.println("Cerinta: Sa se implementeze functionalitatile de server TCP/IP");
        final int PORT=3929;
        new Thread(()->{
            try(
                    var server=new ServerSocket(PORT);
                    var socket=server.accept();
                    var in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    var out=new PrintWriter(socket.getOutputStream(),true);
                    ){
                String denumire=in.readLine();
                System.out.println("SERVER: AM PRIMIT "+denumire);
                for(Specializari spec:specializari.values()){
                    if(spec.getDenumire().equals(denumire)){
                        out.println(spec.getLocuriDisponibile());
                    }
                }
            }catch (Exception e){e.printStackTrace();}
        }
        ).start();
        Thread.sleep(500);
        try(
                var socket=new Socket("localhost",PORT);
                var out=new PrintWriter(socket.getOutputStream(),true);
                var in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ){
            out.println("Informatica Economica");
            System.out.println("CLIENT: AM PRIMIT "+in.readLine());
        }catch (Exception e){e.printStackTrace();}
    }
}