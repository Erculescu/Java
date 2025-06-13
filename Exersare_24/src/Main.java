import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
        String comanda="CREATE TABLE IF NOT EXISTS cladire("+"id INTEGER PRIMARY KEY AUTOINCREMENT,"+"adresa TEXT NOT NULL,"+"locatari INTEGER NOT NULL,"+
                "costApa REAL,"+"costGaz REAL,"+"costCurent REAL"+");";
        try(Connection connection=connect(); Statement statement=connection.createStatement()){
            statement.execute(comanda);
            System.out.println("Tabela creata");
        }catch (SQLException e){
            System.out.println("Eroare la crearii tabelei "+e.getMessage());
        }
    }

    public static void inserareCladire(String adresa,int locatari,double costApa,double costGaz,double costCurent){
        String comanda="INSERT INTO cladire(adresa,locatari,costApa,costGaz,costCurent) VALUES(?,?,?,?,?)";
        try(Connection connection=connect(); PreparedStatement preparedStatement=connection.prepareStatement(comanda)){
            preparedStatement.setString(1,adresa);
            preparedStatement.setInt(2,locatari);
            preparedStatement.setDouble(3,costApa);
            preparedStatement.setDouble(4,costGaz);
            preparedStatement.setDouble(5,costCurent);
            preparedStatement.executeUpdate();
            System.out.println("Cladire adaugata");
        }catch (SQLException e){
            System.out.println("Eroare la inserarea datelor "+e.getMessage());
        }
    }

    public static double calculIntretinrere(Intretinere intetinere){
        double intretinerea=0;
        for(Cladire cladire:intetinere.cladiri){
            String[] split=cladire.getUtilitati().split(",");
            for(String s:split){
                if(s.equals("Apa")){
                    intretinerea+= intetinere.getApa()* cladire.getLocatari();
                }
                if(s.equals("Gaz")){
                    intretinerea+=intetinere.getGaz()*cladire.getLocatari();
                }
                if(s.equals("Curent")){
                    intretinerea+=intetinere.getCurent()*cladire.getLocatari();
                }
            }
        }
        return intretinerea;

    }
    public static Map<String,Intretinere> intretineri=new HashMap<>();
    public static void main(String[] args)throws  Exception {
        try(BufferedReader br=new BufferedReader(new FileReader("cladiri.csv"))){
            String linie;
            while((linie=br.readLine())!=null){
                String[] split=linie.split(",");
                int id=Integer.parseInt(split[0]);
                double apa=Double.parseDouble(split[2]);
                double gaz=Double.parseDouble(split[3]);
                double curent=Double.parseDouble(split[4]);
                intretineri.put(split[1],new Intretinere(id,split[1],apa,gaz,curent ) );
            }
        }catch (Exception e){
            System.out.println("Eroare la citirea fisierului .csv "+e.getMessage());
        }
        try(FileReader fisier=new FileReader("Raport_cladiri.json")){
            var jcladiri=new JSONArray(new JSONTokener(fisier));

            for(int i=0;i<jcladiri.length();i++){
                var jclad=jcladiri.getJSONObject(i);
                String utilitati=jclad.getString("Utilitati");
                String adresa=jclad.getString("adresa");
                int locatari=jclad.getInt("locatari");
                intretineri.get(adresa).adaugaCladire(new Cladire(adresa,locatari,utilitati));
            }
        }catch (Exception e){
            System.out.println("Eroare la citirea fisierului .json "+e.getMessage());
        }
        System.out.println("Cerinta: introduceti cladirile inregistrate care au sau nu contract: "+intretineri.size());
        System.out.println("Cerinta: Calculati intretinerile pentru fiecare cladire: ");
        for(Intretinere intretinere:intretineri.values()){
            System.out.println(intretinere.adresa+" \tIntretinerea "+calculIntretinrere(intretinere));
        }

        int loc=0;
        for(Intretinere intr:intretineri.values()){
            for(Cladire clad:intr.cladiri){
                loc+=clad.getLocatari();
            }
        }
        System.out.println("Cerinta: Afisare locatari intretinere "+loc);
        System.out.println("\nCerinta: Afisare intretineri in ordinea pretului la utilitatea curent negociat descrescator");
        intretineri.values().stream().sorted((i1,i2)->compare(i2.getCurent(),i1.getCurent())).forEach(System.out::println);
        System.out.println("\nCerinta: Creati o baza de date in care sa stocati datele despre cladiri");
        creareTabele();
        for(Intretinere intretinere:intretineri.values()){
            int locatari=0;
            for(Cladire clad:intretinere.cladiri){
                locatari+=clad.getLocatari();
            }
            inserareCladire(intretinere.getAdresa(),locatari,intretinere.getApa(),intretinere.getGaz(),intretinere.getCurent());
        }
        System.out.println("Imprementati o interfata UDP/IP");
        final int PORT=3929;
        new Thread(()->{
            try(
                    var server=new ServerSocket(PORT);
                    var socket=server.accept();
                    var in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    var out=new PrintWriter(socket.getOutputStream(),true);
                    ){
                var adresa=in.readLine();
                System.out.println("SERVER: AM PRIMIT "+adresa);
                out.println(intretineri.get(adresa));
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
            out.println("Splaiul Independentei 445");
            System.out.println("CLIENT: AM PRIMIT "+in.readLine());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}