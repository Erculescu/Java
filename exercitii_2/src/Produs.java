public class Produs {
    int cod;
    String denumire;

    public Produs(int cod, String denumire) {
        this.cod = cod;
        this.denumire = denumire;
    }

    public Produs(int cod) {
        this.cod = cod;
    }
    public String toString(){
        return "Cod intern "+ cod+" "+"Denumirea: "+ denumire;
    }

    public boolean egal(Produs prod){
        return this.cod==prod.cod;
    }

    public int getCod() {
        return cod;
    }
}
