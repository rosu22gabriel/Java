// Varianta 1: Derivare clasa Thread si suprascriere metoda run
class FirExecutie1 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("A");
        }
    }
}

// Varianta 2: Implementare interfata Runnable si utilizarea
// constructorului clasei Thread pentru construirea obiectului

class FirExecutie2 implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.print("B");
        }
    }
}

public class Program01_ThreadRunnable {
    public static void main(String[] args) throws Exception {
        System.out.println("Start MAIN");
        // Pornire fir de executie - metoda start
        Thread fir1 = new FirExecutie1();
        fir1.start();

        new Thread(new FirExecutie2()).start();

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.print("C");
            }
        }).start();

        fir1.join();
        System.out.println("Sfarsit MAIN");
    }
}
