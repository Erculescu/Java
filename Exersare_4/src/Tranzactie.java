import java.time.LocalDate;
import java.time.LocalDateTime;

public class Tranzactie {
    TipTranzactie tip;
    LocalDate data;
    int codProdus;
    int cantitate;

    public Tranzactie(TipTranzactie tip, LocalDate data, int codProdus, int cantitate) {
        this.tip = tip;
        this.data = data;
        this.codProdus = codProdus;
        this.cantitate = cantitate;
    }

    public TipTranzactie getTip() {
        return tip;
    }

    public void setTip(TipTranzactie tip) {
        this.tip = tip;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getCodProdus() {
        return codProdus;
    }

    public void setCodProdus(int codProdus) {
        this.codProdus = codProdus;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }
}
