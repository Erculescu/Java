import java.io.*;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static double CalculeazaIntretinere(String adresa,List<Bloc> blocuri){
        double intretinere=0;
        for(Bloc bloc:blocuri){
            if(bloc.getAdresa().equals(adresa)){
                for(int i=0;i<bloc.apartamente.size();i++){
                    intretinere=intretinere+bloc.apartamente.get(i).getNrLocatari()*Constante.intretinerePerLoc;
                }
            }
        }
        return intretinere;

    }

    public static void main(String[] args) {

        List<Bloc> ListaBlocuri=new ArrayList<>();
        try(BufferedReader br=new BufferedReader(new FileReader("data\\blocuri.txt"))){
            String linie;
            while((linie= br.readLine())!=null){
                List<Bloc.Apartament> apartamente=new ArrayList<>();
                try(BufferedReader brr=new BufferedReader(new FileReader("data\\apartamente.txt"))){
                    String linie2;

                    while((linie2=brr.readLine())!=null){
                        String[] tokenuri=linie2.split(",");
                        int loc=Integer.parseInt(tokenuri[1]);
                        Bloc.Apartament aptTemp=new Bloc.Apartament(tokenuri[0],loc );
                        apartamente.add(aptTemp);

                    }
                }catch (IOException e){
                    System.err.println("Eroare la citirea listei de apartamente "+e.getMessage());
                }
                Bloc temp=new Bloc(linie,apartamente);
                ListaBlocuri.add(temp);
            }
        }catch(IOException e){
            System.err.println("Eroare la citirea listei de blocuri "+e.getMessage());
        }
        for(Bloc bloc:ListaBlocuri){
            System.out.println(bloc.toString());
        }
        System.out.println("===Cerinta: Afisare nr Total de locuitori inregistrati===");
        int totalLoc=0;
        for(Bloc bloc:ListaBlocuri){
            for(int i=0;i<bloc.apartamente.size();i++){
                totalLoc+=bloc.apartamente.get(i).getNrLocatari();
            }
        }
        System.out.println("Total locuitori inregistrati: "+totalLoc);
        System.out.println("===Cerinta: Calculare intretinere pentru un bloc de la o adresa specifica===");
        Scanner scanner=new Scanner(System.in);
        System.out.println("Introduceti adresa cautata: ");
        String adresa=scanner.nextLine();
        System.out.println("Blocul la adresa: "+adresa+" Are o intretinere lunara de: "+CalculeazaIntretinere(adresa,ListaBlocuri));
        Map<String,Double> IntretinerePerBloc=new HashMap<>();
        for(Bloc bloc:ListaBlocuri){
            IntretinerePerBloc.put(bloc.getAdresa(),CalculeazaIntretinere(bloc.getAdresa(),ListaBlocuri));
        }
        List<Map.Entry<String,Double>> listaSortata=new ArrayList<>(IntretinerePerBloc.entrySet());
        listaSortata.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        
        Map<String,Double> HartaSortata=new LinkedHashMap<>();
        for(Map.Entry<String,Double> intrare:listaSortata){
            HartaSortata.put(intrare.getKey(),intrare.getValue());
        }
        for(Map.Entry<String,Double> intrare:HartaSortata.entrySet()){
            System.out.println(intrare.getKey()+" "+intrare.getValue());
        }
        try(BufferedWriter writer=new BufferedWriter(new FileWriter("data\\raport.txt"))){
            for(Map.Entry<String,Double> intrare:HartaSortata.entrySet()){
                writer.write(intrare.getKey()+": "+(Double.toString(intrare.getValue())));
                writer.newLine();
            }
        }catch (IOException e){
            System.err.println("Eroare la afisarea raportului: "+e.getMessage());
        }

    }
}