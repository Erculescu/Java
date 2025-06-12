import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class Revizie{
    String firma;
    int kilometraj;
    double taxa;
    LocalDate data;
    boolean revenire;

    public Revizie(String firma, int kilometraj, double taxa, LocalDate data, boolean revenire) {
        this.firma = firma;
        this.kilometraj = kilometraj;
        this.taxa = taxa;
        this.data = data;
        this.revenire = revenire;
    }

    @Override
    public String toString() {
        return "Revizie{" +
                "firma='" + firma + '\'' +
                ", kilometraj=" + kilometraj +
                ", taxa=" + taxa +
                ", data=" + data +
                ", revenire=" + revenire +
                '}';
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public int getKilometraj() {
        return kilometraj;
    }

    public void setKilometraj(int kilometraj) {
        this.kilometraj = kilometraj;
    }

    public double getTaxa() {
        return taxa;
    }

    public void setTaxa(double taxa) {
        this.taxa = taxa;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public boolean isRevenire() {
        return revenire;
    }

    public void setRevenire(boolean revenire) {
        this.revenire = revenire;
    }
}
public class Vehicul {
    String nrInmatriculare;
    int anFabricatei;
    List<Revizie> revizii;
    String combustibil;

    public String getCombustibil() {
        return combustibil;
    }

    public void setCombustibil(String combustibil) {
        this.combustibil = combustibil;
    }

    public Vehicul(String nrInmatriculare, int anFabricatei) {
        this.nrInmatriculare = nrInmatriculare;
        this.anFabricatei = anFabricatei;
        this.revizii=new ArrayList<>();
    }
    public void adaugaRevizie(Revizie revizie){
        this.revizii.add(revizie);
    }

    public String getNrInmatriculare() {
        return nrInmatriculare;
    }

    public void setNrInmatriculare(String nrInmatriculare) {
        this.nrInmatriculare = nrInmatriculare;
    }

    public int getAnFabricatei() {
        return anFabricatei;
    }

    public void setAnFabricatei(int anFabricatei) {
        this.anFabricatei = anFabricatei;
    }

    @Override
    public String toString() {
        return "Vehicul{" +
                "nrInmatriculare='" + nrInmatriculare + '\'' +
                ", anFabricatei=" + anFabricatei +", tipVehicul "+combustibil+
                ", revizii=" + revizii +
                '}';
    }
}
