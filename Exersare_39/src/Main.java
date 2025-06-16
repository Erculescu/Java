import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.*;

import static java.lang.Float.compare;

public class Main {
    public static Connection connect(){
        Connection conn=null;
        String url="jdbc:sqlite:Date\\raspunsuri_studenti.db";
        try{
            conn= DriverManager.getConnection(url);
            System.out.println("Conectat");
        }catch (SQLException e){
            System.out.println("Eroare la conectare "+e.getMessage());
        }
        return conn;
    }
    public static void citireDB(List<Raspunsuri> rasp){
        String comanda="SELECT * FROM Raspunsuri";
        try(Connection connection=connect(); Statement statement=connection.createStatement();
            ResultSet rs=statement.executeQuery(comanda);){
            while (rs.next()){
                String nume=rs.getString("nume_student");
                int cod=rs.getInt("cod_intrebare");
                String raspuns=rs.getString("raspuns");
                rasp.add(new Raspunsuri(nume,cod,raspuns));
            }
        }catch (SQLException e){
            System.out.println("Eroare la citirea din baza de date "+e.getMessage());
        }
    }

    public static double calculeazaNota(String nume,Map<Integer,Intrebare> intrebari,List<Raspunsuri> raspunsuri){
        double nota=0.0;
        for(Raspunsuri r:raspunsuri){

            if(r.nume.equals(nume)){
                Intrebare i=intrebari.get(r.cod);
                if(i!=null&&r.rasp.equals(i.raspuns)){
                    nota+=i.getPunctaj();
                }
            }
        }
        double punctaj_max=0;
        for(Intrebare i:intrebari.values()){
            punctaj_max+=i.getPunctaj();
        }
        nota=(nota*10)/punctaj_max;
        return nota;
    }


    public static Map<Integer,Intrebare> intrebari=new HashMap<>();
    public static List<Raspunsuri> raspunsuri=new ArrayList<>();
    public static void main(String[] args) throws Exception{
        try(BufferedReader br=new BufferedReader(new FileReader("Date\\intrebari.txt"))){
            String linie;
            while((linie=br.readLine())!=null){
                String[] tokens=linie.split(",");
                int cod=Integer.parseInt(tokens[0]);
                String text=tokens[1];
                String raspuns=tokens[2];
                Double punctaj=Double.parseDouble(tokens[3]);
                intrebari.put(cod,new Intrebare(cod,text,raspuns,punctaj));
            }
        }catch (IOException e){
            System.out.println("Eroare la citirea fiserului .txt "+e.getMessage());
        }
        intrebari.values().stream().sorted((i1,i2)->compare(i1.id,i2.id)).forEach(System.out::println);
        citireDB(raspunsuri);
        HashSet<String> studenti=new HashSet<>();
        for(Raspunsuri r:raspunsuri){
            studenti.add(r.getNume());
        }
        System.out.println("Lista studentilor care au participat: "+studenti);
        Map<String,Double> hartaStudenti=new HashMap<>();
        for(String s:studenti){
            double nota=calculeazaNota(s,intrebari,raspunsuri);
            hartaStudenti.put(s,nota);
        }
        try(BufferedWriter bw=new BufferedWriter(new FileWriter("Date\\date_stud.txt"))){
            bw.write("Nume student      Nota obtinuta");
            bw.newLine();
            for(Map.Entry<String,Double> intrare: hartaStudenti.entrySet()){
                bw.write(intrare.getKey()+"     "+intrare.getValue());
                bw.newLine();
            }
        }catch (IOException e){
            System.out.println("Eroare la scrierea in fisier "+e.getMessage());
        }
        final int PORT=3929;
        new Thread(()->{
            try(
                    var server=new ServerSocket(PORT);
                    var socket=server.accept();
                    var in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    var out=new PrintWriter(socket.getOutputStream(),true);
                    ){
                var cod=in.readLine();
                System.out.println("SERVER: AM PRIMIT "+cod);
                out.println(intrebari.get(Integer.parseInt(cod)).text);
            }catch (Exception e){e.printStackTrace();}
        }).start();
        Thread.sleep(500);
        try(
                var socket=new Socket("localhost",PORT);
                var out=new PrintWriter(socket.getOutputStream(),true);
                var in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ){
            out.println("5");
            System.out.println("CLIENT: AM PRIMIT "+in.readLine());
        }catch (Exception e){e.printStackTrace();}
    }
}