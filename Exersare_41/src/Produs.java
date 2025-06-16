import java.util.ArrayList;
import java.util.List;

class Tranzactie{
    String tip;
    int cantitate;

    public Tranzactie(String tip, int cantitate) {
        this.tip = tip;
        this.cantitate = cantitate;
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

    @Override
    public String toString() {
        return "Tranzactie{" +
                "tip='" + tip + '\'' +
                ", cantitate=" + cantitate +
                '}';
    }
}
public class Produs {
    int cod;
    String denumire;
    double pret;
    List<Tranzactie> tranzactii;
    int stoc;
    double valoare_stoc;

    public Produs(int cod, String denumire, double pret) {
        this.cod = cod;
        this.denumire = denumire;
        this.pret = pret;
        this.tranzactii=new ArrayList<>();
        this.stoc=0;
        this.valoare_stoc=0;
    }
    public void adaugaTranzactie(Tranzactie tranzactie){
        tranzactii.add(tranzactie);
        if(tranzactie.getTip().equals("intrare")){
            this.stoc+= tranzactie.cantitate;
            this.valoare_stoc+=tranzactie.cantitate*this.pret;
        }else{
            this.stoc-= tranzactie.cantitate;
            this.valoare_stoc-=tranzactie.cantitate*this.pret;
        }
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
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

    public int getStoc() {
        return stoc;
    }

    public double getValoare_stoc() {
        return valoare_stoc;
    }

    @Override
    public String toString() {
        return "Produs{" +
                "cod=" + cod +
                ", denumire='" + denumire + '\'' +
                ", pret=" + pret +
                ", tranzactii=" + tranzactii +
                ", stoc=" + stoc +
                ", valoare_stoc=" + valoare_stoc +
                '}';
    }
}
