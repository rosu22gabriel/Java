
/*
    Cerinte:
    1) Un enum TipTranzactie cu valorile INTRARE si IESIRE si o metoda semn care sa dea semnul tranzactiei (-1 sau 1).

    2) O clasa imutabila Produs (cantitate - intreg, denumire - string).
    Constructori care sa primeasca toate valorile, respectiv doar codul.
    Doua produse sunt considerate egale daca au acelasi cod.

    3) O clasa Tranzactie (tip - tipul tranzactiei, data - tranzactiei, codProdus si cantitate - intreg).

    4) In clasa ProgramStocuri:
    - sa se defineasca un camp static stocuri de tip dictionar care sa asocieze fiecarui produs lista de tranzactii aferente
    - sa se defineasca o metoda statica AdaugaProdus(int cod, String denumire) care sa adauge un produs nou in lista de stocuri. Produsul NU trebuie sa existe in stocuri.
    - sa se defineasca o metoda statica AdaugaTranzactie(TipTranzactie tip, LocalDate data, int codProdus, int cantitate) care sa adauge o tranzactie nou. Produsul trebuie sa existe in stocuri.
    - sa se defineasca o metoda statica AfisareStocuri() care sa afiseze lista de produse cu: cod, denumire, stocCurent, data ultimei tranzactii.

 */

import stocuri.Produs;
import stocuri.TipTranzactie;
import stocuri.Tranzactie;

import java.time.LocalDate;
import java.util.*;

public class ProgramStocuri {
    static Map<Produs, List<Tranzactie>> stocuri = new HashMap<>();

    static void AdaugaProdus(int cod, String denumire) {
        stocuri.put(
                new Produs(cod, denumire),
                new ArrayList<>());
    }

    static void AdaugaTranzactie(
            TipTranzactie tip,
            LocalDate data,
            int codProdus,
            int cantitate) {
        var produs = new Produs(codProdus);
        if (!stocuri.containsKey(produs)) {
            throw new IllegalArgumentException("Produsul #" + codProdus + " nu exista.");
        }
        stocuri.get(produs).add(new Tranzactie(
                tip, data, codProdus, cantitate));
    }

    static void AfisareStocuri() {
        for (var element: stocuri.entrySet()) {
            var tranzactii = element.getValue();
            int stocTotal = 0;
            for (var tranzactie : tranzactii) {
                stocTotal += tranzactie.getTip().semn() * tranzactie.getCantitate();
            }

            String ultimaTranzactie = ", ultima tranzactie: ";
            if (!tranzactii.isEmpty()) {
                ultimaTranzactie += Collections.max(element.getValue(), new Comparator<Tranzactie>() {
                    @Override
                    public int compare(Tranzactie o1, Tranzactie o2) {
                        return o1.getData().compareTo(o2.getData());
                    }
                }).getData();
            }

            System.out.println(element.getKey() + " -> " + stocTotal + ultimaTranzactie);
        }
    }

    public static void main(String[] args) {
        stocuri = new HashMap<>();
        AdaugaProdus(1, "A");
        AdaugaProdus(0, "C");
        AdaugaProdus(2, "B");

        AdaugaTranzactie(TipTranzactie.INTRARE, LocalDate.of(2020,1,3), 1, 10);
        AdaugaTranzactie(TipTranzactie.INTRARE, LocalDate.of(2020,1,6), 2, 10);
        AdaugaTranzactie(TipTranzactie.IESIRE, LocalDate.of(2020,2,6), 1, 7);
        AdaugaTranzactie(TipTranzactie.INTRARE, LocalDate.of(2019,2,6), 1, 7);
        AfisareStocuri();
    }
}
