import java.util.HashMap;
import java.util.Map;

public class Raspunsuri {
    String nume;
    Integer cod;
    String rasp;


    public Raspunsuri(String nume,Integer cod,String rasp){
        this.nume=nume;
        this.cod=cod;
        this.rasp=rasp;
    }


    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public String toString() {
        return "Raspunsuri{" +
                "nume='" + nume + '\'' +
                ", rasp=" + rasp +
                '}';
    }
}
