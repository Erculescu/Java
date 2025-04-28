import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static List<Piesa> pieseCompatibile(String denumire,List<produs> produse,List<Piesa> piese){
        String cod=null;
        List<Piesa> pieseComp=new ArrayList<>();
        for(produs produs:produse){
            if(produs.getDenumire().equals(denumire)){
                cod= produs.getCod();
                break;
            }
        }
        if(cod!=null){
            String[] token=cod.split("\\.");
            for(Piesa piesa:piese){
                String[] ptoken=piesa.getId().split("\\.");
                if(ptoken[1].equals(token[0])){
                    pieseComp.add(piesa);
                }
            }
        }
        return pieseComp;
    }


    public static void main(String[] args) {
        List<produs> produse=new ArrayList<>();
        List<Piesa> piese=new ArrayList<>();
        try(BufferedReader br=new BufferedReader(new FileReader("data\\stocuri.txt"))){
            String linie;
            while((linie=br.readLine())!=null){
                String[] token=linie.split(",");
                String[] codtoken=token[0].split("\\.");
                int cantitate=Integer.parseInt(token[2]);
                float pret=Float.parseFloat(token[3]);
                if(codtoken[0].equals("P")){
                    Piesa ptemp=new Piesa(token[0],token[1],cantitate,pret);
                    piese.add(ptemp);
                }else{
                    produs temp=new produs(token[0],token[1],cantitate,pret);
                    produse.add(temp);
                }
            }
        }catch(IOException e){
            System.err.println("Eroare la citirea fisierului: "+e.getMessage());
        }
        Collections.sort(produse, Comparator.comparing(produs::getPret).reversed());
        Collections.sort(piese,Comparator.comparing(Piesa::getCantitate));
        for(produs p:produse){
            System.out.println(p);
        }
        for(Piesa p:piese){
            System.out.println(p);
        }
        List<Piesa> pieseCompatibile=new ArrayList<>();
        pieseCompatibile=pieseCompatibile("Pompa M35",produse,piese);
        System.out.println("Piese compatibile cu: Pompa M35: ");
        for(Piesa p:pieseCompatibile){
            System.out.println(p);
        }
        try(BufferedWriter writer=new BufferedWriter(new FileWriter("data\\raport.txt"))){
            for(produs p:produse){
                writer.write("Denumire: "+p.getDenumire());
                List<Piesa> pieseComp=new ArrayList<>();
                pieseComp=pieseCompatibile(p.getDenumire(),produse,piese);
                if(!pieseComp.isEmpty()){
                    writer.write(" Piese compatibile:");
                    writer.newLine();
                    for(Piesa piesa:pieseComp){
                        writer.write("  "+piesa.getDenumire()+" cantitate: "+piesa.getCantitate()+" pret per buc: "+piesa.getPret()+" valoare stoc: "+(piesa.getCantitate()*piesa.getPret()));
                        writer.newLine();
                    }
                }else{
                    writer.newLine();
                    writer.write("  Nu exista pe stoc piese compatibile cu produsul "+p.getDenumire());
                    writer.newLine();
                }
            }
        }catch(IOException e){
            System.err.println("Eroare la scrierea raportului: "+e.getMessage());
        }

    }
}