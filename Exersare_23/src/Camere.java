import java.util.ArrayList;
import java.util.List;

class Operatiuni{
    int cantitate;
    String status;

    public Operatiuni(int cantitate, String status) {
        this.cantitate = cantitate;
        this.status = status;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Operatiuni{" +
                "cantitate=" + cantitate +
                ", status='" + status + '\'' +
                '}';
    }
}
public class Camere {
    int id;
    String nume;
    double pretOperatiune;
    int nrCamere;
    List<Operatiuni> opertiuni;

    public Camere(int id, String nume, double pretOperatiune) {
        this.id = id;
        this.nume = nume;
        this.pretOperatiune = pretOperatiune;
        this.nrCamere=0;
        this.opertiuni=new ArrayList<>();
    }
    public void adaugaOperatiune(Operatiuni operatiune){
        opertiuni.add(operatiune);
        if(operatiune.status.equals("Reparat")){
            nrCamere-=operatiune.cantitate;
        }else{
            nrCamere+=operatiune.cantitate;
        }
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

    public double getPretOperatiune() {
        return pretOperatiune;
    }

    public void setPretOperatiune(double pretOperatiune) {
        this.pretOperatiune = pretOperatiune;
    }

    public int getNrCamere() {
        return nrCamere;
    }

    @Override
    public String toString() {
        return "Camere{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", pretOperatiune=" + pretOperatiune +
                ", nrCamere=" + nrCamere +
                ", opertiuni=" + opertiuni +
                '}';
    }
}
