import java.util.Scanner;

class TestSleepJoin extends Thread {
    @Override
    public void run() {
        // Afisam doua litere pe secunda pana cand suntem intrerupti
        try {
            while(true) {
                Thread.sleep(500);
                System.out.print("A");
            }
        } catch (InterruptedException e) {
            // cand am fost intrerupti afisam un mesaj
            System.out.println("Am fost intrerupt...");
        }
        // si continuam cu alte operatii
        System.out.println("Gata!");
    }
}

public class Program02_SleepJoin {
    public static void main(String[] args) throws Exception {
        // Pornim firul de executie secundar
        var fir = new TestSleepJoin();
        fir.start();

        // cand am citit un rand de la tastatura
        new Scanner(System.in).nextLine();
        // trimitem mesajul de intrerupere catre firul de executie secundar
        System.out.println("Am intrerupt firul secundar...");

        // Asteptam sa se termine executia firului secundar
        fir.join();
        System.out.println("Firul secundar s-a incheiat");
    }
}
