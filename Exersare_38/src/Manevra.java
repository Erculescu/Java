public class Manevra {
    int cod;
    int durata;
    Double tarif;

    public Manevra(int cod, int durata, Double tarif) {
        this.cod = cod;
        this.durata = durata;
        this.tarif = tarif;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public Double getTarif() {
        return tarif;
    }

    public void setTarif(Double tarif) {
        this.tarif = tarif;
    }

    @Override
    public String toString() {
        return "Manevra{" +
                "cod=" + cod +
                ", durata=" + durata +
                ", tarif=" + tarif +
                '}';
    }
}
