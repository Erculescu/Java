public class Consultatie {
    String specialitate;
    int codManevra;
    int numar;

    public Consultatie(String specialitate, int codManevra, int numar) {
        this.specialitate = specialitate;
        this.codManevra = codManevra;
        this.numar = numar;
    }

    public String getSpecialitate() {
        return specialitate;
    }

    public void setSpecialitate(String specialitate) {
        this.specialitate = specialitate;
    }

    public int getCodManevra() {
        return codManevra;
    }

    public void setCodManevra(int codManevra) {
        this.codManevra = codManevra;
    }

    public int getNumar() {
        return numar;
    }

    public void setNumar(int numar) {
        this.numar = numar;
    }

    @Override
    public String toString() {
        return "Consultatie{" +
                "specialitate='" + specialitate + '\'' +
                ", codManevra=" + codManevra +
                ", numar=" + numar +
                '}';
    }
}
