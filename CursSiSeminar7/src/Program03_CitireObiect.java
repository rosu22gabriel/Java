/*
* Exemplu: Citire obiect oarecare
*
* Fie urmatoarele adnotari optionale pentru campuri:
* - IgnoreCamp - campul va fi ignorat la citire
* - Mesaj(string) - mesajul care trebuie afisat pentru utilizator la citirea valorii
* - Dimensiune(int minim, int maxim) - dimensiunile valide pentru un camp de tip String
*
*Se cere sa se scrie o metoda care primeste ca parametru o clasa si citeste
* de la consola un obiect respectand adnotarile de mai sus aplicate campurilor clasei.
*
*Se presupune ca pentru fiecare camp ne-ignorat exista o metoda publica set corespunzatoare
*
* */

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.Scanner;

@Retention(RetentionPolicy.RUNTIME);
@interface IgnoraCamp {
}

@Retention(RetentionPolicy.RUNTIME)
@interface Mesaj {
    String value();
}

@Retention(RetentionPolicy.RUNTIME)
@interface Dimensiune{
    int minim();
    int maxim();
}

public class Program03_CitireObiect {
    public static Object CitireObiect(Class<?> clasa)
            throws NoSuchMethodException,
            IllegalAccessException,
            InstantiationError,
            InvocationTargetException, InstantiationException {
        var scanner = new Scanner(System.in);

        // 1. Construim o instanta din obiectul solicitat
        // folosind constructorul implicit
        var rezultat = clasa.getConstructor().newInstance();

        // 2. Pentru fiecare camp declarat in clasa
        for (var camp : clasa.getDeclaredFields()) {
            if (Modifier.isStatic(camp.getModifiers())) {
                continue; // ignoram campurile statice
            }

            if (camp.isAnnotationPresent(IgnoraCamp.class)) {
                continue; // ignoram campurile marcate cu @IgnoraCamp
            }

            // Afisam mesajul specificat sau un mesaj implicit daca nu exista
            String mesaj = String.format(
                    "Introduceti valoarea pentru campul '%s': ",
                    camp.getName());
            if (camp.isAnnotationPresent(Mesaj.class)) {
                mesaj = camp.getAnnotationsByType(Mesaj.class)[0].value();
            }

            System.out.print(mesaj);

            // Citim valoarea introdusa de utilizator ca string
            var linie = scanner.nextLine();

            // O convertim la tipul campului
            Object valoare = null;
            if (camp.getType() == String.class) {
                valoare = linie;

            }

        }
        return rezultat;
    }


}

