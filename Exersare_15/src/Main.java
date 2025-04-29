import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

public class Main {
    public static void main(String[] args) {
        List<Vehicul> vehicule=new ArrayList<>();
        try(BufferedReader br=new BufferedReader(new FileReader("data\\parcare.txt"))){
            String linie;
            while((linie= br.readLine())!=null){
                String[] data=linie.split(",");
                int nrPasageri=Integer.parseInt(data[2]);
                Vehicul vehTemp=new Vehicul(data[0],data[1],nrPasageri);
                vehicule.add(vehTemp);
            }
        }catch(IOException e){
            System.err.println("Eroare la citirea fisierului: "+e.getMessage());
        }
        int nrPasageri=0;
        for(Vehicul v:vehicule){
            nrPasageri+=v.getNrPasageri();
        }
        System.out.println("Nr de vehicule: "+vehicule.size()+" Pasageri: "+nrPasageri);
        System.out.println("===Cerinta 2===");
        int nrPasageriLux=0;
        int nrVehiculeLux=0;
        for(Vehicul v:vehicule){
            if(v.esteDeLux()){
                nrVehiculeLux++;
                nrPasageriLux+=v.getNrPasageri();
            }
        }
        System.out.println("Vehicule de lux: "+nrVehiculeLux+" Pasageri: "+nrPasageriLux);
        System.out.println("Alte vehicule: "+(vehicule.size()-nrVehiculeLux)+" Pasageri: "+(nrPasageri-nrPasageriLux));
        Map<String,Double> map=new HashMap<>();
        final double taxa=2.5;
        for(Vehicul v:vehicule){
            String nrInmat=v.getNrInmatriculare();
            String[] tok=nrInmat.split("-");
            double taxaP=taxa;
            if(v.esteDeLux()){
                taxaP=taxaP*1.2;
            }
            map.put(tok[0],map.getOrDefault(tok[0],0.0)+taxaP);
        }
        try(BufferedWriter writer=new BufferedWriter(new FileWriter("data\\raportParcare.txt"))){
            for(Map.Entry<String,Double> harta:map.entrySet()){
                writer.write("Judet: "+harta.getKey()+" Valoare: "+harta.getValue());
                writer.newLine();
            }
        }catch(IOException e){
            System.err.println("Eroare la afisarea fisierului: "+e.getMessage());
        }
    }
}