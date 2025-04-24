public class Nota {
    String numeDisciplina;
    Float nota;

    public Nota(String numeDisciplina, Float nota) {
        this.numeDisciplina = numeDisciplina;
        this.nota = nota;
    }

    public String getNumeDisciplina() {
        return numeDisciplina;
    }

    public void setNumeDisciplina(String numeDisciplina) {
        this.numeDisciplina = numeDisciplina;
    }

    public Float getNota() {
        return nota;
    }

    public void setNota(Float nota) {
        this.nota = nota;
    }
}
