import java.util.ArrayList;
import java.util.List;

class Service{
    Double pret;
    String operatiuni;

    public Service(Double pret, String operatiuni) {
        this.pret = pret;
        this.operatiuni = operatiuni;
    }

    public String getOperatiuni() {
        return operatiuni;
    }

    public void setOperatiuni(String operatiuni) {
        this.operatiuni = operatiuni;
    }

    public Double getPret() {
        return pret;
    }

    public void setPret(Double pret) {
        this.pret = pret;
    }

    @Override
    public String toString() {
        return "Service{" +
                "pret=" + pret +
                ", operatiuni='" + operatiuni + '\'' +
                '}';
    }
}
public class Vehicul {
    int id;
    String VIN;
    String proprietar;
    String marca;
    String model;
    List<Service> revizii;

    public Vehicul(int id, String VIN, String proprietar, String marca, String model) {
        this.id = id;
        this.VIN = VIN;
        this.proprietar = proprietar;
        this.marca = marca;
        this.model = model;
        this.revizii=new ArrayList<>();
    }

    public void adaugaService(Service service){
        revizii.add(service);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }

    public String getProprietar() {
        return proprietar;
    }

    public void setProprietar(String proprietar) {
        this.proprietar = proprietar;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
    public double calculPret(){
        double val=0;
        for(Service service:revizii){
            val+= service.getPret();
        }
        return val;
    }


    @Override
    public String toString() {
        return "Vehicul{" +
                "id=" + id +
                ", VIN='" + VIN + '\'' +
                ", proprietar='" + proprietar + '\'' +
                ", marca='" + marca + '\'' +
                ", model='" + model + '\'' +
                ", revizii=" + revizii +
                '}';
    }
}
