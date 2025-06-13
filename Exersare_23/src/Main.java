import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Double.compare;

public class Main {
    public static Connection connect(){
        String url="jdbc:sqlite:baza.db";
        Connection conn=null;
        try{
            conn= DriverManager.getConnection(url);
            System.out.println("Conectat!");
        }catch (SQLException e ){
            System.out.println("Eroare la conectare "+e.getMessage());
        }
        return conn;
    }
    public static void creareTabele(){
        String comanda1="CREATE TABLE IF NOT EXISTS Camere("+"codCamera INTEGER PRIMARY KEY,"+"modelCamera TEXT NOT NULL,"+"pretPerOperatiune REAL,"+"nrCamereInService INTEGER"+");";
        String comanda2="CREATE TABLE IF NOT EXISTS Operatiuni("+"modelCamera TEXT ,"+"cantitate INTEGER NOT NULL,"+"statusCamera TEXT NOT NULL"+");";
        try(Connection con=connect(); Statement statement=con.createStatement()){
            statement.execute(comanda1);
            statement.execute(comanda2);
            System.out.println("Tabele create");
        }catch (SQLException e){
            System.out.println("Eroare la crearea tabelelor "+e.getMessage());
        }
    }
    public static void inserareCamera(int codCamera,String modelCamera,double pretPerOperatiune,int nrCamereInService){
        String comanda="INSERT INTO Camere(codCamera,modelCamera,pretPerOperatiune,nrCamereInService) VALUES(?,?,?,?)";
        try(Connection con=connect();
            PreparedStatement preparedStatement=con.prepareStatement(comanda)){
            preparedStatement.setInt(1,codCamera);
            preparedStatement.setString(2,modelCamera);
            preparedStatement.setDouble(3,pretPerOperatiune);
            preparedStatement.setInt(4,nrCamereInService);
            preparedStatement.executeUpdate();
            System.out.println("Camera inserata");
        }catch (SQLException e){
            System.out.println("Eroare la inserarea camerei "+e.getMessage());
        }
    }
    public static void inserareOperatiune(String modelCamera,int cantitate,String statusCamera){
        String comanda="INSERT INTO Operatiuni(modelCamera,cantitate,statusCamera) VALUES(?,?,?)";
        try(Connection con=connect();
        PreparedStatement preparedStatement=con.prepareStatement(comanda)){
            preparedStatement.setString(1,modelCamera);
            preparedStatement.setInt(2,cantitate);
            preparedStatement.setString(3,statusCamera);
            preparedStatement.executeUpdate();
            System.out.println("Operatiune inserata");
        }catch (SQLException e){
            System.out.println("Eroare la inserarea operatiunii "+e.getMessage());
        }
    }
    public static Map<String,Camere> camere=new HashMap<>();
    public static void main(String[] args) throws Exception{
        try(BufferedReader br=new BufferedReader(new FileReader("camere.csv"))){
            String linie;
            while((linie=br.readLine())!=null){
                int id=Integer.parseInt(linie.split(",")[0]);
                String nume=linie.split(",")[1];
                double pretOp=Double.parseDouble(linie.split(",")[2]);
                camere.put(nume,new Camere(id,nume,pretOp));
            }
        }catch (IOException e){
            System.out.println("Eroare la citirea fisierului .csv "+e.getMessage());
        }
        try(FileReader fisier=new FileReader("Raport Service.json")){
            var joperatiuni=new JSONArray(new JSONTokener(fisier));
            for(int i=0;i<joperatiuni.length();i++){
                var jop=joperatiuni.getJSONObject(i);
                String camera=jop.getString("Model Camera");
                Operatiuni op=new Operatiuni(jop.getInt("Cantitate"),jop.getString("Status"));
                camere.get(camera).adaugaOperatiune(op);
            }
        }catch(IOException e){
            System.out.println("Eroare la citirea fisierului .json "+e.getMessage());
        }
        int contor=0;
        for(Camere c:camere.values()){
            if(c.nrCamere>0){
                contor+=c.nrCamere;
            }
        }
        System.out.println("Cerinta: Afisare nr camere in service: "+contor);
        System.out.println("Cerinta: Afisare camere in oridne descrescatoare a pretului de reparatie ");
        camere.values().stream().sorted((c1,c2)->compare(c2.pretOperatiune,c1.pretOperatiune)).forEach(System.out::println);
        System.out.println("Cerinta: Creare fisier .txt in care se vor stoca camerele si operatiunile");
        try(BufferedWriter bw=new BufferedWriter(new FileWriter("raport.txt"))){
            for(Camere c:camere.values()){
                bw.write("Id camera "+c.id+" Nume camera "+c.nume+" pret per servisare "+c.pretOperatiune+" log operatiuni: ");
                bw.newLine();bw.write("\t");
                for(Operatiuni op:c.opertiuni){
                    bw.write(op.toString());
                    bw.newLine();bw.write("\t");
                }
                bw.newLine();
            }
        }catch (IOException e){
            System.out.println("Eroare la scrierea fisierului .txt "+e.getMessage());
        }
        double valoareNerambursata=0;
        for(Camere c:camere.values()){
            valoareNerambursata+=c.pretOperatiune*c.nrCamere;
        }
        System.out.println("Cerinta: Afisare valoarea operatiunilor in curs "+valoareNerambursata);
        System.out.println("Cerinta: Stocare date intr-o baza de date");
        creareTabele();
        for(Camere c:camere.values()){
            inserareCamera(c.getId(),c.getNume(),c.getPretOperatiune(),c.getNrCamere());
            for(Operatiuni op:c.opertiuni){
                inserareOperatiune(c.getNume(),op.getCantitate(),op.getStatus());
            }
        }
        System.out.println("Cerinta: Realizare Conexiune de tip TCP/IP");
        final int PORT=3929;
        new Thread(()->{
            try(var server=new ServerSocket(PORT);
            var socket=server.accept();
            var in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            var out=new PrintWriter(socket.getOutputStream(),true);){
                var numeCamera=in.readLine();
                System.out.println("SERVER: AM PRIMIT "+numeCamera);
                out.println(camere.get(numeCamera));
            }catch (Exception e){
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(500);
        try(
                var socket=new Socket("localhost",PORT);
                var out=new PrintWriter(socket.getOutputStream(),true);
                var in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ){
            out.println("Minolta Dynax 500si");
            System.out.println("CLIENT: AM PRIMIT "+in.readLine());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}