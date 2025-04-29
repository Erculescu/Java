import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Autoturism {
    private final int serie;
    private final String marca;
    private final String model;
    private final Float putere;

    public Autoturism(int serie, String marca, String model, Float putere) {
        this.serie = serie;
        this.marca = marca;
        this.model = model;
        this.putere = putere;
    }

    public int getSerie() {
        return serie;
    }

    public String getMarca() {
        return marca;
    }

    public String getModel() {
        return model;
    }

    public Float getPutere() {
        return putere;
    }
    public boolean esteDeLux(){
        List<String> brand=new ArrayList<>();
        brand.add("BMW");
        brand.add("Audi");
        brand.add("Mercedes");
        brand.add("Ferrari");
        brand.add("Bentley");
        brand.add("Rolls Royce");
        brand.add("Porsche");
        for(String aut:brand){
            if(this.marca.equals(aut)){
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Autoturism that = (Autoturism) o;
        return serie == that.serie;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(serie);
    }

    @Override
    public String toString() {
        return "Autoturism{" +
                "serie=" + serie +
                ", marca='" + marca + '\'' +
                ", model='" + model + '\'' +
                ", putere=" + putere +
                '}';
    }
}
