import java.util.ArrayList;
import java.util.List;

class Student{
    long cod_candidat;
    String nume;
    double notaBac;
    int cod_Specializare;

    public Student(long cod_candidat, String nume, double notaBac, int cod_Specializare) {
        this.cod_candidat = cod_candidat;
        this.nume = nume;
        this.notaBac = notaBac;
        this.cod_Specializare = cod_Specializare;
    }

    public long getCod_candidat() {
        return cod_candidat;
    }

    public void setCod_candidat(long cod_candidat) {
        this.cod_candidat = cod_candidat;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public double getNotaBac() {
        return notaBac;
    }

    public void setNotaBac(double notaBac) {
        this.notaBac = notaBac;
    }

    public int getCod_Specializare() {
        return cod_Specializare;
    }

    public void setCod_Specializare(int cod_Specializare) {
        this.cod_Specializare = cod_Specializare;
    }

    @Override
    public String toString() {
        return "Student{" +
                "cod_candidat=" + cod_candidat +
                ", nume='" + nume + '\'' +
                ", notaBac=" + notaBac +
                ", cod_Specializare=" + cod_Specializare +
                '}';
    }
}
public class specializare {
    int cod;
    String denumire;
    private final int locuri;
    int locuriDisponibile;
    List<Student> studenti;

    public specializare(int cod, String denumire, int locuri) {
        this.cod = cod;
        this.denumire = denumire;
        this.locuri = locuri;
        this.locuriDisponibile=locuri;
        this.studenti=new ArrayList<>();
    }
    public void adaugaStudent(Student s){
        studenti.add(s);
        this.locuriDisponibile--;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public int getLocuri() {
        return locuri;
    }

    public int getLocuriDisponibile() {
        return locuriDisponibile;
    }
    public Double getMedie(){
        Double medie=0.0;
        for(Student stud:studenti){
            medie+= stud.getNotaBac();
        }
        return (medie/studenti.size());
    }

    @Override
    public String toString() {
        return "specializare{" +
                "cod=" + cod +
                ", denumire='" + denumire + '\'' +
                ", locuri=" + locuri +
                ", locuriDisponibile=" + locuriDisponibile +
                ", studenti=" + studenti +
                '}';
    }
}

