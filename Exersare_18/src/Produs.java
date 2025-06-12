import java.util.ArrayList;
import java.util.List;

public class Produs {
    int codProdus;
    String denumire;
    double pret;
    List<Tranzactie> tranzactii;
    public Produs(String linie){
        this.codProdus = Integer.parseInt(linie.split(",")[0]);
        this.denumire=linie.split(",")[1];
        this.pret=Double.parseDouble(linie.split(",")[2]);
        this.tranzactii=new ArrayList<>();
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
    public int getNrTranz(){
        return tranzactii.size();
    }
    public double getValoare(){
        return pret*cantitate;
    }

    @Override
    public String toString() {
        return "Produs{" +
                "codProdus=" + codProdus +
                ", denumire='" + denumire + '\'' +
                ", pret=" + pret +
                ", tranzactii=" + tranzactii +
                ", cantitate=" + cantitate +
                '}';
    }
}
