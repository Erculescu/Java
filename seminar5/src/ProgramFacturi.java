import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProgramFacturi {
    static List<Factura> generareFacturi(LocalDate dataMin,int numarFacturi){
        String[] denumiriClienti = new String[]{
                "ALCOR CONSTRUCT SRL",
                "SC DOMINO COSTI SRL",
                "SC TRANSCRIPT SRL",
                "SIBLANY SRL",
                "INTERFLOOR SYSTEM SRL",
                "MERCURY  IMPEX  2000  SRL",
                "ALEXANDER SRL",
                "METAL INOX IMPORT EXPOSRT SRL",
                "EURIAL BROKER DE ASIGURARE SRL"
        };

        String[] denumiriProduse = new String[]{
                "Stafide 200g",
                "Seminte de pin 300g",
                "Bulion Topoloveana 190g",
                "Paine neagra Frontera",
                "Ceai verde Lipton"

        };

        double[] preturiProduse = new double[]{
                5.20,
                12.99,
                6.29,
                4.08,
                8.99
        };

        var facturi=new ArrayList<Factura>();
        var random=new Random(42);
        var nrMaxZile=100;
        for(var indexFactura=0;indexFactura<numarFacturi;indexFactura++){
            var denumire=denumiriClienti[random.nextInt(denumiriClienti.length)];
            var data=dataMin.plusDays(random.nextInt(nrMaxZile));
            var factura=new Factura(data,denumire);
            var nrProduse=1+random.nextInt(10);
            for(var indexProdus=0;indexProdus < nrProduse;indexProdus++){
                int produsSelectat=random.nextInt(denumiriProduse.length);
                factura.adaugaLinie(denumiriProduse[produsSelectat],preturiProduse[produsSelectat],1+random.nextInt(10));

            }
            facturi.add(factura);
        }
        return facturi;
    }
    static void afisare(String mesaj,List<Factura> facturi){
        System.out.println("========"+mesaj+"========");
        for(var factura:facturi){
            System.out.println(factura);
        }
        System.out.println("_________________________");
    }
    static void salvare(String caleFisier,List<Factura> facturi)throws Exception{
        try(var fisier=new DataOutputStream(new FileOutputStream(caleFisier))){
            fisier.writeInt(facturi.size());
            for(var factura:facturi){
                fisier.writeUTF(factura.getDenumireClient());
                fisier.writeInt(factura.getDataEmitere().getYear());
                fisier.writeInt(factura.getDataEmitere().getMonthValue());
                fisier.writeInt(factura.getDataEmitere().getDayOfMonth());

            }
        }
    }
    static List<Factura> citire(String caleFisier) throws Exception{
        var facturi=new ArrayList<Factura>();
        try(var fisier=new DataInputStream(new FileInputStream(caleFisier))){
            int nrFacturi=fisier.readInt();
            for(var i=0;i<nrFacturi;i++){
                var denumire=fisier.readUTF();
                int an=fisier.readInt(),luna=fisier.readInt(),zi=fisier.readInt();
                var factura=new Factura(LocalDate.of(an,luna,zi),denumire);
                facturi.add(factura);
            }
        }
        return facturi;
    }
    public static void main(String[] args) throws Exception{

        var facturi=generareFacturi(LocalDate.of(2025,1,1),10);
        afisare("initial",facturi);
        salvare("facturi.dat", facturi);
        facturi=citire("facturi.dat");
        afisare("dupa citire fisier",facturi);

//    var factura = new Factura(LocalDate.of(2025, 03, 25), "Testescu SRL");
//        factura.adaugaLinie("Mere", 1.4, 12);
//        factura.adaugaLinie("Pere", 2.5, 22);
//        System.out.println(factura);
}
}
