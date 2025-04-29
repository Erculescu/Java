public final class Student {
    private final String nume;
    private final String denumireDisciplina;
    private final int nota;

    public Student(String nume, String denumireDisciplina, int nota) {
        this.nume = nume;
        this.denumireDisciplina = denumireDisciplina;
        this.nota = nota;
    }

    public String getNume() {
        return nume;
    }

    public String getDenumireDisciplina() {
        return denumireDisciplina;
    }

    public int getNota() {
        return nota;
    }

    @Override
    public String toString() {
        return "Student{" +
                "nume='" + nume + '\'' +
                ", denumireDisciplina='" + denumireDisciplina + '\'' +
                ", nota=" + nota +
                '}';
    }
}
