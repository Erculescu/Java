import java.util.ArrayList;
import java.util.Scanner;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void citire_studenti(ArrayList<Student> studenti){
        Scanner scanner=new Scanner(System.in);
        System.out.println("Introduceti numarul de studenti:");
        int nr=scanner.nextInt();
        for(int i=0;i<nr;i++){
            int id= scanner.nextInt();
            String nume= scanner.next();
            int grupa= scanner.nextInt();

            ArrayList<Nota> note=new ArrayList<>();
            int nrNote= scanner.nextInt();
            for(int j=0;j<nrNote;j++){
                String disciplina= scanner.next();
                Float nota=scanner.nextFloat();
                note.add(new Nota(disciplina,nota));
            }
            Student stud=new Student(id,nume,grupa,An.Anul_1,note);
            studenti.add(stud);
        }
    }

    public static void citire_Catalog(ArrayList<Student> studenti){
        Scanner scanner=new Scanner(System.in);
        System.out.println("Introduceti disciplina:");
        String disciplina=scanner.next();
        System.out.println("Introduceti nr de note:");
        int nr=scanner.nextInt();
        for(int i=0;i<nr;i++){
            System.out.println("Introduceti nota:");
            float nota=scanner.nextFloat();
            System.out.println("Introduceti id:");
            int id=scanner.nextInt();
            for(int j=0;j<studenti.size();j++){
                if(studenti.get(j).getId()==id){
                    studenti.get(j).getNote().add(new Nota(disciplina,nota));
                }
            }
        }
    }
    public static void afisare_Catalog(ArrayList<Student> studenti){
        int nr=studenti.size();
        for(int i=0;i<nr;i++){
            Student stud=studenti.get(i);
            System.out.println("Id stud: "+stud.getId());
            System.out.println("Nume: "+stud.getNume());
            System.out.println("Grupa: "+stud.getGrupa());
            System.out.println("Anul: "+stud.getAnul());
            System.out.println("Note: "+stud.getNote());

        }
    }

    public static void main(String[] args) {

        ArrayList<Student> studenti=new ArrayList<>();
        citire_studenti(studenti);
        afisare_Catalog(studenti);

    }
}