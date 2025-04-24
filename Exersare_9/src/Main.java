import java.io.*;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {


        ArrayList<Student> studenti=new ArrayList<>();
        try(BufferedReader br=new BufferedReader(new FileReader("data\\studenti.txt"))){
            String linie;
            while((linie=br.readLine())!=null){
                String[] tokenuri=linie.split(",");
                int nota=Integer.parseInt(tokenuri[2]);
                Student studTemp=new Student(tokenuri[0],tokenuri[1],nota);
                studenti.add(studTemp);
            }
        }catch(IOException e){
            System.err.println("Eroare la citirea fisierului"+e.getMessage());
        }
        for(Student stud:studenti){
            System.out.println(stud);
        }
        System.out.println("===Cerinta 2===");
        Set<String> participanti=new HashSet<>();
        for(Student stud:studenti){
            participanti.add(stud.getNume());
        }
        for(String part:participanti){
            System.out.println(part);
        }
        Collections.sort(studenti,Comparator.comparing(Student::getNota).reversed());
        System.out.println("===Cerinta 3===");
        System.out.println("Introduceti materia cautata: ");
        Scanner scanner=new Scanner(System.in);
        String materia=scanner.nextLine();
        for(Student stud:studenti){
            if(stud.getDisciplina().equals(materia)){
                System.out.println(stud);
            }
        }
        HashMap<String,Integer> raport=new HashMap<>();
        try(BufferedWriter writer=new BufferedWriter(new FileWriter("data\\raport.txt"))){
            for(Student stud:studenti){
                raport.put(stud.getDisciplina(),raport.getOrDefault(stud.getDisciplina(),0)+1);
            }
            for(Map.Entry<String,Integer> intrare: raport.entrySet()){
                writer.write(intrare.getKey()+": "+intrare.getValue());
                writer.newLine();
            }
        }catch (IOException e){
            System.err.println("Eroare la scrierea fisierului: "+e.getMessage());
        }
    }
}