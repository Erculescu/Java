import java.util.ArrayList;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        ArrayList<Personaj> personaje=new ArrayList<>();
        personaje.add(new Personaj(1,"Rares",TipPersonaj.luck,120));
        Protagonist p=new Protagonist(personaje,55.0f);
        p.CalculConflict();
        }
    }
