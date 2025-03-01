import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        System.out.println("Introduceti numarul de linii: ");
        var n=scanner.nextInt();
        System.out.println("Introduceti numarul de coloane: ");
        var m=scanner.nextInt();
        var matrice=new int[n][m];
        System.out.println("Introduceti elementele matricei: ");
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                matrice[i][j]=scanner.nextInt();
            }
        }
        var array=new int[m*n];
        var index=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                array[index++]=matrice[i][j];
            }
        }
        for(int i=0;i<array.length;i++){
            for(int j=i+1;j<array.length;j++){
                if(array[i]>array[j]){
                    var aux=array[i];
                    array[i]=array[j];
                    array[j]=aux;
                }
            }
        }
        index=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                matrice[i][j]=array[index++];
            }
        }
        System.out.println("Matricea ordonata este: ");
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                System.out.print(matrice[i][j]+" ");
            }
            System.out.println();
        }



    }
}