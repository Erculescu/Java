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
        Connection conn=null;
        String url="jdbc:sqlite:baza.db";
        try{
            conn= DriverManager.getConnection(url);
            System.out.println("Conectat");
        }catch (SQLException e){
            System.out.println("Eroare la conectare "+e.getMessage());
        }
        return conn;
    }
    public static void creareTabele(){
        String comanda1="CREATE TABLE IF NOT EXISTS Sectii("+"codSectie INTEGER PRIMARY KEY,"+"denumire TEXT,"+"capacitate INTEGER"+");";
        String comanda2="CREATE TABLE IF NOT EXISTS Pacienti("+"cnp TEXT PRIMARY KEY,"+"nume TEXT,"+"varsta INTEGER,"+"codSectie INTEGER"+");";
        try(Connection connection=connect(); Statement statement=connection.createStatement()){
            statement.execute(comanda1);
            statement.execute(comanda2);
            System.out.println("Tabele create");
        }catch (SQLException e){
            System.out.println("Eroare la creare "+e.getMessage());
        }
    }
    public static void inserareSectie(int codSectie,String denumire,int capacitate){
        String comanda="INSERT INTO Sectii(codSectie,denumire,capacitate) VALUES(?,?,?)";
        try(Connection connection=connect(); PreparedStatement preparedStatement=connection.prepareStatement(comanda)){
            preparedStatement.setInt(1,codSectie);
            preparedStatement.setString(2,denumire);
            preparedStatement.setInt(3,capacitate);
            preparedStatement.executeUpdate();
            System.out.println("Sectie inserata");
        }catch (SQLException e){
            System.out.println("Eroare la inserarea sectiei "+e.getMessage());
        }
    }
    public static void inserarePacient(String cnp,String nume,int varsta, int codSectie){
        String comanda="INSERT INTO Pacienti(cnp,nume,varsta,codSectie) VALUES(?,?,?,?)";
        try(Connection connection=connect();PreparedStatement preparedStatement=connection.prepareStatement(comanda)){
            preparedStatement.setString(1,cnp);
            preparedStatement.setString(2,nume);
            preparedStatement.setInt(3,varsta);
            preparedStatement.setInt(4,codSectie);
            preparedStatement.executeUpdate();
            System.out.println("Pacient inserat");
        }catch (SQLException e){
            System.out.println("Eroare la inserarea pacientului "+e.getMessage());
        }
    }
    public static Map<Integer,Sectie> sectii=new HashMap<>();
    public static void main(String[] args)throws Exception {
        try(FileReader file=new FileReader("date\\sectii.json")){
            var jsectii =new JSONArray(new JSONTokener(file));
            for(int i = 0; i< jsectii.length(); i++){
                var jsectie=jsectii.getJSONObject(i);
                int codSectie=jsectie.getInt("codSectie");
                String denumire=jsectie.getString("denumire");
                int nrLocuri=jsectie.getInt("nrLocuri");
                sectii.put(codSectie,new Sectie(codSectie,denumire,nrLocuri));
            }
        }catch (IOException e){
            System.out.println("Eroare la citirea fisierului .json "+e.getMessage());
        }
        try(BufferedReader br=new BufferedReader(new FileReader("date\\pacienti.txt"))){
            String linie;
            while((linie=br.readLine())!=null){
                String[] tokens=linie.split(",");
                String cnp=tokens[0];
                String nume=tokens[1];
                int varsta=Integer.parseInt(tokens[2]);
                int codSectie=Integer.parseInt(tokens[3]);
                sectii.get(codSectie).adaugaPacient(new Pacient(cnp,nume,varsta,codSectie));
            }
        }catch (IOException e){
            System.out.println("Eroare la citirea fisierului .txt "+e.getMessage());
        }
        System.out.println("Cerinta: Să afișeze la consolă lista secțiilor spitalului cu un număr de locuri strict mai mare decât 10");
        for(Sectie sectie: sectii.values()){
            if(sectie.capacitate>10){
                System.out.println(sectie);
            }
        }
        System.out.println("\nCerinta: Să afișeze la consolă lista secțiilor spitalului sortată descrescător după varsta medie a pacientilor internați pe secție.");
        sectii.values().stream().sorted((s1,s2)->compare(s2.getVarstaMedie(),s1.getVarstaMedie())).forEach(System.out::println);

        System.out.println("\nCerinta: Să se scrie în fișierul text jurnal.txt un raport al internărilor pe secții, de forma cod_secție_1,nume secție_1,numar_pacienti_1");
        try(BufferedWriter bw=new BufferedWriter(new FileWriter("date\\jurnal.txt"))){
            for(Sectie sectie: sectii.values()){
                bw.write(sectie.getCodSectie()+" "+sectie.getDenumire()+" "+sectie.pacienti.size());
                bw.newLine();
            }
        }catch (IOException e){
            System.out.println("Eroare la scrierea fisierului .txt "+e.getMessage());
        }
        System.out.println("\nCerinta: Să implementeze funcționalitățile de server și client TCP/IP și să se execute următorul scenariu: componenta client trimite serverului codul unei secții iar componenta server va întoarce clientului numărul de locuri libere.");
        final int PORT=3929;
        new Thread(()->{
            try( var server=new ServerSocket(PORT);
                var socket=server.accept();
                var in =new BufferedReader(new InputStreamReader(socket.getInputStream()));
                var out=new PrintWriter(socket.getOutputStream(),true);
            ){
                var cod= in.readLine();
                System.out.println("SERVER: AM PRIMIT "+cod);
                out.println(sectii.get(Integer.parseInt(cod)).locuriDisponibile);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        ).start();
        Thread.sleep(500);
        try(
                var socket=new Socket("localhost",PORT);
                var out=new PrintWriter(socket.getOutputStream(),true);
                var in=new BufferedReader(new InputStreamReader(socket.getInputStream()))
                ){
            out.println("2");
            System.out.println("CLIENT: AM PRIMIT "+in.readLine());
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("\nCerinta: Stocati datele intr-o baza de date sqlite cu doua tabele");
        creareTabele();
        for(Sectie sectie:sectii.values()){
            inserareSectie(sectie.codSectie, sectie.denumire, sectie.capacitate);
            for(Pacient pacient:sectie.pacienti){
                inserarePacient(pacient.getCnp(),pacient.getNume(), pacient.getVarsta(), pacient.getCodSectie());
            }
        }
    }
}