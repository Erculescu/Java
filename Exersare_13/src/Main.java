import java.io.*;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Gestiune gestiune=new Gestiune();
        try(BufferedReader br=new BufferedReader(new FileReader("data\\produse.txt"))){
            String linie;
            while((linie=br.readLine())!=null){
                String[] tokens=linie.split(",");
                int cod=Integer.parseInt(tokens[0]);
                double cantitate=Double.parseDouble(tokens[2]);
                double pret=Double.parseDouble(tokens[3]);
                Produs prodTemp=new Produs(cod,tokens[1],cantitate,pret);
                gestiune.AdaugaProduse(prodTemp);

            }

        }catch(IOException e){
            System.err.println("Eroare la citirea fisierului: "+e.getMessage());
        }
        gestiune.Afisare();
        List<Map.Entry<String,Double>> lista=new ArrayList<>();
        for(Produs p: gestiune.produse){
            lista.add(new AbstractMap.SimpleEntry<>(p.getDenumire(),p.getValoare()));
        }
        lista.sort(Map.Entry.<String,Double>comparingByValue().reversed());

        try (BufferedWriter writer=new BufferedWriter(new FileWriter("data\\stocuri.txt"))){
            for(Map.Entry<String,Double> intrare:lista){
                writer.write("Produsul: "+intrare.getKey()+" Valoarea totala a stocului: "+intrare.getValue());
                writer.newLine();
            }
        }catch(IOException e){
            System.err.println("Eroare la scrierea fisierului: "+e.getMessage());
        }
    }
}