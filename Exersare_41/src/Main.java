import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Double.compare;

public class Main {
    public static Map<Integer,Produs> produse=new HashMap<>();
    public static void main(String[] args)throws Exception {
        try {
            File xml = new File("Date\\produse.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xml);
            doc.getDocumentElement().normalize();
            NodeList produseList=doc.getElementsByTagName("produs");
            for(int i=0;i<produseList.getLength();i++){
                Node node=produseList.item(i);
                if(node.getNodeType()==Node.ELEMENT_NODE){
                    Element element=(Element) node;
                    int cod=Integer.parseInt(((element.getElementsByTagName("cod").item(0).getTextContent())));
                    String denumire=element.getElementsByTagName("denumire").item(0).getTextContent();
                    Double pret=Double.parseDouble(element.getElementsByTagName("pret").item(0).getTextContent());
                    produse.put(cod,new Produs(cod,denumire,pret));
                    NodeList tranzactii=element.getElementsByTagName("tranzactie");
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
        int nrTranz=0;
        for(Produs p:produse.values()){
            nrTranz+=p.tranzactii.size();
        }
        System.out.println("Nr tranzactii: "+nrTranz);
        produse.values().stream().filter(p->p.stoc>5).forEach(System.out::println);
        try(BufferedWriter bw=new BufferedWriter(new FileWriter("Date\\raport.txt"))){
                List<Produs> listaTemp=produse.values().stream().sorted((p1, p2)->compare(p2.valoare_stoc,p1.valoare_stoc)).toList();
                for(Produs p:listaTemp){
                    bw.write(p.denumire+" "+p.stoc+" "+p.valoare_stoc);
                    bw.newLine();
                }
        }catch (IOException e){
            System.out.println("Eroare la scrierea in fisier .txt "+e.getMessage());
        }
        final int port=3929;
        new Thread(()->{
            try(
                    var server=new ServerSocket(port);
                    var socket=server.accept();
                    var in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    var out=new PrintWriter(socket.getOutputStream(),true);
                    ){
                var intrare=in.readLine();
                System.out.println("SERVER: AM PRIMIT "+intrare);
                for(Produs p:produse.values()){
                    if(intrare.equals(p.denumire)){
                        out.println(p.stoc+" "+p.valoare_stoc+" "+p.tranzactii.size());
                    }
                }
            }catch (Exception e){e.printStackTrace();}
        }).start();
        Thread.sleep(500);
        try(
                var socket=new Socket("localhost",port);
                var out=new PrintWriter(socket.getOutputStream(),true);
                var in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ){
            out.println("Ceai verde Lipton");
            System.out.println("CLIENT: AM PRIMIT "+in.readLine());
        }catch (Exception e){e.printStackTrace();}
    }
}