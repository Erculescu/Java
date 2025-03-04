import java.time.LocalDate;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        Produs p1=new Produs(1,"Laptop");
        Produs p2=new Produs(2,"Telefon");
        Produs p3=new Produs(3,"Monitor");
        Produs p4=new Produs(4,"Tastatura");
        Produs p5=new Produs(5,"Imprimanta");
        Produs[] produse={p1,p2,p3,p4,p5};
        LocalDate date=LocalDate.now();
        Tranzactie t1=new Tranzactie(TipTranzactie.INTRARE, date,1,50);
        Tranzactie t2=new Tranzactie(TipTranzactie.INTRARE, date,2,24);
        Tranzactie t3=new Tranzactie(TipTranzactie.INTRARE, date,4,10);
        Tranzactie t4=new Tranzactie(TipTranzactie.INTRARE, date,4,9);
        Tranzactie t5=new Tranzactie(TipTranzactie.INTRARE, date,5,4);
        Tranzactie t6=new Tranzactie(TipTranzactie.INTRARE, date,3,12);
        Tranzactie t7=new Tranzactie(TipTranzactie.IESIRE, date,1,3);
        Tranzactie t8=new Tranzactie(TipTranzactie.IESIRE, date,2,10);
        Tranzactie t9=new Tranzactie(TipTranzactie.IESIRE, date,3,1);
        Tranzactie t10=new Tranzactie(TipTranzactie.IESIRE, date,4,4);
        Tranzactie t11=new Tranzactie(TipTranzactie.IESIRE, date,5,2);
        Tranzactie t12=new Tranzactie(TipTranzactie.IESIRE, date,1,13);
        Tranzactie[] tranzactii={t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12};
        ProgramStocuri programStocuri=new ProgramStocuri();
        programStocuri.AdaugaProdus(p1);
        programStocuri.AdaugaProdus(p2);
        programStocuri.AdaugaProdus(p3);
        programStocuri.AdaugaProdus(p4);
        programStocuri.AdaugaProdus(p5);
        programStocuri.AdaugaTranzactie(TipTranzactie.INTRARE, date,1,50);
        programStocuri.AdaugaTranzactie(TipTranzactie.INTRARE, date,2,24);
        programStocuri.AdaugaTranzactie(TipTranzactie.INTRARE, date,4,10);
        programStocuri.AdaugaTranzactie(TipTranzactie.INTRARE, date,5,4);
        programStocuri.AdaugaTranzactie(TipTranzactie.INTRARE, date,3,12);
        programStocuri.AdaugaTranzactie(TipTranzactie.INTRARE, date,1,3);
        programStocuri.AdaugaTranzactie(TipTranzactie.INTRARE, date,2,10);
        programStocuri.AdaugaTranzactie(TipTranzactie.IESIRE, date,1,13);
        programStocuri.AdaugaTranzactie(TipTranzactie.IESIRE, date,2,1);
        programStocuri.AdaugaTranzactie(TipTranzactie.IESIRE, date,2,4);
        programStocuri.AdaugaTranzactie(TipTranzactie.IESIRE, date,2,5);
        programStocuri.AdaugaTranzactie(TipTranzactie.IESIRE, date,1,13);
        programStocuri.AdaugaTranzactie(TipTranzactie.IESIRE, date,3,9);
        programStocuri.AfisareStocuri();



    }
}