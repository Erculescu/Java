import java.util.Objects;

public class Personaj {
    private final int id;
    private final String nume;
    private final TipPersonaj tipPersonaj;
    private final float hp;

    public Personaj(int id, String nume, TipPersonaj tipPersonaj, float hp) {
        this.id = id;
        this.nume = nume;
        this.tipPersonaj = tipPersonaj;
        this.hp = hp;
    }

    public int getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public TipPersonaj getTipPersonaj() {
        return tipPersonaj;
    }

    public float getHp() {
        return hp;
    }

    @Override
    public String toString() {
        return "Personaj{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", tipPersonaj=" + tipPersonaj +
                ", hp=" + hp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Personaj personaj = (Personaj) o;
        return id == personaj.id && tipPersonaj == personaj.tipPersonaj;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipPersonaj);
    }
}
