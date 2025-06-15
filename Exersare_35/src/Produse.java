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
public class Produse {
    int cod;
    String denumire;
    double pret;
    int stoc;
    List<Tranzactie> tranzactii;

    public Produse(int cod, String denumire, double pret) {
        this.cod = cod;
        this.denumire = denumire;
        this.pret = pret;
        this.stoc=0;
        this.tranzactii=new ArrayList<>();
    }
    public void adaugaTranzactie(Tranzactie tranz){
        tranzactii.add(tranz);
        if(tranz.tip.equals("intrare")){
            stoc+=tranz.cantitate;
        }else{
            stoc-=tranz.cantitate;
        }
    }

    public int getStoc() {
        return stoc;
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

    @Override
    public String toString() {
        return "Produse{" +
                "cod=" + cod +
                ", denumire='" + denumire + '\'' +
                ", pret=" + pret +
                ", stoc=" + stoc +
                ", tranzactii=" + tranzactii +
                '}';
    }
}
