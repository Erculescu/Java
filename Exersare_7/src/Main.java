import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
class Profesor{
    private final int idProf;
    private final String prenume;
    private final String nume;
    private final String dept;

    public Profesor(int idProf, String prenume, String nume, String dept) {
        this.idProf = idProf;
        this.prenume = prenume;
        this.nume = nume;
        this.dept = dept;
    }

    public int getIdProf() {
        return idProf;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getNume() {
        return nume;
    }

    public String getDept() {
        return dept;
    }

    @Override
    public String toString() {
        return "Profesor{" +
                "idProf=" + idProf +
                ", prenume='" + prenume + '\'' +
                ", nume='" + nume + '\'' +
                ", dept='" + dept + '\'' +
                '}';
    }
}
class Programare{
    private final String ziua;
    private final String interval;
    private final Profesor profesor;
    private final String disciplina;
    private final String sala;
    private final boolean esteCurs;
    private final String formatie;

    public Programare(String ziua, String interval, Profesor profesor, String disciplina, String sala, boolean esteCurs, String formatie) {
        this.ziua = ziua;
        this.interval = interval;
        this.profesor = profesor;
        this.disciplina = disciplina;
        this.sala = sala;
        this.esteCurs = esteCurs;
        this.formatie = formatie;
    }

    public String getZiua() {
        return ziua;
    }

    public String getInterval() {
        return interval;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public String getSala() {
        return sala;
    }

    public boolean isEsteCurs() {
        return esteCurs;
    }

    public String getFormatie() {
        return formatie;
    }

    @Override
    public String toString() {
        return "Programare{" +
                "ziua='" + ziua + '\'' +
                ", interval='" + interval + '\'' +
                ", profesor=" + profesor +
                ", disciplina='" + disciplina + '\'' +
                ", sala='" + sala + '\'' +
                ", esteCurs=" + esteCurs +
                ", formatie='" + formatie + '\'' +
                '}';
    }
}
public class Main{
    public static void main(String[] args)throws Exception{
        Map<Integer,Profesor> profesori;
        List<Programare> programari;
        try(var fisier=new BufferedReader(new FileReader("data\\profesori.txt"))){
            profesori=fisier.lines().map(linie->new Profesor(
                    Integer.parseInt(linie.split("\t")[0]),
                    linie.split("\t")[1],
                    linie.split("\t")[2],
                    linie.split("\t")[3]
            )).collect(Collectors.toMap(prof->prof.getIdProf(),prof->prof));
        }




        try(var fisier=new BufferedReader(new FileReader("data\\programari.txt"))){
            programari=fisier.lines().map(linie->new Programare(linie.split("\t")[0],
                    linie.split("\t")[1],profesori.get(Integer.parseInt(linie.split("\t")[2])),
                    linie.split("\t")[3],linie.split("\t")[4],Boolean.parseBoolean(linie.split("\t")[5]),
                    linie.split("\t")[6])).collect(Collectors.toList());
        }
        programari.stream().filter(Programare::isEsteCurs)
                .map(Programare::getDisciplina).distinct().sorted().forEach(System.out::println);
        System.out.printf("%30s %2s %2s%n","Profesor","C","S");
        programari.stream().collect(Collectors.groupingBy(Programare::getProfesor))
                .forEach((profesor,programariProfesor)->{System.out.printf(
                        "%30s %2d %2d%n", profesor.getNume(),programariProfesor.stream().filter(
                                Programare::isEsteCurs).count(),
                        programariProfesor.stream().filter(p->!p.isEsteCurs()).count()
                        );
                });
        class Departament {
            String denumire;
            long numarActivitati;

            public Departament(String denumire, long numarActivitati) {
                this.denumire = denumire;
                this.numarActivitati = numarActivitati;
            }

            @Override
            public String toString() {
                return "Departament{" +
                        "denumire='" + denumire + '\'' +
                        ", numarActivitati=" + numarActivitati +
                        '}';
            }
        }
        programari.stream().map(programare -> programare.getProfesor().getDept())
                .distinct().map(denumire->new Departament(denumire,programari.stream().filter(
                        programare -> programare.getProfesor().getDept().equals(denumire)
                ).count())).sorted((a,b)->Long.compare(b.numarActivitati,a.numarActivitati))
                .forEach(System.out::println);
    }
}
