import java.util.ArrayList;
import java.util.List;

class Student{
    long cnp;
    String nume;
    double notaBac;
    int codSpecializareAleasa;

    public Student(long cnp, String nume, double notaBac, int codSpecializareAleasa) {
        this.cnp = cnp;
        this.nume = nume;
        this.notaBac = notaBac;
        this.codSpecializareAleasa = codSpecializareAleasa;
    }

    @Override
    public String toString() {
        return "Student{" +
                "cnp=" + cnp +
                ", nume='" + nume + '\'' +
                ", notaBac=" + notaBac +
                ", codSpecializareAleasa=" + codSpecializareAleasa +
                '}';
    }

    public long getCnp() {
        return cnp;
    }

    public void setCnp(long cnp) {
        this.cnp = cnp;
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

    public int getCodSpecializareAleasa() {
        return codSpecializareAleasa;
    }

    public void setCodSpecializareAleasa(int codSpecializareAleasa) {
        this.codSpecializareAleasa = codSpecializareAleasa;
    }
}
 class Specializari {
    int cod;
    String denumire;
    int locuri;
    int locuriDisponibile;
    List<Student> studenti;

     public Specializari(int cod, String denumire, int locuri) {
         this.cod = cod;
         this.denumire = denumire;
         this.locuri = locuri;
         this.locuriDisponibile = locuri;
         this.studenti=new ArrayList<>();
     }
     public void adaugaStudent(Student stud){
         studenti.add(stud);
         this.locuriDisponibile-=1;
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

     public void setLocuri(int locuri) {
         this.locuri = locuri;
     }

     public int getLocuriDisponibile() {
         return locuriDisponibile;
     }

     public void setLocuriDisponibile(int locuriDisponibile) {
         this.locuriDisponibile = locuriDisponibile;
     }

     @Override
     public String toString() {
         return "Specializari{" +
                 "cod=" + cod +
                 ", denumire='" + denumire + '\'' +
                 ", locuri=" + locuri +
                 ", locuriDisponibile=" + locuriDisponibile +
                 ", studenti=" + studenti +
                 '}';
     }
 }
