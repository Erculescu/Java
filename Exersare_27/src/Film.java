import java.util.ArrayList;
import java.util.List;

final class Tranzactii{
    private final int codProdus;
    private final int cantitate;
    private final String tip;

    public Tranzactii(int codProdus, int cantitate, String tip) {
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
        return "Tranzactii{" +
                "codProdus=" + codProdus +
                ", cantitate=" + cantitate +
                ", tip='" + tip + '\'' +
                '}';
    }
}
public class Film {
    int codProdus;
    String Denumire;
    Double pret;
    int stoc=0;
    double valoareStoc=0;
    List<Tranzactii> tranzactii;

    public Film(int codProdus, String denumire, Double pret) {
        this.codProdus = codProdus;
        Denumire = denumire;
        this.pret = pret;
        this.stoc = 0;
        this.valoareStoc = 0;
        this.tranzactii=new ArrayList<>();
    }
    public void adaugaTranzactie(Tranzactii tranzactie){
        tranzactii.add(tranzactie);
        if(tranzactie.getTip().equals("vanzare")){
            this.stoc-=tranzactie.getCantitate();
        }else{
            this.stoc+=tranzactie.getCantitate();
        }
        this.valoareStoc=this.pret*this.stoc;
    }

    public double getValoareStoc() {
        return stoc*pret;
    }

    public int getCodProdus() {
        return codProdus;
    }

    public void setCodProdus(int codProdus) {
        this.codProdus = codProdus;
    }

    public String getDenumire() {
        return Denumire;
    }

    public void setDenumire(String denumire) {
        Denumire = denumire;
    }

    public Double getPret() {
        return pret;
    }

    public void setPret(Double pret) {
        this.pret = pret;
    }

    public int getStoc() {
        return stoc;
    }

    public void setStoc(int stoc) {
        this.stoc = stoc;
    }

    @Override
    public String toString() {
        return "Film{" +
                "codProdus=" + codProdus +
                ", Denumire='" + Denumire + '\'' +
                ", pret=" + pret +
                ", stoc=" + stoc +
                ", valoareStoc=" + valoareStoc +
                ", tranzactii=" + tranzactii +
                '}';
    }
}
