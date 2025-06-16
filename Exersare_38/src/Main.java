import org.json.JSONArray;
import org.json.JSONTokener;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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
        String url="jdbc:sqlite:Date\\consultatii.db";
        Connection conn=null;
        try{
            conn= DriverManager.getConnection(url);
            System.out.println("Conectat");
        }catch (SQLException e){
            System.out.println("Eroare la conectare "+e.getMessage());
        }
        return conn;
    }
    public static void citireTabela(List<Consultatie> consultatii){
        String comanda="SELECT * FROM Consultatii";
        try(Connection conn=connect(); Statement statement=conn.createStatement();
            ResultSet rs=statement.executeQuery(comanda)){
            while(rs.next()){
                String specialitate=rs.getString("Specialitate");
                int codManevra=rs.getInt("CodManevra");
                int numar=rs.getInt("Numar");
                consultatii.add(new Consultatie(specialitate,codManevra,numar));
            }
        }catch (SQLException e){
            System.out.println("Eroare la citirea tabelei "+e.getMessage());
        }
    }
    public static Double calculeazaVenit(Map.Entry<String,List<Manevra>> hartaspec,List<Consultatie> cons){
        Double venit=0.0;
        for(Consultatie c:cons){
            if(c.specialitate.equals(hartaspec.getKey())){
               for(Manevra m: hartaspec.getValue()){
                   if(m.cod==c.codManevra){
                       venit+=m.tarif*c.numar;
                   }
               }
            }
        }
        return venit;
    }

    public static Map<Integer,Integer> functieNumarare(String spec,List<Manevra> entry,List<Consultatie> cons){

        Map<Integer,Integer> harta=new HashMap<>();
        for(Consultatie c:cons){
            if(c.specialitate.equals(spec)){
                harta.put(c.codManevra,harta.getOrDefault(c.codManevra,0)+c.numar);
            }
        }
        return harta;

    }

    public static Map<String, List<Manevra>> specialitati=new HashMap<>();
    public static List<Consultatie> consultatii=new ArrayList<>();
    public static void main(String[] args) throws Exception {
        try(FileReader fw=new FileReader("Date\\medicale.json")){
            var specialitate=new JSONArray(new JSONTokener(fw));
            for(int i=0;i<specialitate.length();i++){
                var jspec=specialitate.getJSONObject(i);
                String spec=jspec.getString("specialitate");
                List<Manevra> listatemp=new ArrayList<>();
                var manevre=jspec.getJSONArray("manevre");
                for(int j=0;j<manevre.length();j++){
                    var jmanevra=manevre.getJSONObject(j);
                    int cod= jmanevra.getInt("cod");
                    int durata= jmanevra.getInt("durata");
                    double tarif= jmanevra.getDouble("tarif");
                    listatemp.add(new Manevra(cod,durata,tarif));
                }
                specialitati.put(spec,listatemp);
            }
        }catch (IOException e){
            System.out.println("Eroare la citirea fisierului .json "+e.getMessage());
        }
        citireTabela(consultatii);
        System.out.println("Cerinta:Să se afișeze la consolă, pentru fiecare specialitate medicala, manevrele disponibile in ordine descrescatoare ");
        for(Map.Entry<String,List<Manevra>> intrare:specialitati.entrySet()){
            intrare.getValue().sort((m1,m2)->compare(m2.durata,m1.durata));
            for(Manevra manevra: intrare.getValue()){
                System.out.println(intrare.getKey()+" "+manevra.cod+" " +manevra.durata);
            }
        }
        System.out.println("Să se afișeze la consolă specialitatile medicale sortate descrescator dupa veniturile generate de manevrele\n" +
                "efectuate pentru fiecare specialitate, in formatul");
        Map<String,Double> hartaVenituri=new HashMap<>();
        for(Map.Entry<String,List<Manevra>> intrare:specialitati.entrySet()){
            Double venit=calculeazaVenit(intrare,consultatii);
            hartaVenituri.put(intrare.getKey(),venit);
        }
        hartaVenituri.entrySet().stream().sorted(Map.Entry.<String,Double>comparingByValue().reversed()).forEach(entry-> System.out.println(entry.getKey()+" "+entry.getValue()));
        Map<Integer,Integer> hartaNr=new HashMap<>();
        for(Consultatie c:consultatii){
            hartaNr.put(c.codManevra, hartaNr.getOrDefault(c.codManevra,0)+c.numar);
        }
        try{
            DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
            DocumentBuilder builder= factory.newDocumentBuilder();
            Document document=builder.newDocument();

            Element root=document.createElement("specialitati");
            document.appendChild(root);
            for(Map.Entry<String,List<Manevra>> intrare: specialitati.entrySet()){
                Element spec=document.createElement("specialitate");
                root.appendChild(spec);
                Element denumire=document.createElement("denumire");
                denumire.appendChild(document.createTextNode(intrare.getKey()));
                spec.appendChild(denumire);
                Map<Integer,Integer> filtrat=functieNumarare(intrare.getKey(),intrare.getValue(),consultatii);
                for(Map.Entry<Integer,Integer> nrman: filtrat.entrySet()){
                    if(nrman.getValue()>=20){
                        Element manevra=document.createElement("manevra");
                        manevra.appendChild(document.createTextNode(String.valueOf(nrman.getKey())));
                        spec.appendChild(manevra);

                        Element nr=document.createElement("numar");
                        nr.appendChild(document.createTextNode(String.valueOf(nrman.getValue())));
                        spec.appendChild(nr);
                    }
                }
            }
            TransformerFactory transformerFactory=TransformerFactory.newInstance();
            Transformer transformer=transformerFactory.newTransformer();
            DOMSource source=new DOMSource(document);
            StreamResult result=new StreamResult(new File("Date\\manevremedicale.xml"));
            transformer.transform(source,result);
        }catch (ParserConfigurationException | TransformerException e){
            e.printStackTrace();
        }
        System.out.println(" Să implementeze funcționalitățile de server și client TCP/IP și să se execute următorul scenariu:\n" +
                "- componenta client trimite serverului o specialitate medicala,\n" +
                "- iar componenta server va întoarce lista cu manevrele si numarul de manevre efectuate pentru pentru specialitate\n" +
                "medicala primita,\n" +
                "- componenta server poate servi oricâți clienți.");
        final int PORT=3929;
        new Thread(()->{
            try(
                    var server=new ServerSocket(PORT);
                    var socket=server.accept();
                    var in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    var out=new PrintWriter(socket.getOutputStream(),true)
                    ){
                var spec=in.readLine();
                System.out.println("SERVER: AM PRIMIT "+spec);
                out.println(functieNumarare(spec,specialitati.get(spec),consultatii).toString());
            }catch (Exception e){
                e.printStackTrace();
            }

        }).start();
        Thread.sleep(500);
        try(
                var socket=new Socket("localhost",PORT);
                var out=new PrintWriter(socket.getOutputStream(),true);
                var in=new BufferedReader(new InputStreamReader(socket.getInputStream()))
                ){
            out.println("Oftalmologie");
            System.out.println("CLIENT: AM PRIMIT "+in.readLine());
        }
    }
}