import java.util.ArrayList;

public class ProgramFacturi {
    ArrayList<factura> facturi=new ArrayList<>();

    public ProgramFacturi(ArrayList<factura> facturi) {
        this.facturi = facturi;
    }

    public ArrayList<factura> getFacturi() {
        return facturi;
    }

    public void setFacturi(ArrayList<factura> facturi) {
        this.facturi = facturi;
    }

}
