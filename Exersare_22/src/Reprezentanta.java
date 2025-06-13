import java.util.ArrayList;
import java.util.List;

class Vehicul {
    String VIN;
    int putere;
    double pret;

    public Vehicul(String VIN, int putere, double pret) {
        this.VIN = VIN;
        this.putere = putere;
        this.pret = pret;
    }

    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }

    public int getPutere() {
        return putere;
    }

    public void setPutere(int putere) {
        this.putere = putere;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    @Override
    public String toString() {
        return "Vehicul{" +
                "VIN='" + VIN + '\'' +
                ", putere=" + putere +
                ", pret=" + pret +
                '}';
    }
}
public class Reprezentanta {
    String CUI;
    String denumire;
    List<Vehicul> vehicule;

    public Reprezentanta(String CUI, String denumire) {
        this.CUI = CUI;
        this.denumire = denumire;
        this.vehicule=new ArrayList<>();
    }

    public String getCUI() {
        return CUI;
    }

    public void setCUI(String CUI) {
        this.CUI = CUI;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    @Override
    public String toString() {
        return "Reprezentanta{" +
                "CUI='" + CUI + '\'' +
                ", denumire='" + denumire + '\'' +
                ", vehicule=" + vehicule +
                '}';
    }
}
