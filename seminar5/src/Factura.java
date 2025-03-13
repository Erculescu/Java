import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Factura {
    public static class Linie{
        private final String produs;
        private final double pret;
        private final int cantitate;

        public Linie(String produs, double pret, int cantitate) {
            this.produs = produs;
            this.pret = pret;
            this.cantitate = cantitate;
        }

        public String getProdus() {
            return produs;
        }

        public double getPret() {
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
    private final String denumireClient;
    private final LocalDate dataEmitere;
    private final List<Linie> linii;

    public Factura(LocalDate dataEmitere, String denumireClient) {
        this.dataEmitere = dataEmitere;
        this.denumireClient = denumireClient;
        this.linii=new ArrayList<>();
    }

    public String getDenumireClient() {
        return denumireClient;
    }

    public LocalDate getDataEmitere() {
        return dataEmitere;
    }
    public int getNumarLinii(){return linii.size();}
    public Linie getLinie(int i){return linii.get(i);}
    public void adaugaLinie(Linie linie){linii.add(linie); }
    public void adaugaLinie(String produs,double pret,int cantitate){
        adaugaLinie(new Linie(produs,pret,cantitate));
    }
    public String toString(){
        var builder=new StringBuilder();
        builder.append(String.format("%20s%n",denumireClient));
        for( var linie:linii){
            builder.append("  "+linie+System.lineSeparator());
        }
        return builder.toString();
    }

}
