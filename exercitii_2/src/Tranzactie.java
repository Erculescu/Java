import java.time.LocalDate;
import java.util.Date;

public class Tranzactie {
    TipTranzactie tip;
    LocalDate data;
    int codProd;
    int cantitate;

    public Tranzactie(TipTranzactie tip, LocalDate data, int codProd, int cantitate) {
        this.tip = tip;
        this.data = data;
        this.codProd = codProd;
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

    public int getCodProd() {
        return codProd;
    }

    public void setCodProd(int codProd) {
        this.codProd = codProd;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }


}
