public class Vehicul {
    private final String nrInmatriculare;
    private final String marca;
    private final int pasageri;

    public Vehicul(String nrInmatriculare, String marca, int pasageri) {
        this.nrInmatriculare = nrInmatriculare;
        this.marca = marca;
        this.pasageri = pasageri;
    }

    public String getNrInmatriculare() {
        return nrInmatriculare;
    }

    public String getMarca() {
        return marca;
    }

    public int getPasageri() {
        return pasageri;
    }


    @Override
    public String toString() {
        return "Vehicul{" +
                "nrInmatriculare='" + nrInmatriculare + '\'' +
                ", marca='" + marca + '\'' +
                ", pasageri=" + pasageri +
                '}';
    }
}
