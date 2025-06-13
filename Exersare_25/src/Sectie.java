import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class Pacient{
    String cnp;
    String nume;
    LocalDate dataN;
    boolean esteAsigurat;
    String diagnostic;
    String telefon;
    String TipUrgenta;

    public Pacient(String cnp, String nume, LocalDate dataN, boolean esteAsigurat, String diagnostic, String telefon, String tipUrgenta) {
        this.cnp = cnp;
        this.nume = nume;
        this.dataN = dataN;
        this.esteAsigurat = esteAsigurat;
        this.diagnostic = diagnostic;
        this.telefon = telefon;
        TipUrgenta = tipUrgenta;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public LocalDate getDataN() {
        return dataN;
    }

    public void setDataN(LocalDate dataN) {
        this.dataN = dataN;
    }

    public boolean isEsteAsigurat() {
        return esteAsigurat;
    }

    public void setEsteAsigurat(boolean esteAsigurat) {
        this.esteAsigurat = esteAsigurat;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getTipUrgenta() {
        return TipUrgenta;
    }

    public void setTipUrgenta(String tipUrgenta) {
        TipUrgenta = tipUrgenta;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    @Override
    public String toString() {
        return "Pacient{" +
                "cnp='" + cnp + '\'' +
                ", nume='" + nume + '\'' +
                ", dataN=" + dataN +
                ", esteAsigurat=" + esteAsigurat +
                ", telefon='" + telefon + '\'' +
                ", TipUrgenta='" + TipUrgenta + '\'' +
                '}';
    }
}
public class Sectie {
    int codSectie;
    String numeSectie;
    int capacitateSectie;
    List<Pacient> pacienti;

    public Sectie(int codSectie, String numeSectie, int capacitateSectie) {
        this.codSectie = codSectie;
        this.numeSectie = numeSectie;
        this.capacitateSectie = capacitateSectie;
        this.pacienti=new ArrayList<>();
    }
    public void adaugaPacient(Pacient p){
        pacienti.add(p);
    }

    public int getCodSectie() {
        return codSectie;
    }

    public void setCodSectie(int codSectie) {
        this.codSectie = codSectie;
    }

    public String getNumeSectie() {
        return numeSectie;
    }

    public void setNumeSectie(String numeSectie) {
        this.numeSectie = numeSectie;
    }

    public int getCapacitateSectie() {
        return capacitateSectie;
    }

    public void setCapacitateSectie(int capacitateSectie) {
        this.capacitateSectie = capacitateSectie;
    }

    @Override
    public String toString() {
        return "Sectie{" +
                "codSectie=" + codSectie +
                ", numeSectie='" + numeSectie + '\'' +
                ", capacitateSectie=" + capacitateSectie +
                ", pacienti=" + pacienti +
                '}';
    }
}
