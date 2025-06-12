import java.util.ArrayList;
import java.util.List;

class Examen{
    int sala;
    String materie;
    int capacitate_sala;
    int nrStudenti;
    int ora;

    public Examen(int sala, String materie, int capacitate_sala, int ora, int nrStudenti) {
        this.sala = sala;
        this.materie = materie;
        this.capacitate_sala = capacitate_sala;
        this.ora = ora;
        this.nrStudenti = nrStudenti;
        this.gradOcupare= (double) nrStudenti /capacitate_sala;
    }

    public int getSala() {
        return sala;
    }

    public void setSala(int sala) {
        this.sala = sala;
    }

    public String getMaterie() {
        return materie;
    }

    public void setMaterie(String materie) {
        this.materie = materie;
    }

    public int getCapacitate_sala() {
        return capacitate_sala;
    }

    public void setCapacitate_sala(int capacitate_sala) {
        this.capacitate_sala = capacitate_sala;
    }

    public int getNrStudenti() {
        return nrStudenti;
    }

    public void setNrStudenti(int nrStudenti) {
        this.nrStudenti = nrStudenti;
    }

    public int getOra() {
        return ora;
    }

    public void setOra(int ora) {
        this.ora = ora;
    }
    double gradOcupare;
    public double calculaGradOcupare() {
        return nrStudenti/capacitate_sala;
    }

    @Override
    public String toString() {
        return "Examen{" +
                "sala=" + sala +
                ", materie='" + materie + '\'' +
                ", capacitate_sala=" + capacitate_sala +
                ", nrStudenti=" + nrStudenti +
                ", ora=" + ora +
                ", gradOcupare=" + gradOcupare +
                '}';
    }
}
public class Profesor {
    String nume;
    String disciplina;
    List<Examen> examene;

    public Profesor(String nume, String disciplina) {
        this.nume = nume;
        this.disciplina = disciplina;
        this.examene=new ArrayList<>();
    }
    public void adaugaExamen(Examen examen){
        examene.add(examen);
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public String toString() {
        return "Profesor{" +
                "nume='" + nume + '\'' +
                ", disciplina='" + disciplina + '\'' +
                ", examene=" + examene +
                '}';
    }
}
