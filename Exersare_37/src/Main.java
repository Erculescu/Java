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
        String url="jdbc:sqlite:Date\\facultate.db";
        try{
            conn= DriverManager.getConnection(url);
            System.out.println("Conectat");
        } catch (SQLException e) {
            System.out.println("Eroare la conectare "+e.getMessage());
        }
        return conn;
    }

    public static void citireDB(Map<Integer,specializare> harta){
        String comanda="SELECT * FROM specializari";
        try(
                Connection connection=connect(); Statement statement=connection.createStatement();
                ResultSet rs=statement.executeQuery(comanda);
                ){
            while (rs.next()){
                int cod=rs.getInt("cod");
                String denumire=rs.getString("denumire");
                int locuri=rs.getInt("locuri");
                harta.put(cod,new specializare(cod,denumire,locuri));
            }
        }catch (SQLException e){
            System.out.println("Eroare la citirea datelor din DB "+e.getMessage());
        }
    }

    public static Map<Integer,specializare> specializari=new HashMap<>();
    public static void main(String[] args)throws Exception {
        citireDB(specializari);
        try(BufferedReader br=new BufferedReader(new FileReader("Date\\inscrieri.txt"))){
            String linie;
            while((linie= br.readLine())!=null){
                String[] tokens=linie.split(",");
                long cod_cand=Long.parseLong(tokens[0]);
                String nume=tokens[1];
                Double notaBac=Double.parseDouble(tokens[2]);
                int optiune=Integer.parseInt(tokens[3]);
                specializari.get(optiune).adaugaStudent(new Student(cod_cand,nume,notaBac,optiune));
            }
        }catch (IOException e){
            System.out.println("Eroare la citirea fisierului .txt "+e.getMessage());
        }

        int locuriRamase=0;
        for(specializare s:specializari.values()){
            locuriRamase+=s.locuriDisponibile;
        }
        System.out.println("Sa se afiseze nr de locuri ramase disponibile "+locuriRamase);
        System.out.println("Sa se afiseze specializarile care mai au cel putin 10 locuri disponibile ");
        for(specializare s: specializari.values()){
            if(s.locuriDisponibile>=10){
                System.out.println(s.cod+" "+s.denumire+" "+s.locuriDisponibile);
            }
        }
        JSONArray jsonArray=new JSONArray();
        for(specializare s: specializari.values()){
            JSONObject obj=new JSONObject();
            obj.put("cod",s.cod);
            obj.put("denumire",s.denumire);
            obj.put("inscrieri",(s.getLocuri()-s.locuriDisponibile));
            obj.put("medie",s.getMedie());
            jsonArray.put(obj);
        }
        try(FileWriter fw=new FileWriter("Date\\inscrieri_specializari.json")){
            fw.write(jsonArray.toString(4));
        }catch (IOException e){
            System.out.println("Eroare la scrierea fisierului .json "+e.getMessage());
        }
        final int PORT=3929;
        new Thread(()->{
            try(
                    var server=new ServerSocket(PORT);
                    var sokcet=server.accept();
                    var in=new BufferedReader(new InputStreamReader(sokcet.getInputStream()));
                    var out=new PrintWriter(sokcet.getOutputStream(),true);
                    ){
                String spec=in.readLine();
                System.out.println("SERVER: AM PRIMIT "+spec);
                for(specializare s: specializari.values()){
                    if(s.denumire.equals(spec)){
                        out.println((s.locuriDisponibile));
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        ).start();
        Thread.sleep(500);
        try(
                var socket=new Socket("localhost",PORT);
                var out=new PrintWriter(socket.getOutputStream(),true);
                var in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ){
            out.println("Statistica");
            System.out.println("CLIENT: AM PRIMIT "+in.readLine());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}