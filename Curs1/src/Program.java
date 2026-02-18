import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
//        System.out.println("Hello world");
//        System.out.println("Test");
//        System.out.println(10);
//        System.out.printf("%d + %d = %d", 2, 1, 3);

//        var scanner = new Scanner(System.in);
//        scanner.useDelimiter("[\\s,\\,]+"); // acceptam spatii sau virgula ca delimitatori
//        int cod = scanner.nextInt(); // introducem 3, Maria
//        String nume = scanner.next();
//        System.out.printf("#%d - %s", cod, nume); // afiseaza #3 - Maria
//        int i = 7;
//        double j;

        // Detectie automata a tipului folosind cuvantul cheie var (incepand cu JDK 10)
//        var scanner = new Scanner(System.in);
//
//        // Declarare constante
//        final int NUMAR_MAXIM_ELEMENTE = 1000;

        // Variabilele si constantele pot contine
        // - O valoare corespunzatoare unui tip de baza (int, double, ...)
        // - O referinta la un obiect (adresa de memorie)
//
//        int val = Integer.parseInt("19"); // conversie din String
//        Integer i = val;    // Autoboxing
////        String test = i.toString(); // utilizare metoda
//        int temp = i;   //  Unboxing
//
//        System.out.println('\n' + temp);
//
//        boolean areProiect = true, prezentExamen = true;
//        int notaExamen = 8;
//        boolean estePromovat = areProiect && prezentExamen && notaExamen > 8;
//
//        long numarSecunde = 3452342343L;
//        int numarOre = (int)(numarSecunde / 60 / 60);
//
//        int varsta = 21;
//        varsta ++;
//        double pi = 4.0 / 1 - 4.0 / 3 + 4.0 / 5 - 4.0 / 7 + 4.0 / 9;

        // if / else - instructiune alternativa simpla
        // switch / case / default / break - instructiune alternativa multipla
        // for / while / do - instructiuni repetitive
        // break / continue - instructiuni pentru intreruperea executiei instructiunilor repetitive

        // SIRURI DE CARACTERE
        // Sunt manipulate prin intermediul obiectelor clasei String care:
        // - Contin o colectie de caractere
        // - Sunt imutabile

//        String test1 = new String(); // construire sir vid
//        String test2 = new String("Test2"); // construire pe baza de sir existent;
//        String test3 = "Test3"; // echivalent cu declaratia anterioara
//
//        String test = "Test";
//        for (int i = 0; i < test.length(); i++) {   // determinare numar caractere - .length()
//            System.out.println(test.charAt(i)); // accesare caracter individual - .charAt(index)
//        }
//
//        // Concatenare si formatare:
//        // 1. Utilizare operator de concatenare
//        String hello = "Hello";
//        String world = "World";
//        String rezultat1 = hello + " " + world + "!";
//
//        // 2. Utilizare functie statica format (sintaxa la fel ca la printf)
//        String rezultat2 = String.format("%s %s!", hello, world);
//
//        System.out.println(rezultat1);
//        System.out.println(rezultat2);

        // Functii de comparare si cautare:
        // boolean equals(string s1): Intoarce true daca sirul curent este egal cu s1
        // Varianta case insensitive boolean equalsIgnoreCase(string s1)

        // int compareTo(string s1): Intoarce 0 daca sirul curent este egal cu s1, valoare negativa daca este mai mare
        // valoare pozitiva daca este mai mica
        // varianta case insensitive compareToIgnoreCase(string s1)

        // int indexOf(string s1): Intoarce pozitia(=> 0) sirului s1 in sirul curent sau -1 daca s2 nu a fost gasit
        // boolean startsWith(string s1) intoarce true daca sirul curent incepe cu s1
        // similar boolean endsWith(string s1)

        // Segmentare siruri de caracter - String[] split(String regex)
//        String lista = "1, 6, 2, 4, 2, 5, 2";
//        String[] valoriLista = lista.split(",");
//        int[] valori = new int[valoriLista.length];
//        for (int index = 0; index < valori.length; index++) {
//            valori[index] = Integer.parseInt(valoriLista[index]);
//        }
//
//        // Extragere subsir - String substring(int begin, int end)
//        String data = "20210314"; // yyyymmdd
//        int zi = Integer.parseInt(data.substring(6, 8));

        // Metode de transformare:
        // String toUpperCase() - transformare litere mici in litere mari
        // String toLowerCase() - transformare litere mari in litere mici
        // String trim() - eliminare spatii  de la inceputul si sfarsitul sirului

        // Masive unidimensionale - Declarare
        // Masivele unidimensionale (vector) sunt obiecte care:
        // Contin elemente de acelasi tip (primitive sau referinte
        // Au o dimensiune fixa specificata la initializare

        // Declarare si initializare
//        Student[] vStudenti; declarare fara initializare
//        int[] vector1 = new int[3]; // declarare si initializare cu valori default
//        int[] vector2 = {1, 2, 4};  // declarare si intializare cu literal
//
//        // Accesare elemente, accesare dimensiune vector si parcurgere secventiala
//        int n = vector1.length;
//        int val = vector1[1];
//
//        for (int element: vector1) {
//            /* utilizare element */
//        }

        // Metode statice utile - In clasa java.util.Arrays:
        // int binarySearch(vector, valoare) - cautare element (-1 daca elementul nu este gasit
        // void sort(vector) - sortare elemente vector
        // boolean equals(vector1, vector2) - verifica daca doi vectori sunt egali

        // Functii cu numar variabil de argumente - tip... denumire
        // Au un singur parametru de acest fel, obligatoriu pe ultima pozitie
        // Parametrul este tratat ca un vector in interiorul functiei
//        int[] valori;

//        static int suma(int... valori) {
//            int suma = 0;
//            for (int element : valori) {
//                suma += element;
//            }
//            return suma;
//        }
        // Apeluri suma(1, 5, 2); sau int[] v = (1, 2, 3); suma(v)

        // Pot fi declarate masive multidimensionale - comportament similar cu vector de vectori
        int [][][] cub = {
                {{1, 2}, {3, 4}, {5, 6}},
                {{1, 2}, {3, 4}, {5, 6}}
        };

        int[][] m = cub[0]; // {{1, 2}, {3, 4}, {5, 6}},
        int n = cub.length; // 2

        // Parcurgere
        for (int[][] matrice : cub) {
            for (int[] vector : matrice) {
                for (int element: vector) {
                    System.out.println(element);
                }
            }
        }
    }
}
