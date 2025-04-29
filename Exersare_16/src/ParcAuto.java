import java.util.ArrayList;
import java.util.List;

public class ParcAuto {
    private List<Autoturism> auto;
    private String denumire;

    public ParcAuto(List<Autoturism> auto, String denumire) {
        this.auto = auto;
        this.denumire = denumire;
    }
    public ParcAuto(){
        this.auto=new ArrayList<>();
    }
    public ParcAuto(String denumire){
        this.auto=new ArrayList<>();
        this.denumire=denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public List<Autoturism> getAuto() {
        return auto;
    }

    public String getDenumire() {
        return denumire;
    }
    public void AdaugaVehicule(Autoturism vehicul) {
        try{for(Autoturism veh:this.auto){
            if(veh.equals(vehicul)){
                throw new RuntimeException();
            }

        }} catch (RuntimeException e) {
            System.err.println("Exista deja acest vehicul: "+e.getMessage());
        }
        this.auto.add(vehicul);
    }


}
