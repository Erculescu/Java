import java.time.LocalDate;
import java.util.*;

public class ProgramStocuri {
    public static Map<Produs, List<Tranzactie>> stocuri= new HashMap<>();
    public static void AdaugaProdus(Produs produs){
        if(!stocuri.containsKey(produs)){
            stocuri.put(produs,new ArrayList<>());
        }else {
            System.out.println("Produsul exista deja!");
        }
    }
    public static void AdaugaTranzactie(TipTranzactie tip, LocalDate data, int codProd, int cantitate){
        Produs prodcopie=new Produs(codProd);
        Produs produs=stocuri.keySet().stream().filter(p->p.getCod()==codProd)
                .findFirst().orElse(null);

        if(produs!=null){
            List<Tranzactie> tranzactii=stocuri.get(produs);
            tranzactii.add(new Tranzactie(tip,data,codProd,cantitate));
        }
        else{
            System.out.println("Produsul nu exista!");}
    }
    public static int calculareStocuri(Produs produs){
        int total=0;
        if(stocuri.containsKey(produs)){
            List<Tranzactie> tranzactii=stocuri.get(produs);
            for(Tranzactie tranzactie:tranzactii){
                if(tranzactie.getTip()==TipTranzactie.INTRARE)
                {total=total+tranzactie.getCantitate();}
                else{
                    total=total-tranzactie.getCantitate();
                }
            }
        }
        return total;
    }

    public void AfisareStocuri(){
        for(Produs produs:stocuri.keySet()){
            System.out.println("Produsul: "+produs+" stoc: "+calculareStocuri(produs));
        }
    }
}
