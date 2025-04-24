public class FirMaxim extends Thread{
    private final int[] vector;
    private final int start;
    private final int sfarsit;
    private int localMax=Integer.MIN_VALUE;

    public FirMaxim(int[] vector,int start,int sfarsit){
        this.vector=vector;
        this.start=start;
        this.sfarsit=sfarsit;
    }
    @Override
    public void run(){
        for(int i=start;i<sfarsit;i++){
            if(vector[i]>localMax){
            localMax=vector[i];
            }

        }
    }

    public int getLocalMax() {
        return localMax;
    }
}
