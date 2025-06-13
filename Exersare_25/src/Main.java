import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    public static Connection connect(){
        String url="jdbc:sqlite:baza.db";
        Connection conn=null;
        try{
            conn= DriverManager.getConnection(url);
            System.out.println("Conectat");
        }catch (SQLException e){
            System.out.println("Eroare la conectarea bazei de date "+e.getMessage());
        }
        return conn;
    }
    public static void creareTabela(){
        String comanda="CREATE TABLE IF NOT EXISTS Pacienti("+"CNP TEXT PRIMARY KEY,"+"NUME TEXT,"+"DIAGNOSTIC TEXT,"+"TIPURGENTA TEXT"+");";
        try(Connection connection=connect(); Statement statement=connection.createStatement()){
            statement.execute(comanda);
            System.out.println("Tabela creata");
        }catch (SQLException e){
            System.out.println("Eroare la crearea tabelei "+e.getMessage());
        }
    }
    public static void inserarePacient(String CNP,String Nume,String Diagnostic,String TipUrgenta){
        String comanda="INSERT INTO Pacienti(CNP,NUME,DIAGNOSTIC,TIPURGENTA) VALUES(?,?,?,?)";
        try(Connection connection=connect(); PreparedStatement preparedStatement=connection.prepareStatement(comanda)){
            preparedStatement.setString(1,CNP);
            preparedStatement.setString(2,Nume);
            preparedStatement.setString(3,Diagnostic);
            preparedStatement.setString(4,TipUrgenta);
            preparedStatement.executeUpdate();
            System.out.println("Pacient Inserat");
        }catch (SQLException e){
            System.out.println("Eroare la inserarea pacientului "+e.getMessage());
        }
    }
    public static Map<Integer,Sectie> sectii=new HashMap<>();
    public static void main(String[] args) throws  Exception{
        try{
            File xml=new File("Sectii.xml");
            DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
            DocumentBuilder builder=factory.newDocumentBuilder();
            Document doc=builder.parse(xml);
            doc.getDocumentElement().normalize();
            NodeList lista=doc.getElementsByTagName("Sectie");
            for(int i=0;i< lista.getLength();i++){
                Node nod= lista.item(i);
                if(nod.getNodeType()==Node.ELEMENT_NODE){
                    Element sectie=(Element) nod;
                    int capacitate=Integer.parseInt(sectie.getElementsByTagName("capacitate").item(0).getTextContent());
                    int codSectie=Integer.parseInt(sectie.getElementsByTagName("codSectie").item(0).getTextContent());
                    String numeSectie=sectie.getElementsByTagName("numeSectie").item(0).getTextContent();
                    sectii.put(codSectie,new Sectie(codSectie,numeSectie,capacitate));
                    NodeList nodPacient=sectie.getElementsByTagName("pacienti");
                    for(int j=0;j<nodPacient.getLength();j++){
                        Element pacient=(Element) nodPacient.item(j);
                        String cnp=pacient.getElementsByTagName("cnp").item(0).getTextContent();
                        String nume=pacient.getElementsByTagName("nume").item(0).getTextContent();
                        DateTimeFormatter formatter=DateTimeFormatter.ISO_OFFSET_DATE_TIME;
                        LocalDate dataN=LocalDate.parse(pacient.getElementsByTagName("datan").item(0).getTextContent(),formatter);
                        boolean esteAsigurat=Boolean.parseBoolean(pacient.getElementsByTagName("asigurat").item(0).getTextContent());
                        String diagnostic=pacient.getElementsByTagName("diagnostic").item(0).getTextContent();
                        String telefon=pacient.getElementsByTagName("telefon").item(0).getTextContent();
                        String tipUrgenta=pacient.getElementsByTagName("urgenta").item(0).getTextContent();
                        sectii.get(codSectie).adaugaPacient(new Pacient(cnp,nume,dataN,esteAsigurat,diagnostic,telefon,tipUrgenta));
                    }
                }
            }
        }catch (Exception e){
            System.out.println("Eroare la citirea fisierului .xml "+e.getMessage());
        }
        int nrPacienti=0;
        for(Sectie sectie:sectii.values()){
            for(Pacient pacient:sectie.pacienti){
                nrPacienti++;
            }
        }
        System.out.println("Cerinta: Afisati nr de pacienti: "+nrPacienti);
        System.out.println("Cerinta: Afisati pacientii din sectii in ordinea descrescatoare a varstelor");
        List<Pacient> listaPacienti=new ArrayList<>();
        for(Sectie sectie:sectii.values()){
            for(Pacient pacient:sectie.pacienti){
                listaPacienti.add(pacient);
            }
        }
        listaPacienti.sort((p1,p2)->p2.getDataN().isBefore(p1.getDataN())?1:-1);
        for(Pacient p:listaPacienti){
            System.out.println(p);
        }
        System.out.println("Cerinta: Creati un fisier .txt in care sa stocati un raport cu fiecare sectie si pacientii corespunzatori acesteia");
        try(BufferedWriter bw=new BufferedWriter(new FileWriter("raport.txt"))){
            for(Sectie sectie:sectii.values()){
                bw.write("Cod sectie: "+sectie.getCodSectie()+" Nume Sectie: "+sectie.getNumeSectie()+" Capacitate Sectie: "+sectie.getCapacitateSectie());
                bw.write(" Pacientii din sectie:");
                bw.newLine();
                for(Pacient pacient:sectie.pacienti){
                    bw.write("\t"+pacient.cnp+" "+pacient.nume+" "+pacient.diagnostic+" "+pacient.TipUrgenta);
                    bw.newLine();
                }
                bw.newLine();
            }
        }catch (Exception e){
            System.out.println("Eroare la scrierea fisierului .txt "+e.getMessage());
        }
        System.out.println("Cerinta: Afisati doar pacientii care au urgente");
        for(Pacient pacient:listaPacienti){
            if(pacient.TipUrgenta.equals("URGENT")){
                System.out.println(pacient);
            }
        }
        System.out.println("Cerinta: Creati o baza de date in care se vor stoca pacientii");
        creareTabela();
        for(Pacient pacient:listaPacienti){
            inserarePacient(pacient.cnp, pacient.nume, pacient.diagnostic, pacient.TipUrgenta);
        }
        System.out.println("Cerinta: Să se implementeze funcționalitățile de server și client");
        final int PORT=3929;
        new Thread(()->{
            try(
                    var server=new ServerSocket(PORT);
                    var socket=server.accept();
                    var in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    var out=new PrintWriter(socket.getOutputStream(),true);
                    ){
                var CNP=in.readLine();
                System.out.println("SERVER: AM PRIMIT "+CNP);
                for(Pacient pacient:listaPacienti){
                    if(pacient.cnp.equals(CNP)){
                        out.println(pacient);
                    }
                }

            }catch (Exception e){e.printStackTrace();}
        }
        ).start();
        Thread.sleep(500);
        try(var socket=new Socket("localhost",PORT);
        var out=new PrintWriter(socket.getOutputStream(),true);
        var in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ){
            out.println("4444444");
            System.out.println("CLIENT: AM PRIMIT "+in.readLine());
        }catch (Exception e){e.printStackTrace();}
    }
}