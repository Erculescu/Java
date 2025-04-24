//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        int[] vector={12,3,45,6,7,543,23,4,56};
        int mijloc= vector.length/2;
        FirMaxim fir1=new FirMaxim(vector,0,mijloc);
        FirMaxim fir2=new FirMaxim(vector,mijloc,vector.length);
        fir1.start();
        fir2.start();
        try{
            fir1.join();
            fir2.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        int Max_global=Math.max(fir1.getLocalMax(), fir2.getLocalMax());
        System.out.println("Maximul din vector este: "+Max_global);
    }
}