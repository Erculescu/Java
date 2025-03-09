import java.util.Objects;

public final class Produs {
    private final int cod;
    private final String denumire;

    public Produs(int cod, String denumire) {
        this.cod = cod;
        this.denumire = denumire;
    }

    public Produs(int cod) {
        //BEST PRACTICE:
        this(cod,"[Necunoscut]");

//        this.cod = cod;
//        this.denumire = "necunoscuta";

    }

    public int getCod() {
        return cod;
    }

    public String getDenumire() {
        return denumire;
    }

    @Override
    public String toString() {
        return String.format("#%3d %-15s", getCod(),getDenumire());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Produs produs = (Produs) o;
        return cod == produs.cod;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cod);
    }


}
