import java.util.List;
import java.util.Map;

public class Personaj {
    private final int id;
    private final String nume;
    private final int hp;
    private final Map<String,Integer> inventar;

    public Personaj(int id, String nume, int hp, Map<String, Integer> inventar) {
        this.id = id;
        this.nume = nume;
        this.hp = hp;
        this.inventar = inventar;
    }

    public int getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public int getHp() {
        return hp;
    }

    public Map<String, Integer> getInventar() {
        return inventar;
    }

    public void AdaugaInventar(String Denumire,Integer damage){
        this.inventar.put(Denumire,damage);
    }
    @Override
    public String toString() {
        return "Personaj{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", hp='" + hp + '\'' +
                ", inventar=" + inventar +
                '}';
    }
}
