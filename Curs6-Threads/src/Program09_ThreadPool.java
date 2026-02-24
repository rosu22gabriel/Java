import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class TaskCautareInFisier implements Runnable {
    String caleFisier;
    String textDeCautat;

    public TaskCautareInFisier(String caleFisier, String textDeCautat){
        this.caleFisier = caleFisier;
        this.textDeCautat = textDeCautat;
    }

    @Override
    public void run() {
        if (!caleFisier.endsWith(".java")) {
            return;
        }
        try (var fileIn = new BufferedReader(new FileReader(caleFisier))) {
            int lineNumber = 1;
            String line;
            Thread.sleep(3);

            while ((line = fileIn.readLine()) != null) {
                if (line.contains(textDeCautat)) {
                    System.out.printf("%s:%d %s%n", caleFisier, lineNumber, line);
                }
                lineNumber++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class Program09_ThreadPool {
    static final int NUMAR_FIRE = 8;
    static ExecutorService threadPool = Executors.newFixedThreadPool(NUMAR_FIRE);

    public static void showFolders(File cale, String textDeCautat) throws IOException {
        if (cale.isDirectory()) {
            for (var child : cale.listFiles()) {
                showFolders(child, textDeCautat);
            }
        } else {
            threadPool.submit(
                    new TaskCautareInFisier(cale.getCanonicalPath(), textDeCautat)
            );
        }
    }

    public static void main(String[] args) throws Exception {
        var start = System.nanoTime();
        showFolders(new File("..\\.."), "Persoana");
        threadPool.shutdown();
        threadPool.awaitTermination(30, TimeUnit.SECONDS);
        System.out.printf("Gata - %d ms", (System.nanoTime() - start) / 1000000);
    }

}
