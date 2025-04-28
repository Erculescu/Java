import java.util.Comparator;

public class Produs {
    private final int cod;
    private String denumire;
    private double cantitate;
    private double pret;
    public double Valoare=pret*cantitate;

    public Produs(int cod, String denumire, double cantitate, double pret) {
        this.cod = cod;
        this.denumire = denumire;
        this.cantitate = cantitate;
        this.pret = pret;
        this.Valoare=pret*cantitate;
    }

    public int getCod() {
        return cod;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public double getCantitate() {
        return cantitate;
    }

    public void setCantitate(double cantitate) {
        this.cantitate = cantitate;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public double getValoare() {
        return Valoare;
    }

    @Override
    public String toString() {
        return "Produs{" +
                "cod=" + cod +
                ", denumire='" + denumire + '\'' +
                ", cantitate=" + cantitate +
                ", pret=" + pret +
                ", Valoare=" + Valoare +
                '}';
    }

}

class ProdusComparator implements Comparator<Produs>{
    @Override
    public int compare(Produs o1,Produs o2){
        return o1.getDenumire().compareTo(o2.getDenumire());
    }
}