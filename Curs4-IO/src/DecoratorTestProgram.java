import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/** Component Interface **/
interface Generator {
    int next();
}

/** Concrete Components **/

class SequentialGenerator implements Generator {
    private int current;

    public SequentialGenerator(int startNumber) {
        current = startNumber;
    }

    public int next() {
        return current++;
    }
}

class RandomGenerator implements Generator {
    private int min, max;
    private Random random;

    public RandomGenerator(int min, int max) {
        this.min = min;
        this.max = max;
        this.random = new Random();
    }

    public int next() {
        return min + random.nextInt(max - min);
    }
}

/** Decoractor Abstract Class **/
abstract class Decorator implements Generator {
    Generator generator;

    public Decorator(Generator generator) {
        this.generator = generator;
    }

    public int next() {
        return generator.next();
    }
}

/** Decorator Implementations (concrete classes) **/

// Extrage doar numerele pare din generatorul sursa
class EvenGenerator extends Decorator {
    public EvenGenerator(Generator generator){
        super(generator);
    }

    public int next() {
        while (true) {
            int value = generator.next();
            if (value % 2 == 0) {
                return value;
            }
        }
    }
}

// Dubleaza valoarea extrasa din generatorul sursa
class DoubleGenerator extends Decorator {
    public DoubleGenerator(Generator generator) {
        super(generator);
    }

    public int next() {
        return generator.next() * 2;
    }
}

// Salveaza numerele extrase din generatorul sursa si
// adauga metodele toString() si sum()
class LoggingGenerator extends Decorator {
    List<Integer> values;

    public LoggingGenerator(Generator generator) {
        super(generator);
        values = new ArrayList<>();
    }

    public int next() {
        int value = generator.next();
        values.add(value);
        return value;
    }

    public int sum() {
        int sum = 0;
        for (var value : values) {
            sum += value;
        }
        return sum;
    }

    @Override
    public String toString() {
        var result = new StringBuilder();
        for (var value : values) {
            result.append(value + " ");
        }
        return result.toString();
    }
}

// Adauga metoda nextVector(size) pentru a extrage
// un vecotr de intregi din generatorul sursa
class VectorGenerator extends Decorator {
    public VectorGenerator(Generator generator) {
        super(generator);
    }

    public int[] nextVector(int size) {
        var result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = generator.next();
        }
        return result;
    }
}

public class DecoratorTestProgram {
    public static void main(String[] args) {

        // Exemplul 1: Utilizare generatori simpli
        var serial = new SequentialGenerator(100);
        System.out.printf("Exemplul 1: Generator serial (100): %d %d %d%n",
                serial.next(), serial.next(), serial.next());

        var random = new RandomGenerator(0, 20);
        System.out.printf("Exemplul 1: Generator aleator [0, 20): %d %d %d%n",
                random.next(), random.next(), random.next());

        // Exemplul 2: Generator de numere aleatoare pare
        var even = new EvenGenerator(random); // aplicare decorator
        System.out.printf("Exemplul 2: Generator aleator numere pare [0, 20): %d %d %d%n",
                even.next(), even.next(), even.next());


        // Exemplul 3: Generator de vectori de numere in secventa, inmultite cu 2
        var vector = new VectorGenerator(new DoubleGenerator(serial));
        System.out.printf("Exemplul 3: Vector: %s%n",
                Arrays.toString(vector.nextVector(10)));

        // Exemplul 4: Utilizare complexa generatori (secventa / retinere in log / ...)
        // seq -> log(sursa) -> even -> double -> log(generatorFinal)
        System.out.println();
        System.out.println("Exemplul 4");

        var sursa = new LoggingGenerator(new SequentialGenerator(1));
        var generatorFinal = new LoggingGenerator(new DoubleGenerator(new EvenGenerator(sursa)));

        for (int i = 0; i < 5; i++) {
            System.out.println(generatorFinal.next());
        }

        System.out.println("Numerele generate de secventa originala " + sursa);
        System.out.println("Numerele finale (utilizate pentru suma): " + generatorFinal);
        System.out.println("Suma este " + generatorFinal.sum());
    }

}