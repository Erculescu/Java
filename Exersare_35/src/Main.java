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
import java.util.HashMap;
import java.util.Map;

import static java.lang.Double.compare;

public class Main {
    public static Connection connect(){
        Connection con=null;
        String url="jdbc:sqlite:Date\\baza.bd";
        try{
            con= DriverManager.getConnection(url);
            System.out.println("Conectat");
        }catch (SQLException e){
            System.out.println("Eroare la conectare "+e.getMessage());
        }
        return con;
    }
    public static void creareTabela(){
        String comanda="CREATE TABLE IF NOT EXISTS produs("+"codProdus INTEGER PRIMARY KEY,"+"denumire TEXT,"+"pret REAL,"+"stoc INTEGER"+");";
        try(Connection connection=connect(); Statement statement=connection.createStatement()){
            statement.execute(comanda);
            System.out.println("Tabela creata");
        } catch (SQLException e) {
            System.out.println("Eroare la crearea tabelei "+e.getMessage());
        }
    }

    public static void inserareTabela(int codProdus,String denumire,double pret,int stoc){
        String comanda="INSERT INTO produs(codProdus,denumire,pret,stoc) VALUES(?,?,?,?);";
        try(Connection connection=connect(); PreparedStatement preparedStatement=connection.prepareStatement(comanda)){
            preparedStatement.setInt(1,codProdus);
            preparedStatement.setString(2,denumire);
            preparedStatement.setDouble(3,pret);
            preparedStatement.setInt(4,stoc);
            preparedStatement.executeUpdate();
            System.out.println("Produs inserat");
        }catch (SQLException e){
            System.out.println("Eroare la inserarea tabelei "+e.getMessage());
        }
    }

    public static Map<Integer,Produse> produse=new HashMap<>();
    public static void main(String[] args)throws Exception {
    try{
        File xml=new File("Date\\produse.xml");
        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
        DocumentBuilder builder= factory.newDocumentBuilder();
        Document doc=builder.parse(xml);
        doc.getDocumentElement().normalize();
        NodeList nlist=doc.getElementsByTagName("produs");
        for(int i=0;i<nlist.getLength();i++){
            Node nod=nlist.item(i);
            if(nod.getNodeType()==Node.ELEMENT_NODE){
                Element produs=(Element) nod;
                int cod=Integer.parseInt(produs.getElementsByTagName("cod").item(0).getTextContent());
                String denumire=produs.getElementsByTagName("denumire").item(0).getTextContent();
                double pret=Double.parseDouble(produs.getElementsByTagName("pret").item(0).getTextContent());
                produse.put(cod,new Produse(cod,denumire,pret));
                NodeList tranzactii=produs.getElementsByTagName("tranzactie");
                for(int j=0;j<tranzactii.getLength();j++){
                    Element tranzactie=(Element) tranzactii.item(j);
                    String tip=tranzactie.getAttribute("tip");
                    int cantitate=Integer.parseInt(tranzactie.getAttribute("cantitate"));
                    produse.get(cod).adaugaTranzactie(new Tranzactie(tip,cantitate));
                }
            }
        }
    }catch (Exception e){
        System.out.println("Eroare la citirea fisierului .xml "+e.getMessage());
    }
        System.out.println("Afisati numarul de produse: "+produse.size());
        System.out.println("Afisati numarul de tranzactii pentru fiecare produs: ");
        for(Produse p:produse.values()){
            System.out.println(p.denumire+" "+p.tranzactii.size());
        }
        System.out.println("Afisati lista de produse ordonade descrescator dupa pretul fiecaruia");
        produse.values().stream().sorted((p1,p2)->compare(p2.pret,p1.pret)).forEach(System.out::println);
        System.out.println("Creati un fisier .txt in care sa stocati valoarea stocului curent si numarul de produse pentru fiecare din acestea");
        try(BufferedWriter bw=new BufferedWriter(new FileWriter("Date\\raport.txt"))){
            for(Produse p: produse.values()){
                bw.write(p.denumire+" "+p.stoc+" "+(p.stoc*p.pret));
                bw.newLine();
            }
        }catch (IOException e){
            System.out.println("Eroare la scrierea fisierului .txt "+e.getMessage());
        }
        System.out.println("Creati o interfata TCP/IP");
        final int PORT=3929;
        new Thread(()->{
            try(
                    var server=new ServerSocket(PORT);
                    var socket=server.accept();
                    var in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    var out=new PrintWriter(socket.getOutputStream(),true);
                    ){
                var denumireProdus=in.readLine();
                System.out.println("SERVER: AM PRIMIT: "+denumireProdus);
                for(Produse p: produse.values()){
                    if(p.denumire.equals(denumireProdus)){
                        out.println(p);
                    }
                }
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
            out.println("Stafide 200g");
            System.out.println("CLIENT: AM PRIMIT: "+in.readLine());
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Stocati intr-o baza de date informatii legate de codul produsului, denumirea, pretul si stocul curent");
        creareTabela();
        for(Produse p: produse.values()){
            inserareTabela(p.cod,p.denumire,p.pret,p.stoc);
        }
    }
}