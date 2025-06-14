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
        Connection con=null;
        String url="jdbc:sqlite:baza.db";
        try{
            con= DriverManager.getConnection(url);
            System.out.println("Conectat");
        }catch (SQLException e){
            System.out.println("Eroare la conectare "+e.getMessage());
        }
        return con;
    }
    public static void creareTabele(){
        String comanda1="CREATE TABLE IF NOT EXISTS Filme("+"id INTEGER PRIMARY KEY AUTOINCREMENT,"+"denumire TEXT,"+"pret REAL"+");";
        String comanda2="CREATE TABLE IF NOT EXISTS Tranzactii("+"id INTEGER PRIMARY KEY AUTOINCREMENT,"+"idProdus INTEGER,"+"tip TEXT,"+"cantitate INTEGER"+");";
        try(Connection connection=connect(); Statement statement=connection.createStatement()){
            statement.execute(comanda1);
            statement.execute(comanda2);
            System.out.println("Tabele create");
        }catch (SQLException e){
            System.out.println("Eroare la crearea tabelelor "+e.getMessage());
        }
    }
    public static void inserareFilm(String denumire,Double pret){
        String comanda="INSERT INTO Filme(denumire,pret) VALUES(?,?);";
        try(Connection connection=connect(); PreparedStatement preparedStatement=connection.prepareStatement(comanda)){
            preparedStatement.setString(1,denumire);
            preparedStatement.setDouble(2,pret);
            preparedStatement.executeUpdate();
            System.out.println("Film inserat");
        }catch (SQLException e){
            System.out.println("Eroare la inserarea filmuli "+e.getMessage());
        }

    }
    public static void inserareTranzactie(int idProdus,String tip,int cantitate){
        String comanda="INSERT INTO Tranzactii(idProdus,tip,cantitate) VALUES(?,?,?);";
        try(Connection connection=connect();PreparedStatement preparedStatement=connection.prepareStatement(comanda)){
            preparedStatement.setInt(1,idProdus);
            preparedStatement.setString(2,tip);
            preparedStatement.setInt(3,cantitate);
            preparedStatement.executeUpdate();
            System.out.println("Tranzactie inserata");
        }catch (SQLException e){
            System.out.println("Eroare la inserarea tranzactiei "+e.getMessage());
        }
    }
    public static Map<Integer,Film> filme=new HashMap<>();
    public static void main(String[] args) throws Exception {
        try(BufferedReader br=new BufferedReader(new FileReader("produse.csv"))){
            String linie;
            while((linie=br.readLine())!=null){
                String[] data=linie.split(",");
                int codProdus=Integer.parseInt(data[0]);
                String denumire=data[1];
                Double pret=Double.parseDouble(data[2]);
                filme.put(codProdus,new Film(codProdus,denumire,pret));
            }
        }catch (IOException e){
            System.out.println("Eroare la citirea fisierului .csv " +e.getMessage());
        }
        try(FileReader fw=new FileReader("stocuri.json")){
            var jTranzactii=new JSONArray(new JSONTokener(fw));
            for(int i=0;i<jTranzactii.length();i++){
                var jtranz=jTranzactii.getJSONObject(i);
                int codProdus=jtranz.getInt("codProdus");
                int cantitate=jtranz.getInt("cantitate");
                String operatiune=jtranz.getString("operatiune");
                filme.get(codProdus).adaugaTranzactie(new Tranzactii(codProdus,cantitate,operatiune));
            }
        }catch (IOException e){
            System.out.println("Eroare la citirea fisierului .json "+e.getMessage());
        }
        System.out.println("Cerinta: Afisati numarul de filme stocate: "+filme.size());
        Double valoareTotala=0.0;
        for(Film f:filme.values()){
            valoareTotala+=f.getValoareStoc();
        }
        System.out.println("Cerinta: Afisati valoarea stocului curent: "+valoareTotala);
        System.out.println("Cerinta: Afisati filmele in ordine descrescatoare a valorii stocului acestora:");
        filme.values().stream().sorted((f1,f2)->compare(f2.valoareStoc,f1.valoareStoc)).forEach(System.out::println);
        System.out.println("Cerinta: Stocati intr-un fisier .txt toate filmele care sunt in stoc in ordine crescatoare a numarului de bucati pe stoc");
        List<Film> listaFilme=new ArrayList<>();
        for(Film f:filme.values()){
            if(f.stoc!=0){
                listaFilme.add(f);
            }
        }
        listaFilme.sort((f1,f2)->compare(f1.stoc,f2.stoc));
        try(BufferedWriter bw=new BufferedWriter(new FileWriter("raport.txt"))){
            for(Film f:listaFilme){
                bw.write(f.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Eroare la scrierea fisierului .txt "+e.getMessage());
        }
        System.out.println("Cerinta: Implementati o baza de date in care sa stocati tranzactiile si filmele");
        creareTabele();
        for(Film f:filme.values()){
            inserareFilm(f.Denumire,f.pret);
            for(Tranzactii t:f.tranzactii){
                inserareTranzactie(f.codProdus,t.getTip(),t.getCantitate());
            }
        }
        System.out.println("Cerinta: Imprementati o interfata UDP/IP");
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
                out.println(filme.get(Integer.parseInt(cod)).toString());
            }catch (Exception e){e.printStackTrace();}
        }
        ).start();
        Thread.sleep(500);
        try(var socket=new Socket("localhost",PORT);
        var out=new PrintWriter(socket.getOutputStream(),true);
        var in=new BufferedReader(new InputStreamReader(socket.getInputStream()));){
            out.println("1");
            System.out.println("CLIENT: AM PRIMIT "+in.readLine());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}