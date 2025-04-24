import java.io.*;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void heavyAttack(String atacator,String atacat,List<Personaj> personaje){
       Personaj pAtacator=null;
       Personaj pAtacat=null;
        for(Personaj p:personaje){
            if(p.getNume().equals(atacator)){
                pAtacator=p;
            }
            if(p.getNume().equals(atacat)){
                pAtacat=p;
            }
        }
        int damagedealt=0;
        if(pAtacator!=null&&pAtacat!=null) {
            for (Map.Entry<String, Integer> items : pAtacator.getInventar().entrySet()) {
                if(items.getValue()>damagedealt){
                    damagedealt=items.getValue();
                }
            }
        }
        System.out.println("Personajul: "+pAtacat.getNume()+" a fost atacat de "+pAtacator.getNume()+" Si a pierdut "+damagedealt+" puncte HP "+"(HP actual: "+(pAtacat.getHp()-damagedealt)+" )");
    }
    public static void main(String[] args) {
        List<Personaj> personaje=new ArrayList<>();
        try(BufferedReader br=new BufferedReader(new FileReader("data\\personaje.txt"))){
            String linie;
            while((linie=br.readLine())!=null){
                String[] token=linie.split(",");
                int id=Integer.parseInt(token[0]);
                int hp=Integer.parseInt(token[2]);
                Map<String,Integer> inventar=new HashMap<>();
                for(int i=3;i< token.length;i=i+2){
                    inventar.put(token[i],Integer.parseInt(token[i+1]));
                }
                Personaj temp=new Personaj(id,token[1],hp,inventar);
                personaje.add(temp);
            }
        }catch(IOException e){
            System.err.println("Eroare la citirea elementelor din fisier: "+e.getMessage());
        }
        for(Personaj p:personaje){
            System.out.println(p.toString());
        }
        heavyAttack("Steve","Mihai",personaje);
        Collections.sort(personaje,Comparator.comparingInt(p->p.getInventar().values().stream().max(Integer::compare).orElse(0)));
        try(BufferedWriter writer=new BufferedWriter(new FileWriter("data\\stats.txt"))){
            for(Personaj p:personaje){
                writer.write("Nume personaj: "+p.getNume());
                for(Map.Entry<String,Integer> items:p.getInventar().entrySet()){
                    writer.write(" Arma: "+items.getKey()+" Damage: "+items.getValue());
                    break;
                }
                writer.newLine();
            }
        }catch(IOException e){
            System.err.println("Eroare la generearea fisierului cu stats: "+e.getMessage());
        }
    }
}