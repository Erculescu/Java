import java.io.*;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void generare_raport_materie(List<Student> stud,String materie){
        ArrayList<Map.Entry<String,Integer>> lista=new ArrayList<>();
        for(Student s:stud){
            if(s.getDenumireDisciplina().equals(materie)){
                Map.Entry<String,Integer> temp=new AbstractMap.SimpleEntry<>(s.getNume(),s.getNota());
                lista.add(temp);
            }
        }
        Collections.sort(lista,Comparator.comparing(Map.Entry<String,Integer>::getValue).reversed());
        System.out.println("Pentru materia: "+materie+" Studentii cu note sunt: ");
        for(Map.Entry<String,Integer> intrare:lista){
            System.out.println(intrare.getKey()+" Nota: "+intrare.getValue());
        }
    }
    public static void main(String[] args) {
        List<Student> listaStud=new ArrayList<>();
        try(BufferedReader br=new BufferedReader(new FileReader("data\\studenti.txt"))){
            String linie;
            while((linie= br.readLine())!=null){
                String[] data=linie.split(",");
                int nota=Integer.parseInt(data[2]);
                Student studTemp=new Student(data[0],data[1],nota);
                listaStud.add(studTemp);
            }
        }catch(IOException e){
            System.err.println("Eroare la citirea fisierului: "+e.getMessage());
        }
        Map<String,Double> hartaStud=new HashMap<>();
        for(Student stud:listaStud){
            hartaStud.put(stud.getNume(), hartaStud.getOrDefault(stud.getNume(),0.0)+1);
        }
        System.out.println("Exista "+hartaStud.size()+" studenti inscrisi");
        for(Map.Entry<String,Double> intrare:hartaStud.entrySet()){
            System.out.println("Studentul: "+intrare.getKey()+" este inscris la "+intrare.getValue()+" materii");
        }
        Scanner scan=new Scanner(System.in);
        System.out.println("Introduceti materia cautata:");
        String materia=scan.nextLine();
        generare_raport_materie(listaStud,materia);
        Map<String,Integer> materii=new HashMap<>();
        for(Student stud:listaStud){
            materii.put(stud.getDenumireDisciplina(),materii.getOrDefault(stud.getDenumireDisciplina(),0)+1);
        }
        try(BufferedWriter writer=new BufferedWriter(new FileWriter("data\\raport.txt"))){
            for(Map.Entry<String,Integer> intrare:materii.entrySet()){
                writer.write("Materia: "+intrare.getKey()+" Nr note inregistrate: "+intrare.getValue());
                writer.newLine();
            }
        }catch (IOException e){
            System.err.println("Eroare la scrierea fisierului: "+e.getMessage());
        }
    }
}