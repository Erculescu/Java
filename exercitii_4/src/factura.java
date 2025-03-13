import java.time.LocalDate;
import java.util.ArrayList;

public class factura {
    String denumireClient;
    LocalDate dataCalendaristica;
    ArrayList<linii> prod=new ArrayList<>();

    public factura(String denumireClient, LocalDate dataCalendaristica, ArrayList<linii> prod) {
        this.denumireClient = denumireClient;
        this.dataCalendaristica = dataCalendaristica;
        this.prod = prod;
    }

    public String getDenumireClient() {
        return denumireClient;
    }

    public void setDenumireClient(String denumireClient) {
        this.denumireClient = denumireClient;
    }

    public LocalDate getDataCalendaristica() {
        return dataCalendaristica;
    }

    public void setDataCalendaristica(LocalDate dataCalendaristica) {
        this.dataCalendaristica = dataCalendaristica;
    }

    public ArrayList<linii> getProd() {
        return prod;
    }

    public void setProd(ArrayList<linii> prod) {
        this.prod = prod;
    }

    @Override
    public String toString() {
        return "factura{" +
                "denumireClient='" + denumireClient + '\'' +
                ", dataCalendaristica=" + dataCalendaristica +
                ", prod=" + prod +
                '}';
    }

}
