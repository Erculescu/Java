import java.util.ArrayList;
import java.util.Scanner;

public class Student {
    int id;
    String nume;
    int grupa;
    An anul;
    ArrayList<Nota> note;

    public Student(int id, String nume, int grupa, An anul, ArrayList<Nota> note) {
        this.id = id;
        this.nume = nume;
        this.grupa = grupa;
        this.anul = anul;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getGrupa() {
        return grupa;
    }

    public void setGrupa(int grupa) {
        this.grupa = grupa;
    }

    public An getAnul() {
        return anul;
    }

    public void setAnul(An anul) {
        this.anul = anul;
    }

    public ArrayList<Nota> getNote() {
        return note;
    }

    public void setNote(ArrayList<Nota> note) {
        this.note = note;
    }
}




