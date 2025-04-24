import java.util.Objects;

public final class Produs {
private final int cod;
private final String denumire;

    public Produs(int cod, String denumire) {
        this.cod = cod;
        this.denumire = denumire;
    }

    public Produs(int cod) {
        this.cod = cod;
        this.denumire="";
    }

    public int getCod() {
        return cod;
    }

    public String getDenumire() {
        return denumire;
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
