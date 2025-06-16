public class Intrebare {
    int id;
    String text;
    String raspuns;
    double punctaj;

    public Intrebare(int id, String text, String raspuns, double punctaj) {
        this.id = id;
        this.text = text;
        this.raspuns = raspuns;
        this.punctaj = punctaj;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRaspuns() {
        return raspuns;
    }

    public void setRaspuns(String raspuns) {
        this.raspuns = raspuns;
    }

    public double getPunctaj() {
        return punctaj;
    }

    public void setPunctaj(double punctaj) {
        this.punctaj = punctaj;
    }

    @Override
    public String toString() {
        return "Intrebare{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", raspuns='" + raspuns + '\'' +
                ", punctaj=" + punctaj +
                '}';
    }
}
