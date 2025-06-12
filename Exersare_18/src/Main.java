import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static Connection connect(){
        String url="jdbc:sqlite:baza.db";
        Connection conn=null;
        try{
            conn= DriverManager.getConnection(url);
            System.out.println("Conectat la baza de date");
        }catch (SQLException e){
            System.out.println("Conexiune esuata: "+e.getMessage());
        }
        return conn;
    }
    public static void creareTabela(){
        String sql1="CREATE TABLE IF NOT EXISTS Produse("+"codProdus INTEGER PRIMARY KEY, "+"denumireProdus TEXT NOT NULL, "
                +"pret REAL, "+"cantitate integer"+");";
        String sql2="CREATE TABLE IF NOT EXISTS Tranzactii("+"codProdus INTEGER NOT NULL, "+"cantitate INTEGER NOT NULL, "+"tip TEXT"+");";

        try(Connection conn=connect(); Statement statement=conn.createStatement()){
            statement.execute(sql1);
            statement.execute(sql2);
            System.out.println("Produs introdus in baza de date");
        }catch (Exception e){
            System.out.println("Eroare la crearea tabelei "+e.getMessage());
        }
    }
    public static void inserareProdus(int codProdus,String denumireProdus,double pret,int cantitate){
        String sql="INSERT INTO Produse (codProdus, denumireProdus, pret, cantitate) VALUES(?,?,?,?)";
        try(Connection connection=connect();
            PreparedStatement preparedStatement=connection.prepareStatement(sql)){
            preparedStatement.setInt(1,codProdus);
            preparedStatement.setString(2,denumireProdus);
            preparedStatement.setDouble(3,pret);
            preparedStatement.setInt(4,cantitate);
            preparedStatement.executeUpdate();
            System.out.println("Produs introdus in baza de date.");

        }catch (Exception e){
            System.out.println("Eroare la inserarea produsului "+e.getMessage());
        }
    }

    public static void inserareTranzactie(int codProdus, int cantitate,String tip){
        String sql="INSERT INTO Tranzactii (codProdus, cantitate, tip) VALUES(?,?,?)";
        try(Connection connection=connect();PreparedStatement preparedStatement=connection.prepareStatement(sql)){
            preparedStatement.setInt(1,codProdus);
            preparedStatement.setInt(2,cantitate);
            preparedStatement.setString(3,tip);
            preparedStatement.executeUpdate();
            System.out.println("Tranzactie introdusa in baza de date.");
        }catch (Exception e){
            System.out.println("Eroare la inserarea tranzactiei "+e.getMessage());
        }
    }
    private static Map<Integer,Produs> produse=new HashMap<>();
    public static void main(String[] args) throws  Exception{

        try(var fisier=new BufferedReader(new FileReader("produse.txt"))){
            produse=fisier.lines().map(Produs::new).collect(Collectors.toMap(p->p.getCodProdus(),p->p));
        }catch (IOException e){
            e.printStackTrace();
        }
        try(var fisier=new FileReader("tranzactii.json")){
            var jtranz=new JSONArray(new JSONTokener(fisier));
            for(int i=0;i< jtranz.length();i++){
                var jTran=jtranz.getJSONObject(i);
                var tranzactie=new Tranzactie(jTran.getInt("codProdus"),jTran.getInt("cantitate"),jTran.getString("tip"));
                produse.get(tranzactie.getCodProdus()).adaugaTranzactie(tranzactie);

            }
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("Cerinta 1: "+produse.size());
        System.out.println("Cerinta 2:");
        produse.values().stream().sorted((p1,p2)->p1.getDenumire().compareTo(p2.getDenumire())).forEach(System.out::println);
        System.out.println("Cerinta 3:");
        try(var fisier=new PrintWriter(new FileWriter("lista.txt")))
        {produse.values().stream().sorted((p1,p2)->Integer.compare(p1.getNrTranz(),p2.getNrTranz()))
                .forEach(p->fisier.println(p.getDenumire()+", "+p.getNrTranz()));
        }catch (IOException e){
            e.printStackTrace();
        }
        var valoare=produse.values().stream().mapToDouble(Produs::getValoare).sum();
        System.out.println("Cerinta 4: "+valoare);
        System.out.println("Cerinta Suplimentara: INTRODUCETI DATELE INTRO BAZA DE DATE SQLITE");
        creareTabela();
        for(Produs produs:produse.values()){
            inserareProdus(produs.codProdus, produs.denumire,produs.pret,produs.cantitate);
            for(Tranzactie tranzactie: produs.tranzactii){
                if(tranzactie.getCodProdus()== produs.codProdus){
                    inserareTranzactie(tranzactie.codProdus, tranzactie.cantitate, tranzactie.tip);
                }
            }
        }
        System.out.println("Cerinta 5: ");
        final int PORT=3929;
        new Thread(()->{
            try(var server=new ServerSocket(PORT);
            var socket=server.accept();
            var in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            var out=new PrintWriter(socket.getOutputStream(),true);)
        {
          var codProdus=Integer.parseInt(in.readLine());
            System.out.println("SERVER: AM PRIMIT "+codProdus);
            out.println(produse.get(codProdus).getValoare());
        }catch (Exception e){e.printStackTrace();}
        }).start();
        Thread.sleep(500);
        try(
                var socket=new Socket("localhost",PORT);
                var out=new PrintWriter(socket.getOutputStream(),true);
                var in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ){
            out.println(3);
            System.out.println("CLIENT: am primit "+in.readLine());
        }catch (Exception e){e.printStackTrace();}
    }

}