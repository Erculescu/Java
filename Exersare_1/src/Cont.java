public class Cont {
 int sold;
 synchronized void depune(int suma){
     sold=sold+suma;
 }

    public int getSold() {
        return sold;
    }
}
