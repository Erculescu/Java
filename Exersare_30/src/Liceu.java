import java.util.HashMap;
import java.util.Map;

public class Liceu {
    int codLiceu;
    String nume;
    Map<Integer,Integer> specializari;

    public Liceu(int codLiceu, String nume) {
        this.codLiceu = codLiceu;
        this.nume = nume;
        this.specializari=new HashMap<>();
    }
    public void adaugaOptiune(int codSpecializare,int locuri){
        specializari.put(codSpecializare,locuri);
    }
    public int getNrLocuri(){
        int locuri=0;
        for(Integer i:specializari.keySet()){
            locuri+=specializari.get(i);
        }
        return locuri;
    }

    public int getCodLiceu() {
        return codLiceu;
    }

    public void setCodLiceu(int codLiceu) {
        this.codLiceu = codLiceu;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public String toString() {
        return "Liceu{" +
                "codLiceu=" + codLiceu +
                ", nume='" + nume + '\'' +
                ", specializari=" + specializari +
                '}';
    }
}
