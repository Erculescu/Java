final public class linii {
    final private String produs;
    final private float pret;
    final private int cantitate;

    public linii(String produs, float pret, int cantitate) {
        this.produs = produs;
        this.pret = pret;
        this.cantitate = cantitate;
    }

    public String getProdus() {
        return produs;
    }

    public float getPret() {
        return pret;
    }

    public int getCantitate() {
        return cantitate;
    }

    @Override
    public String toString() {
        return "linii{" +
                "produs='" + produs + '\'' +
                ", pret=" + pret +
                ", cantitate=" + cantitate +
                '}';
    }
}
