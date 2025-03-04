public enum TipTranzactie {INTRARE(1),IESIRE(-1);
    private final int valoare;
    TipTranzactie(int valoare){
        this.valoare=valoare;
    }
    public int getValoare(){
        return valoare;
    }
}
