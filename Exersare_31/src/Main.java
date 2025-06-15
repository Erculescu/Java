import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Main {
    public static Connection connect(){
        String url="jdbc:sqlite:Date//bursa.db";
        Connection conn=null;
        try{
            conn= DriverManager.getConnection(url);
            System.out.println("Conectat");
        }catch (SQLException e){
            System.out.println("Eroare la conectare "+e.getMessage());
        }
        return conn;
    }
    public static void citire(Map<Integer,Client> hartaClienti){
        String comadna="SELECT * FROM Persoane";
        try(Connection connection=connect(); Statement statement=connection.createStatement();
            ResultSet rezultat=statement.executeQuery(comadna)){
            while (rezultat.next()){
                int cod=rezultat.getInt("Cod");
                String cnp=rezultat.getString("CNP");
                String nume=rezultat.getString("Nume");
                hartaClienti.put(cod,new Client(cod,cnp,nume));
            }
        }catch (SQLException e){
            System.out.println("Eroare la citirea bazei de date "+e.getMessage());
        }
    }

    public static Map<Integer,Client> clienti=new HashMap<>();
    public static void main(String[] args)throws Exception {
        citire(clienti);
        try(BufferedReader br=new BufferedReader(new FileReader("Date\\bursa_tranzactii.txt"))){
            String linie;
            while((linie=br.readLine())!=null){
                String[] tokens=linie.split(",");
                int cod=Integer.parseInt(tokens[0]);
                String simbol=tokens[1];
                String tip=tokens[2];
                int cantitate=Integer.parseInt(tokens[3]);
                float pret=Float.parseFloat(tokens[4]);
                clienti.get(cod).adaugaTranzactie(new Tranzactie(cod,simbol,tip,cantitate,pret));
            }
        } catch (Exception e) {
            System.out.println("Eroare la citirea fisierului .txt "+e.getMessage() );
        }
        int contor=0;
        for(Client c: clienti.values()){
            if(c.cnp.startsWith("8")||c.cnp.startsWith("9")){
                contor++;
            }
        }
        System.out.println("Cerinta: Afisare clienti straini(CNP incepe cu 8 sau 9): "+contor);
        System.out.println("Cerinta: Afisare nr tranzactii pt fiecare simbol: ");
        Map<String,Integer> hartaSimboluri=new HashMap<>();
        for(Client c: clienti.values()){
            for(Tranzactie tranz:c.tranzactii){
                hartaSimboluri.put(tranz.simbol, hartaSimboluri.getOrDefault(tranz.simbol,0)+1);
            }
        }
        for(Map.Entry<String,Integer> harta: hartaSimboluri.entrySet() ){
            System.out.println(harta.getKey()+" "+harta.getValue()+" Tranzactii");
        }
        System.out.println("Cerinta: Sa se salveze in fisierul simboluri.txt o lista de simboluri fara ca acestea sa se repete");
        HashSet<String> setSimboluri=new HashSet<>();
        for(Client c: clienti.values()){
            for(Tranzactie tranz:c.tranzactii){
                setSimboluri.add(tranz.simbol);
            }
        }
        try(BufferedWriter bw=new BufferedWriter(new FileWriter("Date\\simboluri.txt"))){
            for(String simbol:setSimboluri){
                bw.write(simbol);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Eroare la scrierea fisierului .txt "+e.getMessage());
        }
        System.out.println("Cerinta: Sa se afiseze la consola portofoliul curent pentru fiecare client ");
        for(Client c: clienti.values()){
            System.out.println(c.nume);
            c.calculPortofoliu();
        }
        System.out.println("Cerinta: Sa se implementeze o interfata UDP/IP in care sa se furnizeze un cod de client si se va returna numarul de tranzactii efectuate.");
        final int PORT=3929;
        new Thread(()->{
            try(
                    var server=new ServerSocket(PORT);
                    var socket=server.accept();
                    var in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    var out=new PrintWriter(socket.getOutputStream(),true);
                    ){
                var cod =in.readLine();
                System.out.println("SERVER: AM PRIMIT "+ cod);
                out.println(clienti.get(Integer.parseInt(cod)).tranzactii.size());
            }catch (Exception e){e.printStackTrace();}
        }
        ).start();
        Thread.sleep(500);

        try(
                var socket=new Socket("localhost",PORT);
                var out=new PrintWriter(socket.getOutputStream(),true);
                var in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ){
            out.println("2");
            System.out.println("CLIENT: AM PRIMIT: "+in.readLine());
        }catch (Exception e){e.printStackTrace();}

    }
}