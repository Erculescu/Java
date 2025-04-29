import java.util.ArrayList;
import java.util.List;

public class Vehicul {
    private final String nrInmatriculare;
    private final String marca;
    private final int nrPasageri;

    public Vehicul(String nrInmatriculare, String marca, int nrPasageri) {
        this.nrInmatriculare = nrInmatriculare;
        this.marca = marca;
        this.nrPasageri = nrPasageri;
    }

    public String getNrInmatriculare() {
        return nrInmatriculare;
    }

    public String getMarca() {
        return marca;
    }

    public int getNrPasageri() {
        return nrPasageri;
    }

    public boolean esteDeLux(){
        List<String> vehiculeLux=new ArrayList<>();
        vehiculeLux.add("Audi");
        vehiculeLux.add("BMW");
        vehiculeLux.add("Mercedes");
        for(String lux: vehiculeLux){
            if(lux.equals(this.marca)){
                return true;

            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Vehicul{" +
                "nrInmatriculare='" + nrInmatriculare + '\'' +
                ", marca='" + marca + '\'' +
                ", nrPasageri=" + nrPasageri +
                '}';
    }
}
