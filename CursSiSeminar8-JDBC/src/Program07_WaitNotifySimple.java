class AplicatieNumarSleep {
    private volatile boolean avemNumar;
    private volatile int numar;

    public void citire() {
        try {
            Thread.sleep((int) (Math.random() * 1000));
        } catch (InterruptedException e) {
        }
        numar = (int) (Math.random() * 3000);
        avemNumar = true;
    }

    public void afisare() {
        while (!avemNumar) {
            System.out.println("Asteptam un numar...");
        }
        System.out.println("Numarul este "  + numar);
    }
}

class AplicatieNumarWait {
    private volatile boolean avemNumar;
    private volatile int numar;

    public synchronized void citire() {
        try {
            Thread.sleep((int) (Math.random() * 1000));
        } catch (InterruptedException e) {
        }
        numar = (int) (Math.random() * 3000);
        avemNumar = true;
        notifyAll();
    }

    public synchronized void afisare() {
        while (!avemNumar) {
            System.out.println("Asteptam un numar...");
            try { wait(); } catch (InterruptedException e) { }
        }
        System.out.println("Numarul este " + numar);
    }
}

public class Program07_WaitNotifySimple {
    public static void main(String[] args) {
        // Varianta 1 : Folosind o bucla simpla
        // var app = new AplicatieNumarSleep();

        // Varianta 2: Folosind o bucla cu wait() si notifyAll()
        var app = new AplicatieNumarWait();

        // Fir de executie care asteapta numarul pentru a-l afisa
        new Thread(() -> {
            app.afisare();
        }).start();

        // Fir de executie care genereaza numarul de afisat
        new Thread(() -> {
            app.citire();
        }).start();
    }
}
