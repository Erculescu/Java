import java.io.*;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.


public class Main {
    public static boolean esteDeLux(Vehicul v){
        if(v.getMarca().equals("Porsche")||v.getMarca().equals("BMW")||v.getMarca().equals("Audi")||v.getMarca().equals("Bentley")||v.getMarca().equals("Mercedes")){
            return true;
        }else {
            return false;}
    }
    public static void main(String[] args) {
        ArrayList<Vehicul> vehicule=new ArrayList<>();

        try(BufferedReader br=new BufferedReader(new FileReader("data\\parcare.txt"))){
            String linie;
            while((linie=br.readLine())!=null){
                String[] tokenuri=linie.split(",");
                int pasageri=Integer.parseInt(tokenuri[2]);
                Vehicul vehiculTemp=new Vehicul(tokenuri[0],tokenuri[1],pasageri);
                vehicule.add(vehiculTemp);
            }
        }catch(IOException e){
            System.err.println("Nu s-a putut citi fisierul: "+e.getMessage());
        }
        int nrPasageri=0;
        int nrPasageriLux=0;
        int contor=0;
        Collections.sort(vehicule, Comparator.comparing(Vehicul::getNrInmatriculare));
        for(Vehicul veh:vehicule){
            nrPasageri=nrPasageri+veh.getPasageri();
            if(esteDeLux(veh)==true){
                nrPasageriLux=nrPasageriLux+ veh.getPasageri();
                contor++;
            }
            System.out.println(veh.toString());
        }

        System.out.println("===Cerinta 2===");
        System.out.println("Nr Vehicule: "+vehicule.size()+", Nr Pasageri total: "+nrPasageri);
        System.out.println("===Cerinta 3===");
        System.out.println("Nr Vehicule de lux: "+contor+", Nr Pasageri in Vehicule de Lux: "+nrPasageriLux);

        Map<String,Double> taxePerCategorie=new HashMap<>();
        try(BufferedWriter writer=new BufferedWriter(new FileWriter("data\\raportParcare.txt"))){
            for(Vehicul veh:vehicule){
                String[] tok=veh.getNrInmatriculare().split("-");
                String cat=tok[0];
                double taxa=0;
                if(esteDeLux(veh)==true){
                    taxa=taxa+constante.taxaParcare*1.2;
                }else{
                    taxa=taxa+constante.taxaParcare;
                }
                taxePerCategorie.put(cat,taxePerCategorie.getOrDefault(cat,0.0)+taxa);


            }
            for(Map.Entry<String,Double> intrare:taxePerCategorie.entrySet()){
                writer.write(intrare.getKey()+" "+intrare.getValue());
                writer.newLine();
            }
        }catch (IOException e){
            System.err.println("Nu s-a putut scrie fisierul: "+e.getMessage());

        }



    }
}