import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Main {
public static Map<Integer,Produs> produse=new HashMap<>();
    public static void main(String[] args)throws  Exception {
        try (BufferedReader br = new BufferedReader(new FileReader("produse.txt"))) {
            String linie;
            while ((linie = br.readLine()) != null) {
                int cod = Integer.parseInt(linie.split(",")[0]);
                String denumire = linie.split(",")[1];
                double pret = Double.parseDouble(linie.split(",")[2]);
                produse.put(cod, new Produs(pret, denumire, cod));
            }
        } catch (Exception e) {
            System.out.println("Eroare la citirea fisierului " + e.getMessage());
        }

        try {
            File xml = new File("produse.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xml);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("produs");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node nod = nodeList.item(i);
                if (nod.getNodeType() == Node.ELEMENT_NODE) {
                    Element produs = (Element) nod;
                    int cod = Integer.parseInt(produs.getElementsByTagName("cod").item(0).getTextContent());
                    String denumire = produs.getElementsByTagName("denumire").item(0).getTextContent();
                    NodeList tranzlist = produs.getElementsByTagName("tranzactie");
                    for (int j = 0; j < tranzlist.getLength(); j++) {
                        Element tranz = (Element) tranzlist.item(j);
                        String tip = tranz.getAttribute("tip");
                        int cantitate = Integer.parseInt(tranz.getAttribute("cantitate"));
                        produse.get(cod).adaugaTranzactie(new Tranzactie(cod, cantitate, tip));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Eroare la citirea fisierului xml " + e.getMessage());
        }
        int cant = 0;
        for (Produs prod : produse.values()) {
            cant += prod.cantitate;
        }
        System.out.println("Cerinta 1: " + cant);
        System.out.println("Cerinta 2: ");
        List<Produs> listaProduse = new ArrayList<>(produse.values());
        listaProduse.sort((p1, p2) -> p1.denumire.compareTo(p2.denumire));
        for (Produs prod : listaProduse) {
            System.out.println(prod);
        }
        System.out.println("Cerinta 3: ");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("lista.txt"))) {
            for (Produs prod : listaProduse) {
                bw.write(prod.denumire);
                bw.write(" ");
                bw.write(String.valueOf(prod.tranzactii.size()));
                bw.newLine();
            }
        } catch (Exception e) {
            System.out.println("Eroare la scrierea fisierului " + e.getMessage());
        }
        double val = 0;
        for (Produs prod : produse.values()) {
            val += prod.cantitate * prod.pret;
        }
        System.out.println("Cerinta 4: " + val);

        final int PORT = 3929;
        new Thread(() -> {
            try (var server = new ServerSocket(PORT);
                 var socket = server.accept();
                 var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 var out = new PrintWriter(socket.getOutputStream(), true);
            ) {
                var codProdus = Integer.parseInt(in.readLine());
                System.out.println("SERVER: AM PRIMIT "+codProdus);
                out.println(produse.get(codProdus).getPret());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ).start();
        Thread.sleep(500);
        try (var socket = new Socket("localhost", PORT);
             var out = new PrintWriter(socket.getOutputStream(), true);
             var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            out.println(2);
            System.out.println("CLIENT: AM PRIMIT");
            System.out.println(in.readLine());

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}