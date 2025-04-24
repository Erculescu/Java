public final class Student {
    private final String nume;
    private final String disciplina;
    private final int nota;

    public Student(String nume, String disciplina, int nota) {
        this.nume = nume;
        this.disciplina = disciplina;
        this.nota = nota;
    }

    public String getNume() {
        return nume;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public int getNota() {
        return nota;
    }

    @Override
    public String toString() {
        return "Student{" +
                "nume='" + nume + '\'' +
                ", disciplina='" + disciplina + '\'' +
                ", nota=" + nota +
                '}';
    }
}
