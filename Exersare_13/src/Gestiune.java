import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Gestiune {
    public List<Produs> produse;
    public double ValoareTotala;
    public Gestiune(){
        this.produse=new ArrayList<>();
        this.ValoareTotala=0;
    }

    public Gestiune(List<Produs> produse){
        this.produse=produse;
        this.ValoareTotala =0;
        for(Produs prod:this.produse){
            ValoareTotala =ValoareTotala+(prod.getPret()*prod.getCantitate());
        }
        this.NrProduse=produse.size();
    }
    public int NrProduse;

    public void AdaugaProduse(Produs prod){
        try{
        for(Produs prodTemp:this.produse){
            Comparator<Produs> comparator=new ProdusComparator();
            if(comparator.compare(prodTemp,prod)==0){
                throw new ArithmeticException();
            }
        }
        this.produse.add(prod);

        }catch(ArithmeticException e){
            System.err.println(e.getMessage());
        }
    }
    public void Afisare(){
        for(Produs prod:this.produse){
            System.out.println(prod);
        }
    }
}
