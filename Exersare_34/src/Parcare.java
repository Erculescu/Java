import java.util.ArrayList;
import java.util.List;

class Vehicul{
    int codParcare;
    String numarInmatriculare;
    int durata;

    public Vehicul(int codParcare, String numarInmatriculare, int durata) {
        this.codParcare = codParcare;
        this.numarInmatriculare = numarInmatriculare;
        this.durata = durata;
    }

    public int getCodParcare() {
        return codParcare;
    }

    public void setCodParcare(int codParcare) {
        this.codParcare = codParcare;
    }

    public String getNumarInmatriculare() {
        return numarInmatriculare;
    }

    public void setNumarInmatriculare(String numarInmatriculare) {
        this.numarInmatriculare = numarInmatriculare;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    @Override
    public String toString() {
        return "Vehicul{" +
                "codParcare=" + codParcare +
                ", numarInmatriculare='" + numarInmatriculare + '\'' +
                ", durata=" + durata +
                '}';
    }
}
public class Parcare {
    int codParcare;
    String adresa;
    double tarifOra;
    List<Vehicul> vehiculeParcate;

    public Parcare(int codParcare, String adresa, double tarifOra) {
        this.codParcare = codParcare;
        this.adresa = adresa;
        this.tarifOra = tarifOra;
        this.vehiculeParcate=new ArrayList<>();
    }
    public void adaugaVehicul(Vehicul vehicul){
        vehiculeParcate.add(vehicul);
    }

    public int getCodParcare() {
        return codParcare;
    }

    public void setCodParcare(int codParcare) {
        this.codParcare = codParcare;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public double getTarifOra() {
        return tarifOra;
    }

    public void setTarifOra(double tarifOra) {
        this.tarifOra = tarifOra;
    }

    @Override
    public String toString() {
        return "Parcare{" +
                "codParcare=" + codParcare +
                ", adresa='" + adresa + '\'' +
                ", tarifOra=" + tarifOra +
                ", vehiculeParcate=" + vehiculeParcate +
                '}';
    }
}
