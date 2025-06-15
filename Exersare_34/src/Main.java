import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Main {
    public  static Map<Integer,Parcare> parcari=new HashMap<>();
    public static void main(String[] args) throws Exception {
    try(BufferedReader br=new BufferedReader(new FileReader("Date\\locuri.txt"))){
        String linie;
        while((linie=br.readLine())!=null){
            String[] tokens=linie.split(",");
            int codParcare=Integer.parseInt(tokens[0]);
            String adresa=tokens[1];
            Double tarif=Double.parseDouble(tokens[2]);
            parcari.put(codParcare,new Parcare(codParcare,adresa,tarif));
        }
    }catch (IOException e){
        System.out.println("Eroare la citirea fisierului .txt "+e.getMessage());
    }
    try(FileReader file=new FileReader("Date\\parcare.json")){
        var jLoc=new JSONArray(new JSONTokener(file));
        for(int i=0;i<jLoc.length();i++){
            var jparcat=jLoc.getJSONObject(i);
            int codParcare=jparcat.getInt("codLoc");
            String nrInmatrculare=jparcat.getString("nrInmatriculare");
            int timp=jparcat.getInt("durata");
            parcari.get(codParcare).adaugaVehicul(new Vehicul(codParcare,nrInmatrculare,timp));
        }
    }catch (IOException e){
        System.out.println("Eroare la citirea fisierului .json "+e.getMessage());
    }
        HashSet<String> nrInmatriculate=new HashSet<>();
    for(Map.Entry<Integer,Parcare> parcare: parcari.entrySet()){
        for(Vehicul v:parcare.getValue().vehiculeParcate){
            nrInmatriculate.add(v.getNumarInmatriculare());
        }
    }
        System.out.println("Cerinta: Afisati numarul de vehicule parcate: "+nrInmatriculate.size());
        Map<String,Double> tarifeVehicule=new HashMap<>();
        for(Map.Entry<Integer,Parcare> parcare: parcari.entrySet()){
            for(Vehicul v:parcare.getValue().vehiculeParcate){
                double valoare=parcare.getValue().tarifOra*((double) v.durata /60);
                tarifeVehicule.put(v.getNumarInmatriculare(), tarifeVehicule.getOrDefault(v.getNumarInmatriculare(),0.0)+valoare);
            }
        }
        System.out.println("Cerinta: Afisati cat a platit fiecare masina pentru a sta in parcari: ");
        for(Map.Entry<String,Double> tarif: tarifeVehicule.entrySet()){
            System.out.println(tarif.getKey()+" "+tarif.getValue());
        }
        System.out.println("Cerinta: Afisati vehiculele in ordine descrescatoare a timpului petrecut in parcari: ");
        Map<Vehicul,Integer> vehiculmap=new HashMap<>();
        for(Map.Entry<Integer,Parcare> p: parcari.entrySet()){
            for(Vehicul v:p.getValue().vehiculeParcate){
                vehiculmap.put(v, vehiculmap.getOrDefault(v,0)+v.getDurata());
            }
        }
        vehiculmap.values().stream().sorted((v1,v2)-> v2.compareTo(v1)).forEach(System.out::println);

        try(BufferedWriter bw=new BufferedWriter(new FileWriter("Date\\raport.txt"))){
            for(Map.Entry<Integer,Parcare> p: parcari.entrySet()){
                bw.write(p.getValue().codParcare+" "+p.getValue().adresa+" Vehicule:");
                for(Vehicul v:p.getValue().vehiculeParcate) {
                    double valoare = p.getValue().tarifOra * ((double) v.durata / 60);
                    bw.write(" "+v.getNumarInmatriculare()+" "+valoare);
                    bw.newLine();
                }
                bw.newLine();
            }
        }catch (IOException e){
            System.out.println("Eroare la scrierea fisierului .txt "+e.getMessage());
        }
        final int PORT=3929;
        new Thread(()->{
            try(
                    var server=new ServerSocket(PORT);
                    var socket=server.accept();
                    var in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    var out=new PrintWriter(socket.getOutputStream(),true);
                    ){
                var cod=in.readLine();
                System.out.println("SERVER: AM PRIMIT "+cod);
                out.println(parcari.get(Integer.parseInt(cod)).vehiculeParcate.size());
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        ).start();
        Thread.sleep(500);
        try(
                var socket=new Socket("localhost",PORT);
                var out=new PrintWriter(socket.getOutputStream(),true);
                var in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ){
            out.println("1");
            System.out.println("CLIENT: AM PRIMIT "+in.readLine());
        }catch (Exception e){e.printStackTrace();}
    }
}