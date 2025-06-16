import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static Connection connect(){
        Connection conn=null;
        String url="jdbc:sqlite:Date\\bursa.db";
        try{
            conn= DriverManager.getConnection(url);
            System.out.println("Conectat");
        }catch (SQLException e){
            System.out.println("Eroare la conectare "+e.getMessage());
        }
        return conn;
    }
    public static void citireBD(Map<Integer,Persoana> hpers){
        String comanda="SELECT * FROM Persoane";
        try(
                Connection connection=connect(); Statement statement=connection.createStatement();
                ResultSet rs=statement.executeQuery(comanda);
                ){
            while(rs.next()){
                int cod=rs.getInt("Cod");
                String cnp= rs.getString("CNP");
                String nume=rs.getString("Nume");
                hpers.put(cod,new Persoana(cod,cnp,nume));
            }
        }catch (SQLException e){
            System.out.println("Eroare la citirea bazei de date");
        }
    }

    public static Map<Integer,Persoana> persoane=new HashMap<>();
    public static void main(String[] args)throws Exception {
        citireBD(persoane);
        try(BufferedReader br=new BufferedReader(new FileReader("Date\\bursa_tranzactii.txt"))){
            String linie;
            while((linie=br.readLine())!=null){
                String[] tokens=linie.split(",");
                int cod=Integer.parseInt(tokens[0]);
                String simbol=tokens[1];
                String tip=tokens[2];
                int cantitate=Integer.parseInt(tokens[3]);
                float pret=Float.parseFloat(tokens[4]);
                persoane.get(cod).adaugaTranzactie(new Tranzactie(cod,simbol,tip,cantitate,pret));
            }
        }catch (IOException e){
            System.out.println("Eroare la citirea fisierului .txt "+e.getMessage());
        }
        System.out.println("Afisati clientii rezidenti (cnp-urile nu incep cu 8 sau 9) ");
        for(Persoana persoana:persoane.values()){
            if(!(persoana.cnp.startsWith("8")||persoana.cnp.startsWith("9"))){
                System.out.println(persoana.nume+" "+persoana.cnp);
            }
        }
        System.out.println("Afisati pentru fiecare simbol, numarul de tranzactii efectuate");
        Map<String,Integer> hartaSimboluri=new HashMap<>();
        for(Persoana pers: persoane.values()){
            for(Tranzactie tranz:pers.tranzactii){
                hartaSimboluri.put(tranz.simbol, hartaSimboluri.getOrDefault(tranz.simbol,0)+1);
            }
        }
        for(Map.Entry<String,Integer> intrare: hartaSimboluri.entrySet()){
            System.out.println(intrare.getKey()+" -> "+intrare.getValue()+" Tranzactii");
        }
        Map<String,Double> hartaValori=new HashMap<>();
        for(Persoana pers: persoane.values()){
            for(Tranzactie tranz: pers.tranzactii){
                if(tranz.getTip().equals("cumparare")){
                    hartaValori.put(tranz.simbol, hartaValori.getOrDefault(tranz.simbol,0.0)+(tranz.cantitate* tranz.pret));
                }else{
                    hartaValori.put(tranz.simbol, hartaValori.getOrDefault(tranz.simbol,0.0)-(tranz.cantitate* tranz.pret));
                }
            }
        }
        JSONArray jsonArray=new JSONArray();
        for(Map.Entry<String,Double> intrare: hartaValori.entrySet()){
            JSONObject obj=new JSONObject();
            obj.put("Simbol",intrare.getKey());
            obj.put("ValoareTranzactionata",intrare.getValue());
            jsonArray.put(obj);
        }
        try(FileWriter fw=new FileWriter("Date\\valori.json")){
            fw.write(jsonArray.toString(4));
        }catch (IOException e){
            System.out.println("Eroare la scrierea fisierului ,json "+e.getMessage());
        }
        for(Persoana pers: persoane.values()){
            Map<String,Integer> harta=new HashMap<>();
            for(Tranzactie tranz: pers.tranzactii){
                if(tranz.getTip().equals("cumparare")){
                    harta.put(tranz.simbol, harta.getOrDefault(tranz.simbol,0)+ tranz.cantitate);
                }else{
                    harta.put(tranz.simbol, harta.getOrDefault(tranz.simbol,0)- tranz.cantitate);
                }
            }
            System.out.println(pers.nume+" "+"tranzactii:");
            for(Map.Entry<String,Integer> intrare:harta.entrySet()){
                System.out.println("\t"+intrare.getKey()+" -> "+intrare.getValue());
            }}

            final int PORT=3929;
            new Thread(()->{
                try(
                        var server=new ServerSocket(PORT);
                        var socket=server.accept();
                        var in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        var out=new PrintWriter(socket.getOutputStream(),true);
                        ){
                    String cnp=in.readLine();
                    System.out.println("SERVER: AM PRIMIT "+cnp);
                    for(Persoana p: persoane.values()){
                        if(p.cnp.equals(cnp)){
                            out.println(p.valoare_curenta);
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
                out.println("8321312312");
                System.out.println("CLIENT: AM PRIMIT "+in.readLine());
            }catch (Exception e){e.printStackTrace();}

    }
}