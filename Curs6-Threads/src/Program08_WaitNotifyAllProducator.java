// Doua fire de executie:
// producatorul - genereaza date si le pune intr-o zona comuna de dimensiune fixa (CoadaMesaje)
// consumatorul - extrage datele din zona comuna si le proceseaza
//
// Daca zona comuna este goala atunci consumatorul trebuie sa astepte pana
// la venirea urmatoarei valori, iar daca zona este plina producatorul trebuie
// sa astepte pana se elibereaza spatiu.

class CoadaMesaje {  // zona comuna de capacitate 1 element
    private String mesajCurent;
    private boolean areMesaj = false;

    public synchronized void adaugaMesaj(String mesaj) {
        // 1. Asteptam pana cand nu mai avem mesaj
        while (areMesaj) {
            try {
                System.out.printf("adauga(%s): avem deja un mesaj. Asteptam...%n", mesaj);
                wait(); // eliberam monitorul si asteptam
            } catch (InterruptedException e) {
            }
        }

        // 2. Salvam mesajul primit
        mesajCurent = mesaj;
        areMesaj = true;
        System.out.printf("adauga(%s): am pus mesajul in coada%n", mesaj);
        notifyAll();
    }

    public synchronized String extrageMesaj() {
        // 1. Asteptam pana cand avem un mesaj
        while (!areMesaj) {
            try {
                System.out.printf("extrage(): nu avem un mesaj in coada. Asteptam...%n", mesajCurent);
                wait(); // eliberam monitorul si asteptam
            } catch (InterruptedException e) {
            }
        }

        // 2. Intoarcem mesajul primit si eliberam zona
        areMesaj = false;
        System.out.printf("extrage(): am scos mesajul '%s' din coada %n", mesajCurent);
        notifyAll();
        return mesajCurent;
    }
}

class Consumator extends Thread {
    private CoadaMesaje coadaMesaje;

    public Consumator(CoadaMesaje coadaMesaje) {
        this.coadaMesaje = coadaMesaje;
    }

    @Override
    public void run() {
        while (true) {
            var mesaj = coadaMesaje.extrageMesaj();
            if (mesaj.equals("STOP")) {
                break;
            }

            System.out.print("MESAJ: ");
            for (int index = 0; index < mesaj.length(); index++) {
                System.out.print(mesaj.charAt(index));
                try { Thread.sleep(1000); } catch (InterruptedException e) { }
            }
            System.out.println();
        }
        System.out.println("Consumator - sfarsit");
    }
}

class Producator extends Thread {
    private CoadaMesaje coadaMesaje;

    public Producator(CoadaMesaje coadaMesaje) {
        this.coadaMesaje = coadaMesaje;
    }

    @Override
    public void run() {
        String[] mesaje = {"Ana", "are", "trei", "mere", "STOP"};
        for (var mesaj : mesaje) {
            coadaMesaje.adaugaMesaj(mesaj);
            try { Thread.sleep(1000); } catch (InterruptedException e) { }
        }
        System.out.println("Producator - sfarsit");
    }
}

public class Program08_WaitNotifyAllProducator {
    public static void main(String[] args) throws Exception {
        var coadaMesaje = new CoadaMesaje();
        var producator = new Producator(coadaMesaje);
        var consumator = new Consumator(coadaMesaje);

        System.out.println("MAIN - Inceput executie");

        producator.start();
        consumator.start();

        // Asteptam terminarea executiei
        producator.join();
        producator.join();

        System.out.println("MAIN - Sfarsit executie");
    }
}
