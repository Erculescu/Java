import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Tranzactie{
    int cod;
    String simbol;
    String tip;
    int cantitate;
    float pret;

    public Tranzactie(int cod, String simbol, String tip, int cantitate, float pret) {
        this.cod = cod;
        this.simbol = simbol;
        this.tip = tip;
        this.cantitate = cantitate;
        this.pret = pret;
    }

    @Override
    public String toString() {
        return "Tranzactie{" +
                "cod=" + cod +
                ", simbol='" + simbol + '\'' +
                ", tip='" + tip + '\'' +
                ", cantitate=" + cantitate +
                ", pret=" + pret +
                '}';
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getSimbol() {
        return simbol;
    }

    public void setSimbol(String simbol) {
        this.simbol = simbol;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public float getPret() {
        return pret;
    }

    public void setPret(float pret) {
        this.pret = pret;
    }
}
public class Client {
    int cod;
    String cnp;
    String nume;
    List<Tranzactie> tranzactii;

    public Client(int cod, String cnp, String nume) {
        this.cod = cod;
        this.cnp = cnp;
        this.nume = nume;
        this.tranzactii=new ArrayList<>();
    }
    public void adaugaTranzactie(Tranzactie tranz){
        tranzactii.add(tranz);
    }
    @Override
    public String toString() {
        return "Client{" +
                "cod=" + cod +
                ", cnp='" + cnp + '\'' +
                ", nume='" + nume + '\'' +
                ", tranzactii=" + tranzactii +
                '}';
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }
    public void calculPortofoliu(){
        Map<String,Integer> hartaSimboluri=new HashMap<>();
        for (Tranzactie tranz:tranzactii){
            if(tranz.getTip().equals("cumparare")){
                    hartaSimboluri.put(tranz.simbol,hartaSimboluri.getOrDefault(tranz.getSimbol(),0)+tranz.getCantitate());
            }else{
                hartaSimboluri.put(tranz.simbol,hartaSimboluri.getOrDefault(tranz.getSimbol(),0)-tranz.getCantitate());
            }
        }
        for(Map.Entry<String,Integer> intrare: hartaSimboluri.entrySet()){
            System.out.println("\t"+intrare.getKey()+" "+intrare.getValue());
        }
    }

}
