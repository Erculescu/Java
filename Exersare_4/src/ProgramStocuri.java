import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgramStocuri {
    private static final Map<Integer, List<Object>> dictionar=new HashMap<>();
    static void AdaugaProdus(int cod, String denumire){
        Produs produs=new Produs(cod,denumire);
           dictionar.putIfAbsent(cod,new ArrayList<>());
           dictionar.get(cod).add(produs);

    }
    static void AdaugaTranzactie(TipTranzactie tip, LocalDate data, int codProdus,int cantitate){
        if(dictionar.containsKey(codProdus)){
            Tranzactie tranzactie=new Tranzactie(tip,data,codProdus,cantitate);
            dictionar.get(codProdus).add(tranzactie);

        }
    }
    static void AfisareStocuri(){
        for(Map.Entry<Integer,List<Object>> elem:dictionar.entrySet()){
            int cod=elem.getKey();
            List<Object> valori=elem.getValue();
            System.out.println("Cod: "+cod);
            for(Object valoare:valori){
            if(valoare instanceof Produs){
                Produs produs=(Produs) valoare;
                System.out.println("Produs: "+produs.getDenumire());
            }else if(valoare instanceof Tranzactie) {
                Tranzactie tranzactie=(Tranzactie) valoare;
                System.out.println("Tipul tranzactiei: "+tranzactie.getTip()+" Data: "+tranzactie.getData()+
                        " Cantitate: "+tranzactie.getCantitate());
            }
        }
    }
}
}
