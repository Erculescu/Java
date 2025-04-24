import java.util.List;

public class Bloc {
    public static final class Apartament{
        private final String familie;
        private final int nrLocatari;

        public Apartament(String familie, int nrLocatari) {
            this.familie = familie;
            this.nrLocatari = nrLocatari;
        }

        public String getFamilie() {
            return familie;
        }

        public int getNrLocatari() {
            return nrLocatari;
        }

        @Override
        public String toString() {
            return "Apartament{" +
                    "familie='" + familie + '\'' +
                    ", nrLocatari=" + nrLocatari +
                    '}';
        }
    }
    public String Adresa;
    public List<Apartament> apartamente;

    public Bloc(String adresa, List<Apartament> apartamente) {
        Adresa = adresa;
        this.apartamente = apartamente;
    }

    public String getAdresa() {
        return Adresa;
    }

    public List<Apartament> getApartamente() {
        return apartamente;
    }
    public void addApartament(Apartament apartament){
        apartamente.add(apartament);
    }

    @Override
    public String toString() {
        return "Bloc{" +
                "Adresa='" + Adresa + '\'' +
                ", apartamente=" + apartamente +
                '}';
    }
}
