public class Tranzactie {
    int cod;
    String denumire;
    double pret;
    int cantitate;

    public Tranzactie(int cod, String denumire, double pret, int cantitate) {
        this.cod = cod;
        this.denumire = denumire;
        this.pret = pret;
        this.cantitate = cantitate;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    @Override
    public String toString() {
        return "Tranzactie{" +
                "cod=" + cod +
                ", denumire='" + denumire + '\'' +
                ", pret=" + pret +
                ", cantitate=" + cantitate +
                '}';
    }
}
