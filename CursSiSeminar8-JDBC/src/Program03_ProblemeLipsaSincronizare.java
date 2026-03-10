class TestIncrementareFaraSincronizare {
    private int n = 0;

    public void incrementere() {
        n++; // -> race condition
        // Operatiile sunt de fapt:
        // O1) citire valoare n
        // O2) calcul valoare n + 1
        // O3) scriere valoare calculata
    }

    public int get() {
        return n;
    }
}

class FirExecutieIncrementare extends Thread {
    TestIncrementareFaraSincronizare incrementare;

    public FirExecutieIncrementare(TestIncrementareFaraSincronizare incrementare) {
        this.incrementare = incrementare;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
            incrementare.incrementere();
        }
    }
}

public class Program03_ProblemeLipsaSincronizare {
    public static void main(String[] args) throws Exception {
        var test = new TestIncrementareFaraSincronizare();

        // construim doua fire de executie; ambele primesc
        // o referinta la acelasi obiect (test)

        var fir1 = new FirExecutieIncrementare(test);
        var fir2 = new FirExecutieIncrementare(test);

        // pornim firele de executie
        fir1.start(); // apeleaza test.incrementare de 10000 de ori
        fir2.start(); // apeleaza test.incrementare de 10000 de ori

        // asteptam sa se termine cele doua fire de executie
        fir1.join();
        fir2.join();

        // Rezultatul corect este 20000; pentru ca nu avem sincronizare
        // vom avea un rezulat mai mic pentru ca avem situatii de forma
        // fir1: O1) citire valoare n
        // fir2: O2) citire valoare n
        // fir1: O2) calcul valoare n + 1
        // fir2: O2) calcul valoare n + 1
        // fir1: O3) scriere valoare calculata
        // fir2: O3) scriere valoare calculata
        // (avem doua apeluri de incrementare(). dar valoarea lui n creste cu 1
        System.out.println(test.get());
    }
}
