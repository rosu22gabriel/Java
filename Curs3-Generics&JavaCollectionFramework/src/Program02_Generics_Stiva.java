/**
 * Exemplu clasa generica Stiva (implementata ca lista simplu inlantuita
 * cu adaugare / stergere de la inceputul listei)
 * @param <T> Tipul de obiect stocat in stiva
 */

class Stiva<T> {
    /**
     * Clasa privata pentru elementele listei simplu inlantuite
     * @param <T> Tipul de obiect stocat in stiva
     */

    private static class Nod<T>{
        T valoare;
        Nod<T> urmator;

        public Nod(T valoare, Nod<T> urmator) {
            this.valoare = valoare;
            this.urmator = urmator;
        }
    }

    /**
     * Stiva este considerata vida daca avem capul listei null. In cazul in care
     * este ne-nul refera primul element din stiva.
     */
    Nod cap = null;

    public void push(T valoare) {
        cap = new Nod(valoare, cap);
    }

    public T pop() {
        if (esteGoala()) {
            throw new UnsupportedOperationException("Stiva este goala");
        }

        var temp = cap.valoare;
        cap = cap.urmator;
        return (T)temp;
    }

    public boolean esteGoala() {
        return cap == null;
    }
}


// *** Exemplu de utilizare ***

class Persoana {
    private int cod;
    private String nume;

    public Persoana(int cod, String nume) {
        this.cod = cod;
        this.nume = nume;
    }

    @Override
    public String toString() {
        return "Persoana{" +
                "cod=" + cod +
                ", nume='" + nume + '\'' +
                '}';
    }
}

public class Program02_Generics_Stiva{
    // Functie generica pentru afisarea elementelor stivei.
    public static <T> void afisare(Stiva<T> stiva) {
        // Folosim o stiva temporara pentru pastrarea elementelor
        var temp = new Stiva<T>();
        while(!stiva.esteGoala()) {
            var val = stiva.pop();
            temp.push(val);
            System.out.println(val);
        }

        // dupa afisare adaugam la loc elementele in stiva originala
        while(!temp.esteGoala()) {
            stiva.push(temp.pop());
        }
    }

    public static void main(String[] args) {
        var stivaPersoane = new Stiva<Persoana>();
        stivaPersoane.push(new Persoana(1, "Ion"));
        stivaPersoane.push(new Persoana(2, "Maria"));
        afisare(stivaPersoane);

        var stivaIntregi = new Stiva<Integer>();
        for (int i = 1; i <= 10; i++) {
            stivaIntregi.push(i);
        }
        afisare(stivaIntregi);
    }
}