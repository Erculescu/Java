import java.util.ArrayList;
import java.util.Random;

class FirNumarare extends Thread{
    static long idCastigator=-1;
    static final Object lock=new Object();
    @Override
    public void run(){
        try{
        for(var i=1;i<=100;i++){
            Thread.sleep(1+new Random().nextInt(100));
            synchronized (lock){
            if(idCastigator>0){
                System.out.printf("#%d: am pierdut    %n",getId(),i);
                return;
            }
            }
            System.out.printf("#%d: %d%n",getId(),i);
        }
            idCastigator=getId();
    }catch (InterruptedException e){ }

}}
public class Program {
    public static void main(String[] args){
        var fire=new ArrayList<FirNumarare>();
        for(int i=0;i<10;i++){
            FirNumarare fir=new FirNumarare();
            fir.start();
            fire.add(fir);

        }
        while(fire.stream().anyMatch(f->f.isAlive())){}
        System.out.println("\nGata! A castigat \n"+FirNumarare.idCastigator);
    }
}
