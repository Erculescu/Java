import java.util.ArrayList;
import java.util.List;

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

    public void setCodProdus(int codProdus) {
        this.codProdus = codProdus;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
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
public class Produs {
    int codProdus;
    String denumire;
    double pret;
    int cantitate;
    List<Tranzactie> tranzactii;

    public Produs(double pret, String denumire, int codProdus) {
        this.pret = pret;
        this.denumire = denumire;
        this.codProdus = codProdus;
        this.cantitate=0;
        this.tranzactii=new ArrayList<>();
    }
    public void adaugaTranzactie(Tranzactie tranzactie){
        tranzactii.add(tranzactie);
        if(tranzactie.tip.equals("intrare")){
            this.cantitate+= tranzactie.cantitate;
        }else{
            this.cantitate-= tranzactie.cantitate;
        }
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

    @Override
    public String toString() {
        return "Produs{" +
                "codProdus=" + codProdus +
                ", denumire='" + denumire + '\'' +
                ", pret=" + pret +
                ", cantitate=" + cantitate +
                ", tranzactii=" + tranzactii +
                '}';
    }
}
