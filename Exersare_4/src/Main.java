import java.time.LocalDate;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
ProgramStocuri programStocuri=new ProgramStocuri();
programStocuri.AdaugaProdus(1,"Laptop");
programStocuri.AdaugaProdus(2,"Telefon");
programStocuri.AdaugaTranzactie(TipTranzactie.Intrare, LocalDate.of(2021,3,12),1,31);
programStocuri.AdaugaTranzactie(TipTranzactie.Intrare, LocalDate.of(2025,4,5),2,101);
programStocuri.AdaugaTranzactie(TipTranzactie.Iesire, LocalDate.of(2025,5,12),2,12);
programStocuri.AfisareStocuri();
    }
}