import java.util.ArrayList;
import java.util.List;

class optiune{
    int codLiceu;
    int codSpecializare;

    public optiune(int codLiceu, int codSpecializare) {
        this.codLiceu = codLiceu;
        this.codSpecializare = codSpecializare;
    }

    public int getCodLiceu() {
        return codLiceu;
    }

    public void setCodLiceu(int codLiceu) {
        this.codLiceu = codLiceu;
    }

    public int getCodSpecializare() {
        return codSpecializare;
    }

    public void setCodSpecializare(int codSpecializare) {
        this.codSpecializare = codSpecializare;
    }

    @Override
    public String toString() {
        return "optiune{" +
                "codLiceu=" + codLiceu +
                ", codSpecializare=" + codSpecializare +
                '}';
    }
}
public class Candidat {
    int codCandidat;
    String nume;
    double medie;
    List<optiune> optiuni;

    public Candidat(int codCandidat, String nume, double medie) {
        this.codCandidat = codCandidat;
        this.nume = nume;
        this.medie = medie;
        this.optiuni=new ArrayList<>();
    }
    public void adaugaOptiune(optiune op){
        optiuni.add(op);
    }

    public int getCodCandidat() {
        return codCandidat;
    }

    public void setCodCandidat(int codCandidat) {
        this.codCandidat = codCandidat;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public double getMedie() {
        return medie;
    }

    public void setMedie(double medie) {
        this.medie = medie;
    }

    @Override
    public String toString() {
        return "Candidat{" +
                "codCandidat=" + codCandidat +
                ", nume='" + nume + '\'' +
                ", medie=" + medie +
                ", optiuni=" + optiuni +
                '}';
    }


    public int compareTo(Candidat other){
        if(this.optiuni.size()>other.optiuni.size()){
            return 1;
        } else if (this.optiuni.size()<other.optiuni.size()) {
            return -1;

        }else if(this.medie>other.medie){
            return 1;
        }else {
            return -1;
        }
    }

}
