import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
class FirDepunere extends Thread{
    private final Cont cont;
    public FirDepunere(Cont cont){
        this.cont=cont;
    }
    @Override
    public void run(){
        for (int i=0;i<1000;i++){
            cont.depune(1);
        }
    }
}
public class Main {
    public static void main(String[] args) {
        Cont cont=new Cont();
        ArrayList<FirDepunere> fire=new ArrayList<>();

        for(int i=1;i<=10;i++){
            FirDepunere f=new FirDepunere(cont);
            fire.add(f);
            f.start();
        }
        for(FirDepunere f:fire){
            try{
                f.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }

        }
        System.out.println("Soldul final este: "+cont.getSold());

    }
}