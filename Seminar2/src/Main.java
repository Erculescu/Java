//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
import java.util.Arrays;
import java.util.Scanner;
public class Main {

    public static Student[]  citireStud(Scanner scanner){

        int n=Integer.parseInt(scanner.nextLine());
        Student[] stud=new Student[n];
       for(var index=0;index<n;index++)

        {
            var linie = scanner.nextLine().split(",");

            var student = new Student(
                    Integer.parseInt(linie[0]),
                    linie[1],
                    linie[2],anul.valueOf(linie[3])
                    );

            stud[index] = student;
            linie = scanner.nextLine().split(",");

            for (var j = 0; j < linie.length; j += 2) {
                student.add_nota(new Nota(linie[j], Integer.parseInt(linie[j + 1])));
            }
        }
return stud;
    }
public static void afisareCatalog(Student[] stud, String denumireMaterie){
    System.out.println("Denumire materie: "+denumireMaterie);
    int[] note=new int[0];
    String[] nume=new String[0];
    for(var student:stud){
        if(student.getNote().length>0){
            for(var nota:student.getNote()){
                if(nota.getNumeDisciplina().equals(denumireMaterie)){
                    //System.out.println(student.getNume()+" "+nota.getNota());
                    note=(int[]) Arrays.copyOf(note,note.length+1);
                    note[note.length-1]=nota.getNota();
                    nume=(String[]) Arrays.copyOf(nume,nume.length+1);
                    nume[nume.length-1]=student.getNume();
                }
            }
        }
    }
    for(var i=0;i<note.length;i++){
        for(var j=i+1;j<note.length;j++){
            if(note[i]<note[j]){
                int temp=note[i];
                note[i]=note[j];
                note[j]=temp;
                String temp2=nume[i];
                nume[i]=nume[j];
                nume[j]=temp2;
            }
        }
    }
    for(var i=0;i<note.length;i++){
        System.out.println(nume[i]+" "+note[i]);
    }
}


    public static Student[] citireCatalog(Student[] stud,Scanner scanner){


        while(scanner.hasNextLine()){
        var denummire=scanner.nextLine();
        int n=Integer.parseInt(scanner.nextLine());
        for(var index=0;index<n;index++){
            var linie=scanner.nextLine().split(",");
            int idNou=Integer.parseInt(linie[0]);
            int notaNoua=Integer.parseInt(linie[1]);
            for(var student:stud){
                if(student.getIdStudent()==idNou){
                    student.add_nota(new Nota(denummire,notaNoua));
                }
            }

        }}
        return stud;
    }
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        var nota=new Nota("Java",9);
        System.out.println(nota);
nota.setNota(10);
        System.out.println(nota);
        var ion=new Student(1,"Ion","1018",anul.II);
        ion.add_nota(nota);
        ion.add_nota(new Nota("PAW",8));
        ion.add_nota(new Nota("Java",8));
        System.out.println(ion);

//        int n=Integer.parseInt(scanner.nextLine());
//        System.out.println(n);
        Student[] stud=new Student[0];
        stud=citireStud(scanner);
        for(var student:stud){
            System.out.println(student);
        }
        stud=citireCatalog(stud,scanner);
        for(var student:stud){
            System.out.println(student);
        }
        afisareCatalog(stud,"Programare Java");

//tema de citit online ase+ cerinta citire catalog din online.ase.ro


    }
}