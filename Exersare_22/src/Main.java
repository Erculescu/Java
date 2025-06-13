import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Double.compare;

public class Main {
    public static Connection connect(){
        String url="jdbc:sqlite:baza.db";
        Connection conn=null;
        try{
            conn= DriverManager.getConnection(url);
            System.out.println("Conectat");
        }catch (SQLException e){
            System.out.println("Eroare la conectare "+e.getMessage());
        }
        return conn;
    }
    public static void creareTabela(){
        String comanda="CREATE TABLE IF NOT EXISTS vehicule("+"VIN TEXT PRIMARY KEY,"+"PUTERE INTEGER,"+"PRET REAL NOT NULL"+");";
        try(Connection connection=connect(); Statement statement=connection.createStatement()){
            statement.execute(comanda);
            System.out.println("Tabela creata");
        }catch (SQLException e){
            System.out.println("Eroare la crearea tabelei "+e.getMessage());
        }
    }
    public static void inserareTabela(String VIN,int putere,double pret){
        String comanda="INSERT INTO vehicule(VIN,PUTERE,PRET) VALUES(?,?,?)";
        try(Connection connection=connect(); PreparedStatement preparedStatement=connection.prepareStatement(comanda)){
            preparedStatement.setString(1,VIN);
            preparedStatement.setInt(2,putere);
            preparedStatement.setDouble(3,putere);
            preparedStatement.executeUpdate();
            System.out.println("Vehicul inserat in baza de date");
        }catch (SQLException e){
            System.out.println("Eroare la inserarea tabelei "+e.getMessage());
        }
    }
    public static List<Reprezentanta> reprezentante=new ArrayList<>();
    public static void main(String[] args)throws Exception {
        try(BufferedReader br=new BufferedReader(new FileReader("reprezentanta.csv"))){
            String linie;
            while((linie=br.readLine())!=null){
                String cui=linie.split(",")[0];
                String denumire=linie.split(",")[1];
                reprezentante.add(new Reprezentanta(cui,denumire));
            }
        }catch (IOException e){
            System.out.println("Eroare la citirea fisierului .txt "+e.getMessage());
        }
        for(Reprezentanta r:reprezentante){
        try(var fisier=new FileReader("vehicule.json")){
            var jveh=new JSONArray(new JSONTokener(fisier));
            for(int i=0;i<jveh.length();i++){
            var jv=jveh.getJSONObject(i);
            var vehicul=new Vehicul(jv.getString("VIN"), jv.getInt("PUTERE"),jv.getDouble("PRET") );
            r.vehicule.add(vehicul);
            }
        }catch (IOException e){
            System.out.println("Eroare la citirea fisierului .json "+e.getMessage());
        }
        }
        System.out.println("Cerinta: Scrieti cate vehicule are fiecare reprezentanta: ");
        for(Reprezentanta r:reprezentante){
            System.out.println(r.denumire+" "+r.vehicule.size());
        }
        System.out.println("Cerinta: Ordonati vehiculele din fiecare reprezentanta in ordinea descrescatoare a pretului.");
        for(Reprezentanta r:reprezentante){
            r.vehicule.sort((v1, v2)->compare(v2.putere,v1.putere));
        }
        for(Reprezentanta r: reprezentante){
            System.out.println(r.denumire);
            for(Vehicul v:r.vehicule){
                System.out.println(v.getVIN()+" "+v.getPutere());
            }
        }
        System.out.println("Cerinta: Afisati valoarea stocului pentru fiecare reprezentanta: ");
        for(Reprezentanta r: reprezentante){
            double val=0;
            for(Vehicul v:r.vehicule){
                val+=v.getPret();
            }
            System.out.println(r.denumire+" "+r.vehicule.size()+" vehicule cu valoarea "+val);
        }
        for(Reprezentanta r:reprezentante){
            r.vehicule.sort((v1, v2)->compare(v2.pret,v1.pret));
        }
        try(BufferedWriter bw=new BufferedWriter(new FileWriter("raport.txt"))){
            for(Reprezentanta r: reprezentante){
                bw.write(r.denumire+" "+r.vehicule.size()+" vehicule: ");
                for(Vehicul v:r.vehicule){
                    bw.write(v.VIN+" "+v.putere+" "+v.pret);
                    bw.newLine();
                    bw.write("\t\t\t\t");
                }
                bw.newLine();
            }
        }catch (IOException e){
            System.out.println("Eroare la scrierea fisierului .txt "+e.getMessage());
        }
        System.out.println("Cerinta: Introduceti toate vehiculele intr-o baza de date");
        creareTabela();
        for(Reprezentanta r:reprezentante){
            for(Vehicul v:r.vehicule){
                inserareTabela(v.getVIN(),v.getPutere(),v.getPret());
            }
        }
        Map<String,Vehicul> vehicule=new HashMap<>();
        for(Reprezentanta r:reprezentante){
            for(Vehicul v:r.vehicule){
                vehicule.put(v.getVIN(),v);
            }
        }
        System.out.println("Cerinta: Implementati o interfata TCP/IP");
        final int port=3929;
        new Thread(()->{
            try(
                    var server=new ServerSocket(port);
                    var socket=server.accept();
                    var in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    var out=new PrintWriter(socket.getOutputStream(),true);
                    ){
                var VIN=in.readLine();
                System.out.println("SERVER: AM PRIMIT "+VIN);
                out.println(vehicule.get(VIN));
            }catch (Exception e){
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(500);
        try(
                var socket=new Socket("localhost",port);
                var out=new PrintWriter(socket.getOutputStream(),true);
                var in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ){
            out.println("12345444");
            System.out.println("CLIENT: AM PRIMIT "+in.readLine());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}