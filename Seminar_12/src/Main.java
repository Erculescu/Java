import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

enum TipTranzactie{
    INTRARE ,IESIRE
}
class Tranzactie{
    private final TipTranzactie tip;
    private final int cantitate;

    public Tranzactie(TipTranzactie tip, int cantitate) {
        this.tip = tip;
        this.cantitate = cantitate;
    }

    public TipTranzactie getTip() {
        return tip;
    }

    public int getCantitate() {
        return cantitate;
    }

    @Override
    public String toString() {
        return "Tranzactie{" +
                "tip=" + tip +
                ", cantitate=" + cantitate +
                '}';
    }
}
class Produs{
    private final int cod;
    private final String denumire;
    private final List<Tranzactie> tranzactii;

    public Produs(int cod, String denumire, List<Tranzactie> tranzactii) {
        this.cod = cod;
        this.denumire = denumire;
        this.tranzactii = tranzactii;
    }

    public int getCod() {
        return cod;
    }

    public String getDenumire() {
        return denumire;
    }

    public List<Tranzactie> getTranzactii() {
        return tranzactii;
    }

    @Override
    public String toString() {
        return "Produs{" +
                "cod=" + cod +
                ", denumire='" + denumire + '\'' +
                ", tranzactii=" + tranzactii +
                '}';
    }

}
class Main {
    static List<Produs> citireXML(String caleFisier)throws Exception{
        var produse=new ArrayList<Produs>();
        var factory=DocumentBuilderFactory.newInstance();
        var builder=factory.newDocumentBuilder();
        var document=builder.parse(caleFisier);

        var nodProduse=document.getDocumentElement();
        var noduriProduse=nodProduse.getElementsByTagName("produs");
        for (var i=0;i<noduriProduse.getLength();i++){
            var nodProdus=(Element)noduriProduse.item(i);
            var cod=Integer.parseInt(nodProdus.getElementsByTagName("cod").item(0).getTextContent());
            var denumire=nodProdus.getElementsByTagName("denumire").item(0).getTextContent();
            var tranzactii=new ArrayList<Tranzactie>();
            var noduriTranzactie=nodProdus.getElementsByTagName("tranzactie");
            for (var j=0;j<noduriTranzactie.getLength();j++){
                var nodTran=(Element)noduriTranzactie.item(j);
                tranzactii.add(new Tranzactie(TipTranzactie.valueOf(nodTran.getAttribute("tip").toUpperCase()),
                        Integer.parseInt(nodTran.getAttribute("cantitate"))));
            }

            produse.add(new Produs(cod,denumire,tranzactii));
        }
        //System.out.println(nodProduse);
        return  produse;
    }
    static void salvareJson(String caleFisier,List<Produs> produse)throws Exception{
        var jsonProduse=new JSONArray();
        for(var produs:produse){
            var jsonProdus=new JSONObject();
            jsonProdus.put("cod",produs.getCod());
            jsonProdus.put("denumire",produs.getDenumire());
            var jsonTranzactii=new JSONArray();
            for(var tranzactie:produs.getTranzactii()){
                var jsonTranzactie=new JSONObject();
                jsonTranzactie.put("tip",tranzactie.getTip());
                jsonTranzactie.put("cantitate",tranzactie.getCantitate());
                jsonTranzactii.put(jsonTranzactie);
            }
            jsonProdus.put("tranzactii",jsonTranzactii);
            jsonProduse.put(jsonProdus);
        }
            try(var fisier=new FileWriter("produse.json")){
                jsonProduse.write(fisier,3,0);
        }
    }

    static List<Produs> citireJson(String caleFisier)throws Exception{
        var produse=new ArrayList<Produs>();
        try(var fisier=new FileInputStream(caleFisier)){
            var jsonProduse=new JSONArray(new JSONTokener(fisier));
            for(var i=0;i<jsonProduse.length();i++){
                var jsonProd=jsonProduse.getJSONObject(i);
                //System.out.println(jsonProd);
                var tranzactii=new ArrayList<Tranzactie>();
                var jsonTranzactii=jsonProd.getJSONArray("tranzactii");
                for(var j=0;j<jsonTranzactii.length();j++){
                    var jsonTran=jsonTranzactii.getJSONObject(j);
                    tranzactii.add(new Tranzactie(TipTranzactie.valueOf(jsonTran.getString("tip")),
                            jsonTran.getInt("cantitate")));
                }
                produse.add(new Produs(jsonProd.getInt("cod"),jsonProd.getString("denumire"),tranzactii));
            }

        }
        return produse;
    }

    public static void main(String[] args) throws Exception {
//        var produs=new Produs(1,"Mere",List.of(new Tranzactie(TipTranzactie.INTRARE,3),new Tranzactie(TipTranzactie.IESIRE,2)));
//        System.out.println(produs);
        var produse=citireXML("produse.xml");
        salvareJson("produse.Json",produse);
        produse=citireJson("produse.json");
        produse.forEach(System.out::println);
    }
}