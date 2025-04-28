import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Materie {
    private String denumire;
    private List<Double> note;
    private double Medie;

    public Materie(List<Double> note, String denumire) {
        this.note = note;
        this.denumire = denumire;
        this.Medie=0.0;
        for( Double nota: note) {
            this.Medie += nota;
        }
        this.Medie=Medie/note.size();
    }
    public Materie(){
        this.note=new ArrayList<>();
    }
    public void adaugaNota(double nota){
        this.note.add(nota);
        this.Medie=0.0;
        for(Double notaTemp : note) {
            this.Medie += notaTemp;
        }
        this.Medie=Medie/note.size();
    }

    public String getDenumire() {
        return denumire;
    }

    public List<Double> getNote() {
        return note;
    }

    public double getMedie() {
        return Medie;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public void setNote(List<Double> note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Materie{" +
                "denumire='" + denumire + '\'' +
                ", note=" + note +
                ", Medie=" + Medie +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Materie materie = (Materie) o;
        return Objects.equals(denumire, materie.denumire);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(denumire);
    }
}
