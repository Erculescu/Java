public class Piesa {
    String id;
    String denumire;
    int cantitate;
    float pret;

    public Piesa(String id, String denumire, int cantitate, float pret) {
        this.id = id;
        this.denumire = denumire;
        this.cantitate = cantitate;
        this.pret = pret;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public float getPret() {
        return pret;
    }

    public void setPret(float pret) {
        this.pret = pret;
    }

    @Override
    public String toString() {
        return "Piesa{" +
                "id='" + id + '\'' +
                ", denumire='" + denumire + '\'' +
                ", cantitate=" + cantitate +
                ", pret=" + pret +
                '}';
    }
}
