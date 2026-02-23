import java.util.ArrayList;
import java.util.List;

interface Operatie {
    // Metoda statica - apelabila prin Operatie.operatieIdentica
    static Operatie operatieIndentica() {
        // Utilizam o clasa anonima
        return new Operatie() {
            @Override
            public int aplica(int valoare) {
                return valoare;
            }
        };
    }

    int aplica(int valoare);

    // Metoda default - implementata pe baza celorlalte metode din interfata
    default List<Integer> aplicaPeLista(List<Integer> lista) {
        var rezultat = new ArrayList<Integer>();
        for (var valoare : lista) {
            rezultat.add(this.aplica(valoare));
        }
        return rezultat;
    }
}

class Incrementare implements Operatie {
    int delta;

    public Incrementare(int delta) {
        this.delta = delta;
    }

    @Override
    public int aplica(int valoare) {
        return valoare + delta;
    }

    // nu este nevoie sa implementam metoda aplicaPeLista - va fi mostentia din interfata
}

public class Program01_InterfeteFunctionaleLambda {

    static void afisare(String mesaj, List<Integer> lista) {
        System.out.print(mesaj + ":");
        for (var valoare : lista) {
            System.out.print(" " + valoare);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        var lista = List.of(1, 3, 5, 6);
        afisare("Initial", lista);

        // 1. Utilizare implementare din clasa Incrementare
        var incrementare5 = new Incrementare(5);
        afisare("Incrementare cu 5",
                incrementare5.aplicaPeLista(lista));

        // 2. Utilizare metoda statica din interfata
        afisare("Operatie identica",
                Operatie.operatieIndentica().aplicaPeLista(lista));

        // 3. Implementare interfata prin clasa anonima
        Operatie incrementare3 = new Operatie() {
            public int aplica(int valoare) {
                return valoare + 3;
            }
        };
        afisare("Incrementare cu 3",
                incrementare3.aplicaPeLista(lista));

        // 4. Implementare interfata prin expresie lambda
        Operatie incrementare100 = valoare ->  valoare + 100;
        afisare("Incrementare cu 100", incrementare100.aplicaPeLista(lista));

        // 5. Utilizare referinta la metoda
        Operatie refMetoda = incrementare5::aplica;
        afisare("Incrementare cu 5", refMetoda.aplicaPeLista(lista));
    }


}
