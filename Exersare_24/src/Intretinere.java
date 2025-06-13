import java.util.ArrayList;
import java.util.List;

class Cladire{
    String adresa;
    int locatari;
    String utilitati;

    public Cladire(String adresa, int locatari, String utilitati) {
        this.adresa = adresa;
        this.locatari = locatari;
        this.utilitati = utilitati;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public int getLocatari() {
        return locatari;
    }

    public void setLocatari(int locatari) {
        this.locatari = locatari;
    }

    public String getUtilitati() {
        return utilitati;
    }

    public void setUtilitati(String utilitati) {
        this.utilitati = utilitati;
    }

    @Override
    public String toString() {
        return "Cladire{" +
                "adresa='" + adresa + '\'' +
                ", locatari=" + locatari +
                ", utilitati='" + utilitati + '\'' +
                '}';
    }
}
public class Intretinere {
    int id;
    String adresa;
    double apa,gaz,curent;
    List<Cladire> cladiri;

    public Intretinere(int id, String adresa, double apa, double gaz, double curent) {
        this.id = id;
        this.adresa = adresa;
        this.apa = apa;
        this.gaz = gaz;
        this.curent = curent;
        this.cladiri=new ArrayList<>();
    }
    public void adaugaCladire(Cladire cladire){
        cladiri.add(cladire);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public double getApa() {
        return apa;
    }

    public void setApa(double apa) {
        this.apa = apa;
    }

    public double getGaz() {
        return gaz;
    }

    public void setGaz(double gaz) {
        this.gaz = gaz;
    }

    public double getCurent() {
        return curent;
    }

    public void setCurent(double curent) {
        this.curent = curent;
    }

    @Override
    public String toString() {
        return "Intretinere{" +
                "id=" + id +
                ", adresa='" + adresa + '\'' +
                ", apa=" + apa +
                ", gaz=" + gaz +
                ", curent=" + curent +
                ", cladiri=" + cladiri +
                '}';
    }
}
