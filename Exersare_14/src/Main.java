import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        List<Student> studenti=new ArrayList<>();
        try(BufferedReader br=new BufferedReader(new FileReader("data\\studenti.txt"))){
            String linie;
            while((linie=br.readLine())!=null ){
                String[] data=linie.split(",");
                int id=Integer.parseInt(data[0]);
                Student studTemp=new Student(id,data[1],new ArrayList<>());
                studenti.add(studTemp);
            }
        }catch(IOException e){
            System.err.println("Eroare la citirea fisierului studenti.txt: "+e.getMessage());
        }
        for(Student stud:studenti){
            String FilePath="data\\note_";
            FilePath=FilePath+stud.getId()+".txt";
            List<Materie> mat=new ArrayList<>();
            try(BufferedReader br=new BufferedReader(new FileReader(FilePath))){
                String linie;
                while((linie=br.readLine())!=null){
                    String[] data=linie.split(",");
                    List<Double> listaTemp=new ArrayList<>();
                    for(int i=1;i<data.length;i++){
                        listaTemp.add(Double.parseDouble(data[i]));
                    }
                    mat.add(new Materie(listaTemp,data[0]));
                }
            }catch(IOException e){
                System.err.println("Eroare la citirea fisierului note_"+stud.getId()+".txt: "+e.getMessage());
            }
            for(Materie materii:mat){
                stud.AdaugaMaterie(materii);
            }
        }
        Collections.sort(studenti, Comparator.comparing(Student::getMedieFinala));
        try(BufferedWriter writer=new BufferedWriter(new FileWriter("data\\corigenti.txt"))){
            for(Student stud:studenti){
                if(stud.getMedieFinala()<5.0){
                    writer.write("Elevul: "+stud.getNume()+" este corigent, avand notele: ");
                    for(Materie mat:stud.getMaterii()){
                        writer.write(String.valueOf(mat.getMedie())+", ");
                    }
                    writer.newLine();
                }
            }
        }catch(IOException e){
            System.err.println("Eroare la crearea fisierului corigenti.txt: "+e.getMessage());
        }
    }
}