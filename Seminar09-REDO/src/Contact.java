import java.io.Serializable;

public class Contact implements Serializable {
    private int cod;
    private String nume;
    private String telefon;

    public Contact(int cod, String nume, String telefon) {
        this.cod = cod;
        this.nume = nume;
        this.telefon = telefon;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "cod=" + cod +
                ", nume='" + nume + '\'' +
                ", telefon='" + telefon + '\'' +
                '}';
    }
}
