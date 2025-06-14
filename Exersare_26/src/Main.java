import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.HashMap;
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
    public static void creareTabele(){
        String comnada1="CREATE TABLE IF NOT EXISTS Vehicule("+"id INTEGER PRIMARY KEY AUTOINCREMENT,"+"vin TEXT NOT NULL,"+" proprietar TEXT,"+"marca TEXT,"+"model TEXT"+");";
        String comanda2="CREATE TABLE IF NOT EXISTS Operatiuni("+"id INTEGER PRIMARY KEY AUTOINCREMENT,"+"operatiune TEXT,"+"pret REAL"+");";
        try(Connection connection=connect(); Statement statement=connection.createStatement()){
            statement.execute(comnada1);
            statement.execute(comanda2);
            System.out.println("Tabele create");
        }catch (SQLException e){
            System.out.println("Eroare la creare "+e.getMessage());
        }

    }
    public static void inserareVehicul(String vin,String proprietar,String marca,String model){
        String comanda="INSERT INTO Vehicule(vin,proprietar,marca,model) VALUES(?,?,?,?);";
        try(Connection connection=connect(); PreparedStatement preparedStatement=connection.prepareStatement(comanda)){
            preparedStatement.setString(1,vin);
            preparedStatement.setString(2,proprietar);
            preparedStatement.setString(3,marca);
            preparedStatement.setString(4,model);
            preparedStatement.executeUpdate();
            System.out.println("Vehicul inserat");
        }catch (SQLException e){
            System.out.println("Eroare la inserare "+e.getMessage());
        }
    }
    public static void inserareOperatiune(String operatiune, Double pret){
        String comanda="INSERT INTO Operatiuni(operatiune,pret) VALUES(?,?);";
        try(Connection connection=connect();PreparedStatement preparedStatement=connection.prepareStatement(comanda)){
            preparedStatement.setString(1,operatiune);
            preparedStatement.setDouble(2,pret);
            System.out.println("Operatiune inserata");
        }catch (SQLException e){
            System.out.println("Eroare la inserare operatiune "+e.getMessage());
        }
    }
    public static Map<String,Vehicul> vehicule=new HashMap<>();
    public static void main(String[] args) throws Exception {
        try(BufferedReader br=new BufferedReader(new FileReader("vehicule.txt"))){
            String linie;
            while((linie= br.readLine())!=null){
                String[] tokens=linie.split(",");
                int cod=Integer.parseInt(tokens[0]);
                String vin=tokens[1];
                String proprietar=tokens[2];
                String marca=tokens[3];
                String model=tokens[4];
                vehicule.put(vin,new Vehicul(cod,vin,proprietar,marca,model));
            }
        } catch (Exception e) {
            System.out.println("Eroare la citirea fisierului "+e.getMessage());
        }
        try(FileReader fw=new FileReader("raport_devize.json")){
            var jvehicule=new JSONArray(new JSONTokener(fw));
            for(int i=0;i<jvehicule.length();i++){
                var vjev=jvehicule.getJSONObject(i);
                String vin=vjev.getString("vin");
                double pret=vjev.getDouble("pret");
                String operatiuni=vjev.getString("operatiuni");
                vehicule.get(vin).adaugaService(new Service(pret,operatiuni));
            }
        }catch (IOException e){
            System.out.println("Eroare la citirea fisierului json "+e.getMessage());
        }
        System.out.println("Cerinta: Afisati nr de vehicule stocate: "+vehicule.size());
        System.out.println("Cerinta: Afisati vehiculele in ordine descrescatoare a sumei investite in servisari: ");
        vehicule.values().stream().sorted((v1,v2)->compare(v2.calculPret(),v1.calculPret())).forEach(System.out::println);
        System.out.println("Cerinta: Afisati proprietarii care au mai mult de o masina: ");
        Map<String,Integer> proprietari=new HashMap<>();
        for(Vehicul v: vehicule.values()){
            proprietari.put(v.getProprietar(),proprietari.getOrDefault(v.getProprietar(),0)+1);
        }
        for(String prop: proprietari.keySet()){
            if(proprietari.get(prop)>1){
                System.out.println(prop);
            }
        }
        System.out.println("Cerinta: Stocati datele despre proprietari si vehiculele lor intr-un fisier .txt:");
        try(BufferedWriter bw=new BufferedWriter(new FileWriter("raport.txt"))){
        for(Vehicul v: vehicule.values()){
            bw.write(v.getVIN()+" "+v.getProprietar());

            for(Service service:v.revizii){
                bw.newLine();bw.write("\t");
                bw.write(service.getOperatiuni()+" "+service.getPret());

            }
            bw.newLine();bw.newLine();
        }
        }catch (IOException e){
            System.out.println("Eroare la crearea fisierului .txt "+e.getMessage());
        }
        System.out.println("Cerinta: Creati o baza de date unde sa stocati vehiculele si operatiunile");
        creareTabele();
        for(Vehicul v: vehicule.values()){
            inserareVehicul(v.VIN,v.proprietar,v.marca,v.model);
            for(Service service:v.revizii){
                inserareOperatiune(service.operatiuni, service.pret);
            }
        }
        System.out.println("Imprementati o interfata UDP/IP");
        final int PORT=3929;
        new Thread(()->{
            try(
            var server=new ServerSocket(PORT);
            var socket=server.accept();
            var in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            var out=new PrintWriter(socket.getOutputStream(),true);){
                var vin=in.readLine();
                System.out.println("SERVER: AM PRIMIT "+vin);
                out.println(vehicule.get(vin));
            }catch (Exception e){e.printStackTrace();}
        }
        ).start();
        Thread.sleep(500);
        try(
                var socket=new Socket("localhost",PORT);
                var out=new PrintWriter(socket.getOutputStream(),true);
                var in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ){
            out.println("WAUZZ123");
            System.out.println("CLIENT: AM PRIMIT "+in.readLine());
        }catch (Exception e){e.printStackTrace();}
    }
}