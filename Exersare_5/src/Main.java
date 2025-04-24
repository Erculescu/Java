import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void GenerareListaFacturi(LocalDate dataMin, int nr, ArrayList<Factura> facturi){
        int index=0;
        while(index<nr&&index<facturi.size()){
            if(facturi.get(index).getDataEmitere().isAfter(dataMin)){
            System.out.println(facturi.get(index).toString());}
            index++;
        }
    }
    public static void salvareFacturi(String fisier,ArrayList<Factura> facturi){
        try(DataOutputStream dos=new DataOutputStream(new FileOutputStream((String) fisier))){
            for(int i=0;i<facturi.size();i++){
                dos.writeUTF(facturi.get(i).getDenumireClient());
                dos.writeInt(facturi.get(i).getDataEmitere().getYear());
                dos.writeInt(facturi.get(i).getDataEmitere().getMonthValue());
                dos.writeInt(facturi.get(i).getDataEmitere().getDayOfMonth());
                dos.writeInt(facturi.get(i).getLinii().size());
                for(int j=0;j<facturi.get(i).getLinii().size();j++){
                    dos.writeUTF(facturi.get(i).getLinii().get(j).getProdus());
                    dos.writeFloat(facturi.get(i).getLinii().get(j).getPret());
                    dos.writeInt(facturi.get(i).getLinii().get(j).getCantitate());
                }

            }
        }catch (IOException e){
            System.err.println("Eroare la salvarea listei de facturi."+e.getMessage());
        }
    }
    public static ArrayList<Factura> incarcareFacturi(String fisier){
        ArrayList<Factura> facturi=new ArrayList<>();
        try(DataInputStream dis=new DataInputStream(new FileInputStream((String) fisier))){
        while(dis.available()>0){
            String denumireClient= dis.readUTF();
            int an=dis.readInt();
            int luna=dis.readInt();
            int zi=dis.readInt();
            LocalDate dataEmitere=LocalDate.of(an,luna,zi);
            List<Factura.Linie> linii=new ArrayList<>();
            int nrLinii=dis.readInt();
            for(int i=0;i<nrLinii;i++){
                String denumireProdus= dis.readUTF();
                float pret=dis.readFloat();
                int cantitate=dis.readInt();
                linii.add(new Factura.Linie(denumireProdus,pret,cantitate));
            }
            facturi.add(new Factura(denumireClient,dataEmitere,new ArrayList<>(linii)));
        }}catch (IOException e){
            System.err.println("Eroare la citirea listei de facturi. "+e.getMessage());
        }
        return facturi;
    }

    public static void generareRaport(String fisier,ArrayList<Factura> facturi){
        try(FileWriter writer=new FileWriter((String)fisier)){
            for(int i=0;i<facturi.size();i++){
                writer.write(facturi.get(i).getDenumireClient());
                writer.write("      ");
                writer.write(String.valueOf(facturi.get(i).getLinii().size()));
                writer.write(" facturi, ");
                writer.write("TOTAL: ");
                float valoare=0;
                for(int j=0;j<facturi.get(i).getLinii().size();j++){
                    valoare=valoare+facturi.get(i).getLinii().get(j).getPret();
                }
                writer.write(String.format("%.2f",valoare));
                writer.write(" RON");
                writer.write("\n");
            }
        }catch (IOException e){
            System.err.println("Eroare in generarea Raportului. "+e.getMessage());
        }
    }
    public static void main(String[] args) {
    Factura factura=new Factura("Pompe-Agro", LocalDate.of(2022,11,8));
        Factura.Linie linie=new Factura.Linie("Pompa d82",2500,5);
        factura.AdaugaLinie(linie);
        factura.AdaugaLinie(linie);
        factura.AdaugaLinie(linie);
        System.out.println(factura.toString());
        ArrayList<Factura> facturi=new ArrayList<>();
        facturi.add(factura);
        Factura factura2=new Factura("Prosol Chem",LocalDate.of(2024,6,5));
        Factura.Linie linie2=new Factura.Linie("Kart 120",8000,1);
        factura2.AdaugaLinie(linie2);
        facturi.add(factura2);
        GenerareListaFacturi(LocalDate.of(2020,01,01),3,facturi);
        salvareFacturi("facturi.bin",facturi);
        ArrayList<Factura> facturi2=new ArrayList<>();
        facturi2=incarcareFacturi("facturi.bin");
        GenerareListaFacturi(LocalDate.of(2010,01,01),10,facturi2);
        generareRaport("facturier.txt",facturi2);
    }
}