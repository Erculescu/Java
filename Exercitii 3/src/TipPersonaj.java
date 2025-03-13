public enum TipPersonaj {
    strength(1),perception(0),endurance(-1),intelligence(2),agillity(-2),luck(3);
    private int semn;

    TipPersonaj(int semn){
        this.semn=semn;
    }
    public int semn(){
        return semn;
    }


}
