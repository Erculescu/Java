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
            System.out.println("Conexiune reusita");
        }catch (SQLException e){
            System.out.println("Conexiune esuata!");
        }
        return conn;
    }
    public static void creareTabele(){
        String comanda1="CREATE TABLE IF NOT EXISTS Clienti("+"nrInmatriculare TEXT PRIMARY KEY,"+"anFabricatie INTEGER, "+"tipVehicul TEXT"+");";
        String comanda2="CREATE TABLE IF NOT EXISTS Revizii("+"id INTEGER PRIMARY KEY AUTOINCREMENT, "+"nrInmatriculare TEXT NOT NULL, "+"firma TEXT, "+"kilometraj REAL,"+" taxa REAL, "+"dataPrezentarii TEXT, "+" Va_reveni TEXT"+");";
        try(Connection conn=connect(); Statement statement=conn.createStatement()){
            statement.execute(comanda1);
            statement.execute(comanda2);
            System.out.println("Tabele create cu succes!");
        }catch (SQLException e){
            System.out.println("Eroare la crearea tabelelor! "+e.getMessage());
        }
    }
    public static void inserareClient(String nrInmatriculare, int anFabricatie, String tipVeh){
        String sql="INSERT INTO Clienti(nrInmatriculare,anFabricatie,tipVehicul) VALUES(?,?,?)";
        try(Connection connection=connect();
            PreparedStatement preparedStatement=connection.prepareStatement(sql)
        ){
            preparedStatement.setString(1,nrInmatriculare);
            preparedStatement.setInt(2,anFabricatie);
            preparedStatement.setString(3,tipVeh);
            preparedStatement.executeUpdate();
            System.out.println("Client inserat!");
        }catch (SQLException e){
            System.out.println("Eroare la inserarea unui client! "+e.getMessage());
        }
    }
    public static void inserareRevizie(String nrInmatriculare,String firma,double kilometraj,double taxa,LocalDate dataPrezentarii,boolean revenire){
        String sql="INSERT INTO Revizii(nrInmatriculare,firma,kilometraj,taxa,dataPrezentarii,Va_reveni) VALUES(?,?,?,?,?,?)";
        try(
                Connection connection=connect();
                PreparedStatement preparedStatement=connection.prepareStatement(sql);
                ){
            preparedStatement.setString(1,nrInmatriculare);
            preparedStatement.setString(2,firma);
            preparedStatement.setDouble(3,kilometraj);
            preparedStatement.setDouble(4,taxa);
            preparedStatement.setString(5,String.valueOf(dataPrezentarii));
            preparedStatement.setString(6,String.valueOf(revenire));
            preparedStatement.executeUpdate();
            System.out.println("Revizie inserata!");
        }catch (SQLException e){
            System.out.println("Eroare la inserarea unei revizii! "+e.getMessage());
        }
    }

    public static Map<String,Vehicul> vehicule=new HashMap<>();
    public static void main(String[] args) throws Exception {
        try{
            File xml=new File("Autovehicule.xml");
            DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
            DocumentBuilder builder=factory.newDocumentBuilder();
            Document doc=builder.parse(xml);
            doc.getDocumentElement().normalize();
            NodeList nodeList=doc.getElementsByTagName("Autovehicul");
            for(int i=0;i<nodeList.getLength();i++){
                Node nod=nodeList.item(i);
                if(nod.getNodeType()==Node.ELEMENT_NODE){
                    Element vehicul=(Element) nod;
                    String nrInmatriuclare=vehicul.getElementsByTagName("numarInmatriculare").item(0).getTextContent();
                    int an=Integer.parseInt(vehicul.getElementsByTagName("an").item(0).getTextContent());
                    vehicule.put(nrInmatriuclare,new Vehicul(nrInmatriuclare,an));
                    NodeList nodRev=vehicul.getElementsByTagName("revizii");
                    for(int j=0;j<nodRev.getLength();j++){
                        Element revizii=(Element) nodRev.item(j);
                        String firma=revizii.getElementsByTagName("firma").item(0).getTextContent();
                        int kilometraj=Integer.parseInt(revizii.getElementsByTagName("km").item(0).getTextContent());
                        double taxa=Double.parseDouble(revizii.getElementsByTagName("taxa").item(0).getTextContent());
                        DateTimeFormatter formatter=DateTimeFormatter.ISO_OFFSET_DATE_TIME;
                        LocalDate data=LocalDate.parse(revizii.getElementsByTagName("datal").item(0).getTextContent(),formatter);
                        String bool=revizii.getElementsByTagName("revenire").item(0).getTextContent();
                        Boolean revenire=Boolean.parseBoolean(bool);
                        vehicule.get(nrInmatriuclare).adaugaRevizie(new Revizie(firma,kilometraj,taxa,data,revenire));
                    }
                    String combustibil=vehicul.getElementsByTagName("tipAutovehicul").item(0).getTextContent();
                    vehicule.get(nrInmatriuclare).setCombustibil(combustibil);
                }
            }
        }catch (Exception e){
            System.out.println("Eroare la citirea fisierului xml "+e.getMessage());
        }
        List<Vehicul> listaVehicule=new ArrayList<>();
        for(Vehicul veh: vehicule.values()){
            listaVehicule.add(veh);
        }
        listaVehicule.sort((v1,v2)->compare(v2.anFabricatei,v1.anFabricatei));
        System.out.println("Cerinta: Afisati toate vehiculele in ordine descrescatoare a datei fabricatiei:");
        for(Vehicul veh:listaVehicule){
            System.out.println(veh);
        }
        System.out.println("Cerinta: Afisati taxele totale ale fiecarui vehicul");
        for(Vehicul veh: vehicule.values()){

            double taxa=0;
            for(Revizie rev:veh.revizii){
                taxa+=rev.getTaxa();
            }
            System.out.println(veh.getNrInmatriculare()+" "+taxa);
        }
        System.out.println("Cerinta: Afisati nr de vehicule: "+vehicule.size());
        System.out.println("Creati un fisier in care sa stocati un raport al reviziilor pentru fiecare vehicul");
        try(BufferedWriter br=new BufferedWriter(new FileWriter("raport.txt"))){
            for(Vehicul vehicul:listaVehicule){
                br.write(vehicul.nrInmatriculare+" "+vehicul.anFabricatei+" "+vehicul.revizii);
                br.newLine();
            }
        }catch (Exception e){
            System.out.println("Eroare la crearea fisierului "+e.getMessage());
        }
        double valTotala=0;
        for(Vehicul veh:listaVehicule){
            for(Revizie rev:veh.revizii){
                valTotala+=rev.taxa;
            }
        }
        System.out.println("Cerinta: Afisati Veniturile totale ale service-ului: "+valTotala);
        System.out.println("Cerinta: Inserati datele intr-o baza de date.");
        creareTabele();
        for(Vehicul vehicul:listaVehicule){
            inserareClient(vehicul.nrInmatriculare, vehicul.anFabricatei, vehicul.combustibil);
            for(Revizie rev: vehicul.revizii){
                inserareRevizie(vehicul.getNrInmatriculare(), rev.firma,rev.kilometraj,rev.taxa,rev.data,rev.revenire);
            }
        }
        System.out.println("Cerinta: Să se implementeze funcționalitățile de server și client TCP/IP și să se execute următorul scenariu: componenta client trimite serverului un nr de inmatriuclare iar componenta server trebuie sa returneze tipul vehiculului.");
        final int PORT=3929;
        new Thread(()->{
            try(var server=new ServerSocket(PORT);
            var socket=server.accept();
            var in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            var out=new PrintWriter(socket.getOutputStream(),true);
            ){
                var nrInmatriculare=in.readLine();
                System.out.println("Server: Am primit "+nrInmatriculare);
                out.println((vehicule.get(nrInmatriculare).combustibil));
            }catch (Exception e){e.printStackTrace();}

        }).start();
        Thread.sleep(500);
        try(
                var socket=new Socket("localhost",PORT);
                var out=new PrintWriter(socket.getOutputStream(),true);
                var in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ){
            out.println("B-03-MUE");
            System.out.println("Client: Am primit "+in.readLine());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}