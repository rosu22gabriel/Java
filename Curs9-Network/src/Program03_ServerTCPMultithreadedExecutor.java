import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

public class Program03_ServerTCPMultithreadedExecutor {
    public static void main(String[] args) throws IOException {
        final int PORT_NUMBER = 8293;
        final int THREAD_COUNT = 4;

        var threadPool = Executors.newFixedThreadPool(THREAD_COUNT);
        try (ServerSocket serverSocket = new ServerSocket(PORT_NUMBER)) {
            while (true) {
                Socket socket = serverSocket.accept();
                threadPool.submit(() -> procesareCerere(socket));
            }
        }
    }

    private static void procesareCerere(Socket socket) {
        try {
            System.out.println("Asteptam un client...");
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            ) {
                System.out.println("Am stabilit conexiunea. Asteptam mesajul...");

                String mesajPrimit = in.readLine();
                System.out.println("Mesaj primit - " + mesajPrimit);
                out.printf("Mesajul primit este: %s%n", mesajPrimit);
                System.out.println("Am terminat de procesat cererea - inchidem conexiunea");
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException exception) {
            }
        }
    }
}
