import java.util.Arrays;

public class Student {
    int idStudent;
    String nume;
    String grupa;
    anul an;
    Nota[] note;

    Student(int idStudent, String nume, String grupa, anul an) {
        this.idStudent=idStudent;
        this.nume=nume;
        this.grupa=grupa;
        this.an=an;
        this.note=new Nota[0];
    }

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public java.lang.String getNume() {
        return nume;
    }

    public void setNume(java.lang.String nume) {
        this.nume = nume;
    }

    public java.lang.String getGrupa() {
        return grupa;
    }

    public void setGrupa(java.lang.String grupa) {
        this.grupa = grupa;
    }

    public anul getAn() {
        return an;
    }

    public void setAn(anul an) {
        this.an = an;
    }

    public Nota[] getNote() {
        return note;
    }

    public void add_nota(Nota nota){
        for(var notaExistenta:note){
            if(notaExistenta.getNumeDisciplina().equals(nota.getNumeDisciplina())){
                notaExistenta.setNota(nota.getNota());
                return;
            }
        }
        note=(Nota[]) Arrays.copyOf(note,note.length+1);
        note[note.length-1]=nota;
    }

    @Override
    public String toString() {

        var builder=new StringBuilder();
        builder.append(String.format("ID_STUD: %d NUME: %s GRUPA: %s ANUL: %s",idStudent,nume,grupa,an));
        for(var nota:note){
            builder.append("\n"+nota);
        }
        return builder.toString();
    }
}
