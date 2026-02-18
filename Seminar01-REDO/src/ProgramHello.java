import java.util.Scanner;

/**
 * Program Hello
 *
 * - afisare la consola
 * - citire de la consola folosind clasa Scanner
 */

public class ProgramHello {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Afisare Hello Ion Popescu!
        System.out.println("Nume: ");
        String nume = scanner.nextLine();
        System.out.println("Hello " + nume + "!");

        // Suma a doi intregi
        System.out.println("a = ");
        int a = scanner.nextInt();

        System.out.print("b = ");
        int b = scanner.nextInt();

        System.out.printf("%d + %d = %d, a = %d, b = %d", a, b, a + b, a, b);
    }
}