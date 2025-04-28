import java.util.List;

public class Student {
    private final int id;
    private final String nume;
    private List<Materie> materii;
    private double medieFinala;

    public Student(int id, String nume, List<Materie> materii) {
        this.id = id;
        this.nume = nume;
        this.materii = materii;
        this.medieFinala=0.0;
        for(Materie m:materii){
            medieFinala+=m.getMedie();
        }
        this.medieFinala=medieFinala/materii.size();
    }

    public int getId() {
        return id;
    }

    public List<Materie> getMaterii() {
        return materii;
    }

    public void setMaterii(List<Materie> materii) {
        this.materii = materii;
    }

    public String getNume() {
        return nume;
    }

    public double getMedieFinala() {
        return medieFinala;
    }

    public void AdaugaMaterie(Materie materie){
        this.materii.add(materie);
        this.medieFinala=0.0;
        for(Materie m:materii){
            medieFinala+=m.getMedie();
        }
        this.medieFinala=medieFinala/materii.size();
    }
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", materii=" + materii +
                ", medieFinala=" + medieFinala +
                '}';
    }
}
