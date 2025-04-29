import java.io.*;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void CreazaInventar(Map<String,String> harta, String marca,String model){
        if(harta.containsKey(marca)){
            String aux=harta.get(marca);
            harta.put(marca,aux+","+model);
        }else{
            harta.put(marca,model);
        }
    }
    public static void main(String[] args) {
        ParcAuto parc=new ParcAuto();
        try(BufferedReader br=new BufferedReader(new FileReader("data\\auto.txt"))){
            String linie;
            while((linie=br.readLine())!=null){
                String[] data=linie.split(",");
                if(data.length==1){
                    parc.setDenumire(data[0]);
                }else{
                    int serie=Integer.parseInt(data[0]);
                    float putere=Float.parseFloat(data[3]);
                    Autoturism autoTemp=new Autoturism(serie,data[1],data[2],putere);
                    parc.AdaugaVehicule(autoTemp);
                }
            }
        }catch(IOException e){
            System.err.println("Eroare la citirea fisierului: "+e.getMessage());
        }
        List<Autoturism> vehiculeDisponibile=parc.getAuto();
        int nrVehiculeLux=0;
        for(Autoturism aut:vehiculeDisponibile){
            if(aut.esteDeLux()){
                nrVehiculeLux++;
            }
        }
        System.out.println("Vehicule disponibile: "+parc.getAuto().size()+" Dintre care: "+nrVehiculeLux+" sunt din gama de lux");
        Map<String,String> stoc=new HashMap<>();
        for(Autoturism aut:vehiculeDisponibile){
            CreazaInventar(stoc,aut.getMarca(),aut.getModel());
        }
        try(BufferedWriter writer=new BufferedWriter(new FileWriter("data\\stoc.txt"))){
            writer.write("Vehicule de lux:");
            writer.newLine();
            List<String> brand=new ArrayList<>();
            brand.add("BMW");
            brand.add("Audi");
            brand.add("Mercedes");
            brand.add("Ferrari");
            brand.add("Bentley");
            brand.add("Rolls Royce");
            brand.add("Porsche");
            for(Map.Entry<String,String> harta:stoc.entrySet()){
                for(String lux:brand){
                    if(harta.getKey().equals(lux)){
                        writer.write("Brand: "+harta.getKey()+" Selectia de modele: "+harta.getValue());
                        writer.newLine();
                    }
                }
            }
            writer.newLine();
            writer.write("Celelalte vehicule: ");
            writer.newLine();
            for(Map.Entry<String,String> harta:stoc.entrySet()){
                boolean esteDeLux=false;
                for(String lux:brand){

                    if(harta.getKey().equals(lux)){
                        esteDeLux=true;
                        break;
                    }
                }
                if(!esteDeLux){
                    writer.write("Brand: "+harta.getKey()+" Selectia de modele: "+harta.getValue());
                    writer.newLine();
                }
            }
        }catch (IOException e){
            System.err.println("Eroare la scrierea fisierului: "+e.getMessage());
        }
        List<Map.Entry<String,Float>> masini=new ArrayList<>();
        for(Autoturism aut:vehiculeDisponibile){
            Map.Entry<String,Float> intrareTemp=new AbstractMap.SimpleEntry<>(aut.getMarca()+" "+aut.getModel(),aut.getPutere());
            masini.add(intrareTemp);
        }
        Collections.sort(masini,Comparator.comparing(Map.Entry<String,Float>::getValue).reversed());
        System.out.println("Masina cea mai puternica este: "+masini.get(0).getKey()+" cu "+masini.get(0).getValue()+" cai putere.");
    }
}