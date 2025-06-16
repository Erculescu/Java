import java.util.HashMap;
import java.util.Map;

class Manevra {
    int cod;
    int durata;
    double tarif;

    public Manevra(int cod, int durata, double tarif) {
        this.cod = cod;
        this.durata = durata;
        this.tarif = tarif;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public double getTarif() {
        return tarif;
    }

    public void setTarif(double tarif) {
        this.tarif = tarif;
    }

    @Override
    public String toString() {
        return "Manevra{" +
                "cod=" + cod +
                ", durata=" + durata +
                ", tarif=" + tarif +
                '}';
    }
}
class consultatie{
    String spec;
    int codManevra;
    int numar;

    public consultatie(String spec, int codManevra, int numar) {
        this.spec = spec;
        this.codManevra = codManevra;
        this.numar = numar;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public int getCodManevra() {
        return codManevra;
    }

    public void setCodManevra(int codManevra) {
        this.codManevra = codManevra;
    }

    public int getNumar() {
        return numar;
    }

    public void setNumar(int numar) {
        this.numar = numar;
    }

    @Override
    public String toString() {
        return "consultatie{" +
                "spec='" + spec + '\'' +
                ", codManevra=" + codManevra +
                ", numar=" + numar +
                '}';
    }
}
public class Specialitate {
    String denumire;
    Map<Integer,Manevra> manevre;
    Map<String,consultatie> consultatii;

    public Specialitate(String denumire) {
        this.denumire = denumire;
        this.manevre=new HashMap<>();
        this.consultatii=new HashMap<>();
    }
    public void adaugaManevra(Manevra m){
        manevre.put(m.cod,m);
    }
    public void adaugaConsultatie(consultatie c){
        consultatii.put(c.spec,c);
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }
    public double getVenit(){
        double venit=0;
        for(consultatie c:consultatii.values()){
            venit+=c.numar*manevre.get(c.codManevra).tarif;
        }
        return venit;
    }


    @Override
    public String toString() {
        return "Specialitate{" +
                "denumire='" + denumire + '\'' +
                ", manevre=" + manevre +
                ", consultatii=" + consultatii +
                '}';
    }
}
