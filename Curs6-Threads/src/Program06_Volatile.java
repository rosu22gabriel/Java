// Varianta 1 (Gresita): Fara sincronizare

//public class Program06_Volatile {
//    private static boolean opresteExecutia = false;
//    private static Object ion = new Object();
//
//    public static void main(String[] args) throws InterruptedException {
//        var thread = new Thread(() -> {
//            int i = 0;
//            while (true) {
//                synchronized (ion) {
//                    if (!opresteExecutia) {
//                        break;
//                    }
//                }
//                i++;
//            }
//        });
//        thread.start();
//
//        Thread.sleep(1000);
//        synchronized (ion) {
//            opresteExecutia = true;
//        }
//
//        thread.join();
//        System.out.println("Gata!");
//    }
//}

// Varianta 2: Cu sincronizare
//public class Program06_Volatile {
//    private static boolean opresteExecutia = false;
//    private static Object lockOpresteExecutia = new Object();
//
//    public static void main(String[] args) throws InterruptedException {
//        var thread = new Thread(() -> {
//            int i = 0;
//
//            while (true) {
//                synchronized (lockOpresteExecutia) {
//                    if (opresteExecutia) {
//                        break;
//                    }
//                }
//                i++;
//            }
//        });
//        thread.start();
//
//        Thread.sleep(1000);
//        synchronized (lockOpresteExecutia) {
//            opresteExecutia = true;
//        }
//
//        thread.join();
//        System.out.println("Gata!");
//    }
//}

// Varianta 3: Folosind variabile volatile
public class Program06_Volatile {
    private static volatile boolean opresteExecutia = false;

    public static void main(String[] args) throws InterruptedException {
        var thread = new Thread(() -> {
            int i = 0;

            while (!opresteExecutia)
                i++;
        });

        thread.start();

        Thread.sleep(1000);
        opresteExecutia = true;

        thread.join();
        System.out.println("Gata!");
    }
}
