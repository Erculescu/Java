public class produs {
    String cod;
    String denumire;
    int cantitate;
    float pret;

    public produs(String cod, String denumire, int cantitate, float pret) {
        this.cod = cod;
        this.denumire = denumire;
        this.cantitate = cantitate;
        this.pret = pret;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
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
        return "produs{" +
                "cod='" + cod + '\'' +
                ", denumire='" + denumire + '\'' +
                ", cantitate=" + cantitate +
                ", pret=" + pret +
                '}';
    }
}
