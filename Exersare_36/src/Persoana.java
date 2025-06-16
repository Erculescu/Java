import java.util.ArrayList;
import java.util.List;

class Tranzactie{
    int cod;
    String simbol;
    String tip;
    int cantitate;
    float pret;

    public Tranzactie(int cod, String simbol, String tip, int cantitate, float pret) {
        this.cod = cod;
        this.simbol = simbol;
        this.tip = tip;
        this.cantitate = cantitate;
        this.pret = pret;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getSimbol() {
        return simbol;
    }

    public void setSimbol(String simbol) {
        this.simbol = simbol;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public float getPret() {
        return pret;
    }

    public void setPret(float pret) {
        this.pret = pret;
    }

    @Override
    public String toString() {
        return "Tranzactie{" +
                "cod=" + cod +
                ", simbol='" + simbol + '\'' +
                ", tip='" + tip + '\'' +
                ", cantitate=" + cantitate +
                ", pret=" + pret +
                '}';
    }
}
public class Persoana {
    int cod;
    String cnp;
    String nume;
    List<Tranzactie> tranzactii;
    float valoare_curenta;

    public Persoana(int cod, String cnp, String nume) {
        this.cod = cod;
        this.cnp = cnp;
        this.nume = nume;
        this.valoare_curenta=0;
        this.tranzactii=new ArrayList<>();
    }
    public void adaugaTranzactie(Tranzactie tranz){
        tranzactii.add(tranz);
        if(tranz.tip.equals("cumparare")){
            valoare_curenta+=tranz.getCantitate()*tranz.getPret();
        }else{
            valoare_curenta-=tranz.getCantitate()*tranz.getPret();
        }
    }

    public float getValoare_curenta() {
        return valoare_curenta;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public String toString() {
        return "Persoana{" +
                "cod=" + cod +
                ", cnp='" + cnp + '\'' +
                ", nume='" + nume + '\'' +
                ", tranzactii=" + tranzactii +
                ", valoare_curenta=" + valoare_curenta +
                '}';
    }
}
