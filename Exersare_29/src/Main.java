import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Long.compare;

public class Main {
    public static Map<Integer,Apartament> apartamente=new HashMap<>();
    public static void main(String[] args)throws Exception {
        List<Utilitati> listaUtilitati=new ArrayList<>();
        try(BufferedReader br=new BufferedReader(new FileReader("date\\lista_utilitati.txt"))){
            String linie;
            while((linie=br.readLine())!=null){
                String[] tokens=linie.split(",");
                String denumire=tokens[0];
                String repartizare=tokens[1];
                Double valoare=Double.parseDouble(tokens[2]);
                listaUtilitati.add(new Utilitati(denumire,repartizare,valoare));
            }
        }catch (IOException e){
            System.out.println("Eroare la citirea fisierului .txt"+e.getMessage());
        }
        try(FileReader file=new FileReader("date\\apartamente.json")){
            var japartamente=new JSONArray(new JSONTokener(file));
            for(int i=0;i<japartamente.length();i++){
                var japt=japartamente.getJSONObject(i);
                int nrapt=japt.getInt("nrApartament");
                int suprafata=japt.getInt("suprafata");
                int persoane=japt.getInt("nrPersoane");
                apartamente.put(nrapt,new Apartament(nrapt,suprafata,persoane));
                apartamente.get(nrapt).setUtilitati(listaUtilitati);
            }
        }catch (IOException e){
            System.out.println("Eroare la citirea fisierului .json "+e.getMessage());
        }
        double valoare=0.0;
        for(Apartament apt:apartamente.values()){
            valoare+=apt.getFactura();
        }
        System.out.println("Cerinta: Să se afișeze la consolă valoarea totală a facturilor. "+valoare);
        Double valoareApt=0.0,valoareSup=0.0,valoarePers=0.0;
        for(Apartament apt: apartamente.values()){
            for(Utilitati util: apt.utilitati){
                if(util.getRepartizare().equals("apartament")){
                    valoareApt+= util.getValoare();
                } else if (util.getRepartizare().equals("suprafata")) {
                    valoareSup+=util.getValoare()*apt.suprafata;
                }else{
                    valoarePers+=util.getValoare()*apt.persoane;
                }
            }
        }
        System.out.println("Cerinta: Să se afișeze la consolă valoarea totală a facturilor pe fiecare tip de repartizare. "+"Valoare rep apartament "+valoareApt
        +" Valoare rep suprafata "+valoareSup+" Valoare rep persoana "+valoarePers);
        int suprafataTotala=0;
        for(Apartament apartament: apartamente.values()){
            suprafataTotala+=apartament.suprafata;
        }
        System.out.println("Cerinta: Să se afișeze la consolă suprafața totală a apartamentelor din bloc. "+suprafataTotala);
        System.out.println("Cerinta: Să se scrie în fișierul text date\\tabel_intretinere.txt tabelul de intreținere în forma: Număr apartament, Suprafata, Persoane, Cheltuieli Suprafata, Cheltuieli Persoane, Cheltuieli Apartament, Total de plata");
        List<Apartament> listaApartamente=new ArrayList<>();
        for(Apartament apt:apartamente.values()){
            listaApartamente.add(apt);
        }
        listaApartamente.sort((a1,a2)->compare(a1.nrApartament,a2.nrApartament));
        try(BufferedWriter bw=new BufferedWriter(new FileWriter("date\\tabel_intretinere.txt"))){
            for(Apartament apt:listaApartamente){
                Double valoareApt1=0.0,valoareSup1=0.0,valoarePers1=0.0;
                for(Utilitati util:apt.utilitati){
                    if(util.getRepartizare().equals("apartament")){
                        valoareApt1+= util.getValoare();
                    } else if (util.getRepartizare().equals("suprafata")) {
                        valoareSup1+=util.getValoare()*apt.suprafata;
                    }else{
                        valoarePers1+=util.getValoare()*apt.persoane;
                    }
                }
                bw.write(apt.nrApartament+" "+apt.suprafata+" "+apt.persoane+" "+valoareApt1+" "+valoareSup1+" "+valoarePers1+" "+apt.getFactura());
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
                var nrApt=in.readLine();
                System.out.println("SERVER: AM PRIMIT "+nrApt);
                out.println(apartamente.get(Integer.parseInt(nrApt)).getFactura());
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
            out.println("304");
            System.out.println("CLIENT: AM PRIMIT "+in.readLine());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}