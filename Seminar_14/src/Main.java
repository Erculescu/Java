import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Tranzactie{
    int codProdus;
    int cantitate;
    String tip;

    public Tranzactie(int codProdus, int cantitate, String tip) {
        this.codProdus = codProdus;
        this.cantitate = cantitate;
        this.tip = tip;
    }

    public int getCodProdus() {
        return codProdus;
    }

    public int getCantitate() {
        return cantitate;
    }

    public String getTip() {
        return tip;
    }

    @Override
    public String toString() {
        return "Tranzactie{" +
                "codProdus=" + codProdus +
                ", cantitate=" + cantitate +
                ", tip='" + tip + '\'' +
                '}';
    }
}
class Produs{
    int codProdus;
    String denumire;
    double pret;
    List<Tranzactie> tranzactii;

    public Produs(String linie) {
        this.pret =Double.parseDouble(linie.split(",")[2]);
        this.denumire = linie.split(",")[1];
        this.codProdus = Integer.parseInt(linie.split(",")[0]);
        this.tranzactii = new ArrayList<Tranzactie>();
    }

    @Override
    public String toString() {
        return "Produs{" +
                "codProdus=" + codProdus +
                ", denumire='" + denumire + '\'' +
                ", pret=" + pret +
                ", tranzactii=" + tranzactii +
                '}';
    }

    public int getCodProdus() {
        return codProdus;
    }

    public void setCodProdus(int codProdus) {
        this.codProdus = codProdus;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public List<Tranzactie> getTranzactii() {
        return tranzactii;
    }

    public void setTranzactii(List<Tranzactie> tranzactii) {
        this.tranzactii = tranzactii;
    }
    int cantitate=0;
    public void adaugaTranzactie(Tranzactie tranzactie){
        tranzactii.add(tranzactie);
        if(tranzactie.getTip().equals("intrare")){
            cantitate+=tranzactie.getCantitate();
        }else{
            cantitate-=tranzactie.getCantitate();
        }
    }
    public int getNrTranzactii(){
        return tranzactii.size();
    }
    public double getValoare(){
        return pret*cantitate;
    }
}

public class Main {
    public static void main(String[] args)throws Exception {
        Map<Integer,Produs> produse;
        try(var fisier=new BufferedReader(new FileReader("produse.txt"))){
            produse=fisier.lines().map(Produs::new).collect(Collectors.toMap(p->p.getCodProdus(),p->p));
        }

        try(var fisier=new FileReader("tranzactii.json")){
            var jTranzactii=new JSONArray(new JSONTokener(fisier));
            for(int i=0;i<jTranzactii.length();i++){
                var jTran=jTranzactii.getJSONObject(i);
                var tranzactie=new Tranzactie(jTran.getInt("codProdus"),jTran.getInt("cantitate"),jTran.getString("tip"));
                produse.get(tranzactie.getCodProdus()).adaugaTranzactie(tranzactie);
            }
        }
        System.out.printf("CERINTA 1: %d PRODUSE%n%n",produse.size());
        //System.out.println(produse);
        System.out.println("CERINTA 2:");
        produse.values().stream().sorted((p1,p2)->p1.getDenumire().compareTo(p2.getDenumire())).forEach(System.out::println);
        System.out.println("Cerinta 3:");
        try(var fisier=new PrintWriter(new FileWriter("lista.txt"))){
        produse.values().stream().sorted((p1,p2)->Integer.compare(p1.getNrTranzactii(),p2.getNrTranzactii()))
                .forEach(p-> fisier.println(p.getDenumire()+", "+p.getNrTranzactii()));}
        var valoare=produse.values().stream().mapToDouble(Produs::getValoare).sum();
        System.out.printf("CERINTA 4: %5.2f RON%n",valoare);

        final int PORT=3929;
        new Thread(()->{
            try(var server=new ServerSocket(PORT);
            var socket=server.accept();
            var in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            var out=new PrintWriter(socket.getOutputStream(),true);
            ){
                var codProdus=Integer.parseInt(in.readLine());
                System.out.println("SERVER: AM PRIMIT "+codProdus);
                out.println(produse.get(codProdus).getValoare());
            }catch(Exception e){
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(500);
        try(
            var socket=new Socket("localhost",PORT);
            var out=new PrintWriter(socket.getOutputStream(),true);
            var in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ){
         out.println(3);
            System.out.println("CLIENT: am primit "+in.readLine());
        }catch (Exception e){e.printStackTrace();}
        }

    }
