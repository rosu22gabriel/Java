class TestIncrementareCuSincronizare {
    private int n = 0;

    public TestIncrementareCuSincronizare(Object lock) {
        lockPentruN = lock;
    }

    // Varianta 1: marcam metoda ca synchronized
//    public synchronized void incrementare(){
//        n++;
//    }

    // Varianta 2: utilizare bloc synchronized cu referinta this (similar cu varianta 1)
//    public void incrementare() {
//        synchronized (this) {
//            n++;
//        }
//    }

    // Varianta 3: utilizare bloc synchronized cu referinta specifica
    private Object lockPentruN;

    public void incrementare() {
        synchronized (this) {
            n++;
        }
    }

    public int get() {
        synchronized (lockPentruN) {
            return n;
        }
    }
}

class FirExecutieIncrementareSincronizat extends Thread {
    TestIncrementareCuSincronizare incrementare;

    public FirExecutieIncrementareSincronizat(TestIncrementareCuSincronizare incrementare) {
        this.incrementare = incrementare;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            incrementare.incrementare();
        }
    }
}

public class Program04_SincronizareAcces {
    public static void main(String[] args) throws Exception {
        var lock = new Object();
        var test = new TestIncrementareCuSincronizare(lock);

        // Construim doua fire de executie; ambele primesc
        // o referinta la acelasi obiect (test)
        var fir1 = new FirExecutieIncrementareSincronizat(test);
        var fir2 = new FirExecutieIncrementareSincronizat(test);

        // pornim firele de executie
        fir1.start(); // apeleaza test.incrementare() de 10000 ori
        fir2.start(); // apeleaza test.incrementare() de 10000 ori

        // asteptam sa se termine cele doua fire de executie
        fir1.join();
        fir2.join();

        // rezultatul corect este 20000; pentru ca AVEM sincronizare
        // nu putem avea doua apleuri de incrementare simultan; ordinea
        // operatiilor va fi mereu de forma:

        // fir1: O1) citire valoare n
        // fir1: O2) calcul valoare n + 1
        // fir1: O3) scriere valoare calculata

        // fir2: O1) citire valoare n
        // fir2: O2) calcul valoare n + 1
        // fir2: O3) scriere valoare calculata
        System.out.println(test.get());
    }
}
