
/*
Sa se construiasca o aplicatie in care:
- programul principal porneste 10 fire de executie noi
- fiecare fir de executie:
    afiseaza in ordine numerele de la 1 la 100 si identificatorul firului de executie
    asteapta 1-100 milisecunde intre afisari
- programul principal afiseaza identificatorul firului de executie care a castigat (a ajuns primul la 100)
- cand un fir de executie castiga celelalte fire de executie trebuie sa se opreasca si sa afiseze numarul la care s-au oprit
 */

import java.util.ArrayList;
import java.util.List;

class FirNumarare extends Thread {
    private int n = 0;
    static volatile long idCastigator = 0;

    @Override
    public void run() {
        for (int n = 1; n <= 100; n++) {
            try {
                Thread.sleep(1 + (int) (Math.random() * 100));
            } catch (InterruptedException e) {
            }
            if (idCastigator > 0) {
                System.out.println(getId() + " - am pierdut - am ajuns pana la " + n);
                return;
            }
            System.out.printf("#%2d: %d%n", getId(), n);
        }
        idCastigator = getId();
    }
}
public class Seminar01_AfisareNumere {
    public static void main(String[] args) throws InterruptedException {
        // 1. Pornim firele de executie si retinem referintele intr-o lista
        List<Thread> fire = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            var fir = new FirNumarare();
            fire.add(fir);
            fir.start();
        }

        // 2. Asteptam pana cand toate firele de executie s-au terminat
        while (fire.stream().anyMatch(fir -> fir.isAlive())) {
        }

        // 3. Afisam castigatorul
        System.out.println("Castigatorul este " + FirNumarare.idCastigator);
    }
}
