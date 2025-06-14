import java.util.ArrayList;
import java.util.List;

final class Pacient{
    private  final String cnp;
    private final String nume;
    private final int varsta;
    private final int codSectie;

    public Pacient(String cnp, String nume, int varsta, int codSectie) {
        this.cnp = cnp;
        this.nume = nume;
        this.varsta = varsta;
        this.codSectie = codSectie;
    }

    public String getCnp() {
        return cnp;
    }

    public int getCodSectie() {
        return codSectie;
    }

    public int getVarsta() {
        return varsta;
    }

    public String getNume() {
        return nume;
    }

    @Override
    public String toString() {
        return "Pacient{" +
                "cnp='" + cnp + '\'' +
                ", nume='" + nume + '\'' +
                ", varsta=" + varsta +
                ", codSectie=" + codSectie +
                '}';
    }
}
public class Sectie {
    int codSectie;
    String denumire;
    int capacitate;
    List<Pacient> pacienti;
    int locuriDisponibile;

    public Sectie(int codSectie, String denumire, int capacitate) {
        this.codSectie = codSectie;
        this.denumire = denumire;
        this.capacitate = capacitate;
        this.locuriDisponibile=capacitate;
        this.pacienti=new ArrayList<>();
    }
    public void adaugaPacient(Pacient p){
        pacienti.add(p);
        locuriDisponibile--;
    }

    public int getCodSectie() {
        return codSectie;
    }

    public void setCodSectie(int codSectie) {
        this.codSectie = codSectie;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public int getCapacitate() {
        return capacitate;
    }

    public void setCapacitate(int capacitate) {
        this.capacitate = capacitate;
    }
    public double getVarstaMedie(){
        double varsta=0.0;
        for(Pacient p:pacienti){
            varsta+=p.getVarsta();
        }
        varsta=varsta/pacienti.size();
        return varsta;
    }

    @Override
    public String toString() {
        return "Sectie{" +
                "codSectie=" + codSectie +
                ", denumire='" + denumire + '\'' +
                ", capacitate=" + capacitate +
                ", pacienti=" + pacienti +
                ", locuriDisponibile=" + locuriDisponibile +
                '}';
    }
}
