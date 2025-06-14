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

import static java.lang.Long.compare;

public class Main {
    public static Connection connect(){
        Connection con=null;
        String url="jdbc:sqlite:examen.db";
        try{
            con=DriverManager.getConnection(url);
            System.out.println("Conectat");
        }catch (SQLException e){
            System.out.println("Eroare la conectare "+e.getMessage());
        }
        return con;
    }
    public static void creareTabela(){
        String comanda="CREATE TABLE IF NOT EXISTS CANDIDATI("+"cod_candidat INTEGER,"+"nume_candidat TEXT,"+"medie REAL,"+"numar_optiuni INTEGER"+");";
        try(Connection connection=connect(); Statement statement=connection.createStatement()){
            statement.execute(comanda);
            System.out.println("Tabela creata");
        }catch (SQLException e){
            System.out.println("Eroare la creare "+e.getMessage());
        }
    }
    public static void inserareCandidat(int cod_candidat,String nume_candidat,double medie,int numar_optiuni){
        String comanda="INSERT INTO CANDIDATI(cod_candidat,nume_candidat,medie,numar_optiuni) VALUES(?,?,?,?);";
        try(Connection connection=connect(); PreparedStatement preparedStatement= connect().prepareStatement(comanda)){
            preparedStatement.setInt(1,cod_candidat);
            preparedStatement.setString(2,nume_candidat);
            preparedStatement.setDouble(3,medie);
            preparedStatement.setInt(4,numar_optiuni);
            preparedStatement.executeUpdate();
            System.out.println("Candidat inserat");
        }catch (SQLException e){
            System.out.println("Eroare la inserare "+e.getMessage());
        }
    }
    public static Map<Integer,Liceu> licee=new HashMap<>();
    public static Map<Integer,Candidat> candidati=new HashMap<>();
    public static void main(String[] args)throws  Exception {
        try(BufferedReader br=new BufferedReader(new FileReader("date\\licee.txt"))){
            String linie;
            while((linie=br.readLine())!=null){
                String[] tokens=linie.split(",");
                int codLiceu=Integer.parseInt(tokens[0]);
                String nume=tokens[1];
                licee.put(codLiceu,new Liceu(codLiceu,nume));
                int nrSpecializari=Integer.parseInt(tokens[2]);
                String specializari=br.readLine();
                String[] date=specializari.split(",");
                for(int i=0,j=0;i<nrSpecializari;i++,j+=2){
                    licee.get(codLiceu).adaugaOptiune(Integer.parseInt(date[j]),Integer.parseInt(date[j+1]));

                }
            }
        }catch (IOException e){
            System.out.println("Eroare la citirea fisierului .txt "+e.getMessage());
        }
        try(FileReader file=new FileReader("date\\candidati.json")){
            var jcandidati=new JSONArray(new JSONTokener(file));
            for(int i=0;i< jcandidati.length();i++){
                var jcand=jcandidati.getJSONObject(i);
                int codCand=jcand.getInt("codCandidat");
                String numeCandidat=jcand.getString("numeCandidat");
                Double medie=jcand.getDouble("media");
                var joptiuni=jcand.getJSONArray("optiuni");
                candidati.put(codCand,new Candidat(codCand,numeCandidat,medie));
                for(int j=0;j<joptiuni.length();j++){
                    var jop=joptiuni.getJSONObject(j);
                    int codLiceu=jop.getInt("codLiceu");
                    int codSpecializare=jop.getInt("codSpecializare");
                    candidati.get(codCand).adaugaOptiune(new optiune(codLiceu,codSpecializare));
                }
            }
        }catch (IOException e){
            System.out.println("Eroare la citirea fisierului .json "+e.getMessage());
        }
        System.out.println("Cerinta: Să afișeze la consolă numărul de candidați cu medii mai mari sau egale cu 9\n");
        for(Candidat c: candidati.values()){
            if(c.medie>=9.0){
                System.out.println(c);
            }
        }
        System.out.println("Cerinta: Să se afișeze lista liceelor sortată descrescător după numărul total de locuri.\n");
        licee.values().stream().sorted((l1,l2)->compare(l2.getNrLocuri(),l1.getNrLocuri())).forEach(System.out::println);
        System.out.println("Cerinta: Să se listeze în fișierul jurnal.txt candidații ordonați descrescător după numărul de opțiuni (criteriul 1) iar în caz de egalitate după medie (criteriul 2).\n");
        List<Candidat> listaCandidati=new ArrayList<>();
        for(Candidat c:candidati.values()){
            listaCandidati.add(c);
        }
        listaCandidati.sort((c1,c2)->c2.compareTo(c1));
        try(BufferedWriter bw=new BufferedWriter(new FileWriter("date\\jurnal.txt"))){
            for(Candidat c:listaCandidati){
                bw.write(c.codCandidat+" "+c.nume+" "+c.optiuni.size()+" "+c.medie);
                bw.newLine();
            }
        }catch (IOException e){
            System.out.println("Eroare la scrierea fisierului .txt "+e.getMessage());
        }
        System.out.println("Cerinta:  Să se creeze tabela CANDIDATI în baza de date sqlite examen.db și să se salveze opțiunile candidaților.\n" +
                "Tabela va avea câmpurile: cod_candidat - integer, nume_candidat- text, medie - double și numar_optiuni - integer.\n");
        creareTabela();
        for(Candidat c:candidati.values()){
            inserareCandidat(c.codCandidat,c.nume,c.medie,c.optiuni.size());
        }
        System.out.println("Cerinta: Să implementeze funcționalitățile de server și client TCP/IP");
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
                out.println(candidati.get(Integer.parseInt(cod)).medie);
            }catch (Exception e){e.printStackTrace();}
        }
        ).start();
        Thread.sleep(500);
        try(
                var socket=new Socket("localhost",PORT);
                var out=new PrintWriter(socket.getOutputStream(),true);
                var in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ){
            out.println("1");
            System.out.println("CLIENT: AM PRIMIT "+in.readLine());
        }catch (Exception e){e.printStackTrace();}

    }
}