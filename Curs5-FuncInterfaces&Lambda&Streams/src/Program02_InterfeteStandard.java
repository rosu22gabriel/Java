import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Program02_InterfeteStandard {
    static <T> List<T> generareLista(int numarElemente, Supplier<T> generator) {
        var rezultat = new ArrayList<T>();
        for (int index = 0; index < numarElemente; index++) {
            rezultat.add(generator.get());
        }
        return rezultat;
    }

    static <T> void consumaLista(List<T> lista, Consumer<T> consumator) {
        for (var valoare : lista) {
            consumator.accept(valoare);
        }
    }

    static <T> List<T> filtrareLista(List<T> lista, Predicate<T> predicat) {
        var rezultat = new ArrayList<T>();
        for (var valoare : lista) {
            if (predicat.test(valoare)) {
                rezultat.add(valoare);
            }
        }
        return rezultat;
    }

    public static void main(String[] args) {
        // Construire si utilizare interfata standard Generator
        Supplier<Integer> generatorAleator =
                () -> (int) (Math.random() * 200 - 100); // numar intre -100 si +100
        var lista = generareLista(7, generatorAleator);

        // Construire operatie folosind expresie lambda
        Consumer<List<Integer>> afisareLista = oLista -> {
            consumaLista(
                    oLista,
                    elem -> System.out.print(elem + " "));
            System.out.println();
        };
        afisareLista.accept(lista);

        // Utilizare metode de compunere
        Predicate<Integer> estePar = n -> n % 2 == 0;
        Predicate<Integer> estePozitiv = n -> n > 0;
        afisareLista.accept(filtrareLista(lista, estePar.and(estePozitiv)));

        // Implementare interfata functionala standard prin clasa anonima
        var suma = new Consumer<Integer>() {
            int suma;
            @Override
            public void accept(Integer valoare) {
                suma += valoare;
            }

            public int getSuma() { return suma; }
        };

        consumaLista(lista, suma);
        System.out.println("Suma este " + suma.getSuma());
    }

}
