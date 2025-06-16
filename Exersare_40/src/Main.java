import org.json.JSONArray;
import org.json.JSONTokener;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
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
        Connection conn=null;
        String url="jdbc:sqlite:Date\\consultatii.db";
        try{
            conn= DriverManager.getConnection(url);
            System.out.println("Conectat");
        } catch (SQLException e) {
            System.out.println("Eroare la conectare "+e.getMessage());
        }
        return conn;
    }
    public static void citire(Map<String,Specialitate> specialitati){
        String comanda="SELECT * FROM Consultatii";
        try(
                Connection conn=connect(); Statement statement=conn.createStatement();
                ResultSet rs=statement.executeQuery(comanda);
                ){
            while (rs.next()){
                String spec=rs.getString("specialitate");
                int codManevra=rs.getInt("CodManevra");
                int numar=rs.getInt("Numar");
                specialitati.get(spec).adaugaConsultatie(new consultatie(spec,codManevra,numar));
            }
        }catch (SQLException e){
            System.out.println("Eroare la citirea bazei de date "+e.getMessage());
        }
    }


    public static Map<String,Specialitate> specialitati=new HashMap<>();
    public static void main(String[] args) throws Exception {
        try(FileReader file=new FileReader("Date\\medicale.json")){
            var json=new JSONArray(new JSONTokener(file));
            for(int i=0;i<json.length();i++){
                var spec=json.getJSONObject(i);
                String denumire=spec.getString("specialitate");
                specialitati.put(denumire,new Specialitate(denumire));
                var manevre=spec.getJSONArray("manevre");
                for(int j=0;j<manevre.length();j++){
                    var manevra=manevre.getJSONObject(j);
                    int cod=manevra.getInt("cod");
                    int durata=manevra.getInt("durata");
                    double tarif=manevra.getDouble("tarif");
                    specialitati.get(denumire).adaugaManevra(new Manevra(cod,durata,tarif));
                }
            }
        }catch (IOException e){
            System.out.println("Eroare la citirea fisierului .json "+e.getMessage());
        }
        citire(specialitati);
        System.out.println("Să se afișeze la consolă, pentru fiecare specialitate medicala, manevrele disponibile in ordine descrescatoare a duratei ");
        for(var spec:specialitati.values()){
            List<Manevra> listatemp=new ArrayList<>(spec.manevre.values());
            listatemp.sort((m1,m2)->compare(m2.durata,m1.durata));
            for(Manevra m:listatemp){
                System.out.println(spec.denumire+" "+m.cod+" "+m.durata);
            }
        }
        System.out.println("Să se afișeze la consolă specialitatile medicale sortate descrescator dupa veniturile generate de manevrele");
        Map<String,Double> hartaVenituri=new HashMap<>();
        for(var spec:specialitati.values()){
          hartaVenituri.put(spec.denumire, spec.getVenit());
        }
        hartaVenituri.entrySet().stream().sorted((h1,h2)->h2.getValue().compareTo(h1.getValue())).forEach(System.out::println);


        try{
            DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
            DocumentBuilder builder= factory.newDocumentBuilder();
            Document doc=builder.newDocument();
            Element root=doc.createElement("medicale");
            doc.appendChild(root);
            for(var spec:specialitati.values()){
                Element specialitate=doc.createElement("specialitate");
                root.appendChild(specialitate);
                Element denumire=doc.createElement("denumire");
                denumire.appendChild(doc.createTextNode(spec.denumire));
                specialitate.appendChild(denumire);
                Element manevre=doc.createElement("manevre");
                specialitate.appendChild(manevre);
                for(Manevra m:spec.manevre.values()){
                    Element man=doc.createElement("Manevra");
                    man.setAttribute("cod",String.valueOf(m.cod));
                    man.setAttribute("numar",String.valueOf(m.durata));
                    manevre.appendChild(man);
                }
            }
            TransformerFactory transformerFactory=TransformerFactory.newInstance();
            Transformer transformer=transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT,"yes");
            DOMSource source=new DOMSource(doc);
            StreamResult result=new StreamResult(new File("Date\\manevremedicale.xml"));
            transformer.transform(source,result);
        }catch (Exception e){
            e.printStackTrace();
        }


        System.out.println("Să implementeze funcționalitățile de server și client TCP/IP și să se execute următorul scenariu:\n" +
                "- componenta client trimite serverului o specialitate medicala,\n" +
                "- iar componenta server va întoarce lista cu manevrele si numarul de manevre efectuate pentru pentru specialitate\n" +
                "medicala primita");
        final int PORT=3929;
        new Thread(()->{
            try(
                    var server=new ServerSocket(PORT);
                    var socket=server.accept();
                    var in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    var out=new PrintWriter(socket.getOutputStream(),true);
                    ){
                var spec=in.readLine();
                System.out.println("SERVER: AM PRIMIT "+spec);
                out.println(specialitati.get(spec).manevre.size());
                out.println(specialitati.get(spec).manevre.values());
            }catch (Exception e){e.printStackTrace();}
        }
        ).start();
        Thread.sleep(500);
        try(
                var socket=new Socket("localhost",PORT);
                var out=new PrintWriter(socket.getOutputStream(),true);
                var in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ){
            out.println("Pediatrie");
            System.out.println("CLIENT:AM PRIMIT "+in.readLine());
            System.out.println(in.readLine());
        }catch (Exception e){e.printStackTrace();}

    }


}