import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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
        String url="jdbc:sqlite:baza.db";
        Connection conn=null;
        try{
            conn= DriverManager.getConnection(url);
            System.out.println("Avem conexiune");
        }catch (SQLException e){
            System.out.println("Eroare la conectare "+e.getMessage());
        }
        return conn;
    }

    public static void creareTabela(){
        String comanda="CREATE TABLE IF NOT EXISTS Examene("+"id INTEGER PRIMARY KEY AUTOINCREMENT,"+"numeProfesor TEXT NOT NULL,"+"sala INTEGER,"+"studenti_inregistrati INTEGER"+");";
        try(Connection connection= connect(); Statement statement=connection.createStatement()){
            statement.execute(comanda);
            System.out.println("Tabela creata cu succes!");
        } catch (SQLException e) {
            System.out.println("Eroare la crearea bazei de date! "+e.getMessage());
        }
    }
    public static void inserareExamen(String numeProfesor,int sala,int studenti_inregistrati){
        String comanda="INSERT INTO Examene(numeProfesor,sala,studenti_inregistrati) VALUES(?,?,?)";
        try(Connection connection=connect(); PreparedStatement preparedStatement=connection.prepareStatement(comanda)){
            preparedStatement.setString(1,numeProfesor);
            preparedStatement.setInt(2,sala);
            preparedStatement.setInt(3,studenti_inregistrati);
            preparedStatement.executeUpdate();
            System.out.println("Examen inserat!");
        }catch (SQLException e){
            System.out.println("Eroare la inserarea examenului! "+e.getMessage());
        }
    }
    public static Map<String,Profesor> profesori=new HashMap<>();
    public static void main(String[] args)throws  Exception {
        try{
            File xml=new File("Sali.xml");
            DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
            DocumentBuilder builder=factory.newDocumentBuilder();
            Document doc=builder.parse(xml);
            doc.getDocumentElement().normalize();
            NodeList lista=doc.getElementsByTagName("Sala");
            for(int i=0;i<lista.getLength();i++){
                Element sala=(Element) lista.item(i);
                int capacitate=Integer.parseInt(sala.getElementsByTagName("capacitate").item(0).getTextContent());
                int idsala=Integer.parseInt(sala.getElementsByTagName("codSala").item(0).getTextContent());
                NodeList nodProf=sala.getElementsByTagName("listaExamene");
                for(int j=0;j<nodProf.getLength();j++){
                    Element prof=(Element) nodProf.item(j);
                    int ora=Integer.parseInt(prof.getElementsByTagName("ora").item(0).getTextContent());
                    String disciplina=prof.getElementsByTagName("disciplina").item(0).getTextContent();
                    int nrstud=Integer.parseInt(prof.getElementsByTagName("nrStudenti").item(0).getTextContent());
                    String numeProf=prof.getElementsByTagName("titular").item(0).getTextContent();
                    profesori.put(numeProf,new Profesor(numeProf,disciplina));
                    profesori.get(numeProf).adaugaExamen(new Examen(idsala,disciplina,capacitate,ora,nrstud));
                }
            }
        }catch (Exception e){
            System.out.println("Eroare la citirea fisierului xml "+e.getMessage());
        }
        System.out.println("Cerinta: Afisati profesorii si cate examene are fiecare de corectat");
        for(Profesor prof:profesori.values()){
            System.out.println(prof.nume+" Are: "+prof.examene.size()+" examene.");
        }
        System.out.println("Cerinta: Afisati salile in ordine descrescatoare a numarului de locuri disponibile.");
        List<Examen> listaexamene=new ArrayList<>();
        for(Profesor prof: profesori.values()){
            for(Examen exam:prof.examene){
                listaexamene.add(exam);
            }
        }
        listaexamene.sort((e1,e2)->compare(e2.capacitate_sala,e1.capacitate_sala));
        for(Examen exam:listaexamene){
            System.out.println(exam.sala+" "+exam.capacitate_sala+" "+exam.nrStudenti);
        }
        System.out.println("Cerinta: Scrieti un raport in care sa se gaseasca informatii despre examenele organizate de catre profesori.");
        try(BufferedWriter br=new BufferedWriter(new FileWriter("raport.txt"))){
            for(Profesor prof: profesori.values()){
                br.write(prof.nume+" la disciplina "+prof.disciplina+" are urmatoarele examene: ");
                for(Examen exam:prof.examene){
                    br.write("Sala "+exam.sala+" Ora "+exam.ora+" Materia "+exam.materie);
                    br.write("\t");
                    br.newLine();

                }
            }
        }catch (IOException e){
            System.out.println("Eroare la scrierea fisierului "+e.getMessage());
        }
        System.out.println("Cerinta: Introduceti datele intr-o baza de date");
        creareTabela();
        for (Profesor prof:profesori.values()){
            for(Examen examen:prof.examene){
                inserareExamen(prof.getNume(), examen.getSala(),examen.nrStudenti);
            }
        }
        System.out.println("Cerinta: Să se implementeze funcționalitățile de server și client TCP/IP");
        final int PORT=3929;
        new Thread(()->{
            try(
                    var server=new ServerSocket(PORT);
                    var socket=server.accept();
                    var in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    var out=new PrintWriter(socket.getOutputStream(),true);
                    ){
                var numeProf=in.readLine();
                System.out.println("SERVER: AM PRIMIT "+numeProf);
                out.println(profesori.get(numeProf));
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
            out.println("Gigel");
            System.out.println("CLIENT: AM PRIMIT "+in.readLine());

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}