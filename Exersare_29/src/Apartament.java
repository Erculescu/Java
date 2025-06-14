import java.util.ArrayList;
import java.util.List;

final class Utilitati{
    private final String denumire;
    private final String repartizare;
    private final double valoare;

    public Utilitati(String denumire, String repartizare, double valoare) {
        this.denumire = denumire;
        this.repartizare = repartizare;
        this.valoare = valoare;
    }

    public String getDenumire() {
        return denumire;
    }

    public String getRepartizare() {
        return repartizare;
    }

    public double getValoare() {
        return valoare;
    }


    @Override
    public String toString() {
        return "Utilitati{" +
                "denumire='" + denumire + '\'' +
                ", repartizare='" + repartizare + '\'' +
                ", valoare=" + valoare +
                '}';
    }
}
public class Apartament{
    int nrApartament;
    int suprafata;
    int persoane;
    List<Utilitati> utilitati;

    public Apartament(int nrApartament, int suprafata, int persoane) {
        this.nrApartament = nrApartament;
        this.suprafata = suprafata;
        this.persoane = persoane;
        this.utilitati=new ArrayList<>();
    }
    public void adaugaUtilitate(Utilitati utilitate){
        utilitati.add(utilitate);
    }

    public int getNrApartament() {
        return nrApartament;
    }

    public void setNrApartament(int nrApartament) {
        this.nrApartament = nrApartament;
    }

    public int getSuprafata() {
        return suprafata;
    }

    public void setSuprafata(int suprafata) {
        this.suprafata = suprafata;
    }

    public int getPersoane() {
        return persoane;
    }

    public void setPersoane(int persoane) {
        this.persoane = persoane;
    }

    public void setUtilitati(List<Utilitati> utilitati) {
        this.utilitati = utilitati;
    }

    public double getFactura(){
    double suma=0;
    for(Utilitati utilitati :this.utilitati){
        if(utilitati.getRepartizare().equals("apartament")){
            suma+= utilitati.getValoare();
        }else{
            if(utilitati.getRepartizare().equals("suprafata")){
                suma+=utilitati.getValoare()*this.suprafata;
            } else  {
                suma+= utilitati.getValoare()*this.persoane;
            }
        }
    }
    return suma;
    }

    @Override
    public String toString() {
        return "Apartament{" +
                "nrApartament=" + nrApartament +
                ", suprafata=" + suprafata +
                ", persoane=" + persoane +
                ", utilitati=" + utilitati +
                '}';
    }
}