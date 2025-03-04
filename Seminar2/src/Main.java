//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
import java.util.Scanner;
public class Main {

    public static Student[]  citireStud(){
        Scanner scanner=new Scanner(System.in);
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
    };
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
        stud=citireStud();
        for(var student:stud){
            System.out.println(student);
        }

//tema de citit online ase+ cerinta citire catalog din online.ase.ro


    }
}