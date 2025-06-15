import java.util.ArrayList;
import java.util.List;

class rezervare{
    int id_rezervare;
    int cod_aventura;
    int nr_locuri;

    public rezervare(int id_rezervare, int cod_aventura, int nr_locuri) {
        this.id_rezervare = id_rezervare;
        this.cod_aventura = cod_aventura;
        this.nr_locuri = nr_locuri;
    }

    public int getId_rezervare() {
        return id_rezervare;
    }

    public void setId_rezervare(int id_rezervare) {
        this.id_rezervare = id_rezervare;
    }

    public int getCod_aventura() {
        return cod_aventura;
    }

    public void setCod_aventura(int cod_aventura) {
        this.cod_aventura = cod_aventura;
    }

    public int getNr_locuri() {
        return nr_locuri;
    }

    public void setNr_locuri(int nr_locuri) {
        this.nr_locuri = nr_locuri;
    }

    @Override
    public String toString() {
        return "rezervare{" +
                "id_rezervare=" + id_rezervare +
                ", cod_aventura=" + cod_aventura +
                ", nr_locuri=" + nr_locuri +
                '}';
    }
}
public class Aventura {
    int cod_aventura;
    String denumire;
    double tarif;
    int locuri_disponibile;
    int locuri_ramase;
    List<rezervare> rezervari;

    public Aventura(int cod_aventura, String denumire, double tarif, int locuri_disponibile) {
        this.cod_aventura = cod_aventura;
        this.denumire = denumire;
        this.tarif = tarif;
        this.locuri_disponibile = locuri_disponibile;
        this.locuri_ramase=locuri_disponibile;
        this.rezervari=new ArrayList<>();
    }
    public void addRezervare(rezervare rez){
        this.rezervari.add(rez);
        this.locuri_ramase-= rez.getNr_locuri();
    }

    public int getLocuri_ramase() {
        return locuri_ramase;
    }

    public int getCod_aventura() {
        return cod_aventura;
    }

    public void setCod_aventura(int cod_aventura) {
        this.cod_aventura = cod_aventura;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public double getTarif() {
        return tarif;
    }

    public void setTarif(double tarif) {
        this.tarif = tarif;
    }

    public int getLocuri_disponibile() {
        return locuri_disponibile;
    }

    public void setLocuri_disponibile(int locuri_disponibile) {
        this.locuri_disponibile = locuri_disponibile;
    }

    @Override
    public String toString() {
        return "Aventura{" +
                "cod_aventura=" + cod_aventura +
                ", denumire='" + denumire + '\'' +
                ", tarif=" + tarif +
                ", locuri_disponibile=" + locuri_disponibile +
                ", locuri_ramase=" + locuri_ramase +
                ", rezervari=" + rezervari +
                '}';
    }
}
