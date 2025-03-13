import java.util.ArrayList;
import java.util.Arrays;

public class Protagonist {
    private ArrayList<Personaj> personaj;
    private float atac;

    public Protagonist(ArrayList<Personaj> personaj, float atac) {
        this.personaj = new ArrayList<>(personaj);
        this.atac = atac;
    }

    public ArrayList<Personaj> getPersonaj() {
        return personaj;
    }

    public void setPersonaj(ArrayList<Personaj> personaj) {
        this.personaj = personaj;
    }

    public float getAtac() {
        return atac;
    }

    public void setAtac(float atac) {
        this.atac = atac;
    }

    public void AdaugaPersonaj(Personaj personaj){
        if(this.personaj.contains(personaj)){
            throw new IllegalArgumentException("Exista deja acest personaj");
        }
        else{
            this.personaj.add(personaj);
        }
    }

    public void CalculConflict(){
        for(Personaj vpersonaj: personaj){
            System.out.println(vpersonaj);
            System.out.println("Action Points rezultate din conflict: "+ (this.atac+(15*vpersonaj.getTipPersonaj().semn())));

        }

    }

    @Override
    public String toString() {
        return "Protagonist{" +
                "personaj=" + personaj +
                ", atac=" + atac +
                '}';
    }
}
