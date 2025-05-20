import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    final static String DB_URL = "jdbc:sqlite:tranzactii.db";

    static void genereazaBazaDeDate() throws Exception {
        final String[] denumiri = new String[]{
                "Stafide 200g",
                "Seminte de pin 300g",
                "Bulion Topoloveana 190g",
                "Paine neagra Frontera",
                "Ceai verde Lipton"
        };
        var random = new Random();
        try (var conexiune = DriverManager.getConnection(DB_URL);
             var comanda = conexiune.createStatement();
             var cmdInsert = conexiune.prepareStatement("INSERT INTO TRANZACTII VALUES(?,?,?,?)")
        ) {
            comanda.executeUpdate("CREATE TABLE IF NOT EXISTS TRANZACTII (" + "CODPRODUS INTEGER,DENUMIREPRODUS STRING(100)," + "PRET REAL, CANTITATE INTEGER)");
            System.out.println("Am sters " + comanda.executeUpdate("DELETE FROM TRANZACTII"));

            for (int i=0;i<10;i++){
                int cod=random.nextInt(denumiri.length);
                cmdInsert.setInt(1,cod);
                cmdInsert.setString(2,denumiri[cod]);
                cmdInsert.setDouble(3,random.nextDouble()*10);
                cmdInsert.setInt(4,1+random.nextInt(13));
                cmdInsert.executeUpdate();
            }
        }

    }
    static void afisare_stocuri() throws Exception{
        List<Tranzactie> tranzactii=new ArrayList<>();
        try(Connection conexiune=DriverManager.getConnection(DB_URL);
            Statement comanda=conexiune.createStatement();
            ResultSet rezultat=comanda.executeQuery("SELECT * FROM TRANZACTII")){
            System.out.println("COD |DENUMIRE PRODUS    |PRET   |CANTITATE  ");
            System.out.println("____________________________________________");
            while (rezultat.next()) {
                int cod=rezultat.getInt("CODPRODUS");
                String denumire=rezultat.getString("DENUMIREPRODUS");
                double pret=rezultat.getDouble("PRET");
                int cantitate=rezultat.getInt("CANTITATE");
                Tranzactie temp=new Tranzactie(cod,denumire,pret,cantitate);
                tranzactii.add(temp);
            }
        }
        var produse=tranzactii.stream().collect(Collectors.toMap(Tranzactie::getCod,Tranzactie::getDenumire,(p1,p2)->p1));
        var tGrupate=tranzactii.stream().collect(Collectors.groupingBy(Tranzactie::getCod));
        for(var cod:produse.keySet()){
            var stoc=tGrupate.get(cod).stream().mapToInt(Tranzactie::getCantitate).sum();
            System.out.printf("%d %-30s %3d%n",cod,produse.get(cod),stoc);
        }
    }
    public static void main(String[] args) throws Exception {
        genereazaBazaDeDate();
        afisare_stocuri();
    }
}