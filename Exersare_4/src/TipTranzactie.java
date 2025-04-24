public enum TipTranzactie {
    Intrare(1),Iesire(-1);
    private final int val;
    TipTranzactie(int val){
        this.val=val;
    }

    public int getVal() {
        return val;
    }
}
