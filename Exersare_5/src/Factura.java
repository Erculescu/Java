import java.time.LocalDate;
import java.util.ArrayList;

public class Factura {
    String denumireClient;
    LocalDate dataEmitere;

    public static final class Linie {
        private final String produs;
        private final float pret;
        private final int cantitate;

        public Linie(String produs, float pret, int cantitate) {
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
            return "Linie{" +
                    "produs='" + produs + '\'' +
                    ", pret=" + pret +
                    ", cantitate=" + cantitate +
                    '}';
        }
    }

    ArrayList<Linie> linii;

    public Factura(String denumireClient, LocalDate dataEmitere, ArrayList<Linie> linii) {
        this.denumireClient = denumireClient;
        this.dataEmitere = dataEmitere;
        this.linii = linii;
    }

    public Factura(String denumireClient, LocalDate dataEmitere) {
        this.denumireClient = denumireClient;
        this.dataEmitere = dataEmitere;
        this.linii = new ArrayList<>();
    }

    public String getDenumireClient() {
        return denumireClient;
    }

    public LocalDate getDataEmitere() {
        return dataEmitere;
    }

    public ArrayList<Linie> getLinii() {
        return linii;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "denumireClient='" + denumireClient + '\'' +
                ", dataEmitere=" + dataEmitere +
                ", linii=" + linii +
                '}';
    }

    void AdaugaLinie(Linie linie) {
        this.linii.add(linie);
    }
}
