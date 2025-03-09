import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgramStocuri {
    public static Map<Produs, List<Tranzactie>> stocuri=new HashMap<>();
    static void AdaugaProdus(int cod,String denumire){
        if(stocuri.containsKey(new Produs(cod))){
            throw new IllegalArgumentException("Produsul exista deja!");
        }
        else {
            stocuri.put(new Produs(cod,denumire),new ArrayList<>());
        }
    }
    static void AdaugaTranzactie(TipTranzactie tip,LocalDate data, int codProdus, int cantitate){
        var produs=new Produs(codProdus);
        if(stocuri.containsKey(produs)){
            stocuri.get(produs).add(new Tranzactie(tip,data,codProdus,cantitate));
        }
        else{
            throw new IllegalArgumentException("Produsul nu exista!");
        }

    }



    public static void main(String[] args){
        var mere=new Produs(1,"Mere");
        System.out.println(mere);
        if(mere.equals(new Produs(1))){
            System.out.println("Sunt egale");

        }
        var tranzactie=new Tranzactie(TipTranzactie.IESIRE, LocalDate.of(2025,1,12),1,13);
        System.out.println(tranzactie);
        AdaugaProdus(1,"Mere");
        AdaugaProdus(2,"Pere");
        AdaugaProdus(3,"Prune");
//        AdaugaProdus(2,"Prune");
        AdaugaTranzactie(TipTranzactie.INTRARE,LocalDate.of(2025,1,3),1,12);
        AdaugaTranzactie(TipTranzactie.INTRARE,LocalDate.of(2025,2,12),2,21);
        AdaugaTranzactie(TipTranzactie.IESIRE,LocalDate.of(2025,2,21),2,5);
        AdaugaTranzactie(TipTranzactie.INTRARE,LocalDate.of(2025,3,1),3,7);

        for(var entry:stocuri.entrySet()){
            var produs=entry.getKey();
            var tranzactii=entry.getValue();
            var stoc=0;
            for(var tranz:tranzactii){
                stoc+=tranz.getCantitate()*tranz.getTip().semn();
                System.out.println(tranzactie.getTip().semn());
            }
            System.out.println(entry);
            System.out.printf("#%d %-5s %d bucati%n",produs.getCod(),produs.getDenumire(),stoc);
        }
    }

}
