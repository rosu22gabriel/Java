import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

class Factura {
    private final String denumireClient;
    private final LocalDate dataEmitere;
    private final List<Linie> linii;

    // Observatie:
    //
    // Desi are toate campurile marcate ca final, clasa Factura NU este imutabila
    // Campul linii contine o referinta la un obiect (in cazul de fata un ArrayList)
    // Care poate fi modificat - vezi metoda adaugaLinie. In cazul de fata marcajul
    // final pentru linii specifica doar ca obiectul lista nu poate fi inlocuit cu totul
    // dupa ce obiectul Factura a fost creat, dar continutul lui poate fi modificat

    public Factura(String denumireClient, LocalDate dataEmitere) {
        this.denumireClient = denumireClient;
        this.dataEmitere = dataEmitere;
        this.linii = new ArrayList<>();
    }

    public String getDenumireClient() {
        return denumireClient;
    }

    public LocalDate getDataEmitere() {
        return dataEmitere;
    }

    public double getValoare() {
        double valoare = 0;
        for (var linie : linii) {
            valoare += linie.getValoare();
        }
        return valoare;
    }

    public int getNumarLinii() {
        return linii.size();
    }

    public Linie getLinie(int index) {
        return linii.get(index);
    }

    public void adaugaLinie(Linie linie) {
        linii.add(linie);
    }

    public void adaugaLinie(String produs, double pret, int cantitate) {
        adaugaLinie(new Linie(produs, pret, cantitate));
    }

    @Override
    public String toString() {
        var dateFormat = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
        final StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s, Client: %s%n",
                dateFormat.format(dataEmitere), denumireClient));
        for (var linie : linii) {
            sb.append(linie.toString() + System.lineSeparator());
        }
        return sb.toString();
    }

    static class Linie {
        private final String produs;
        private final double pret;
        private final int cantitate;

        public Linie(String produs, double pret, int cantitate) {
            this.produs = produs;
            this.pret = pret;
            this.cantitate = cantitate;
        }

        public String getProdus() {
            return produs;
        }

        public double getPret() {
            return pret;
        }

        public int getCantitate() {
            return cantitate;
        }

        public double getValoare() {
            return pret * cantitate;
        }

        @Override
        public String toString() {
            return String.format("%-25s %3d x %5.2f RON = %6.2f RON", produs, cantitate, pret, getValoare());
        }
    }
}

public class ProgramFacturi {
    public final static int NUMAR_MAXIM_PRODUSE = 10;

    public static List<Factura> generareListaFacturi(
            int numarFacturi, LocalDate dataMinima)  {
        // 1. Datele fixe folosite la generarea facturilor
        String[] denumiriClienti = new String[]{
                "ALCOR CONSTRUCT SRL",
                "SC DOMINO COSTI SRL",
                "SC TRANSCRIPT SRL",
                "SIBLANY SRL",
                "INTERFLOOR SYSTEM SRL",
                "MERCURY  IMPEX  2000  SRL",
                "ALEXANDER SRL",
                "METAL INOX IMPORT EXPOSRT SRL",
                "EURIAL BROKER DE ASIGURARE SRL"
        };

        String[] denumiriProduse = new String[]{
                "Stafide 200g",
                "Seminte de pin 300g",
                "Bulion Topoloveana 190g",
                "Paine neagra Frontera",
                "Ceai verde Lipton"

        };

        double[] preturiProduse = new double[]{
                5.20,
                12.99,
                6.29,
                4.08,
                8.99
        };

        // 2. Initializare generare
        Random rand = new Random();
        int numarMaximZile = (int) ChronoUnit.DAYS.between(dataMinima, LocalDate.now());
        List<Factura> facturi = new ArrayList<>();

        // 3. Generare facturi
        for (int indexFactura = 0; indexFactura < numarFacturi; indexFactura++) {
            var denumireClient = denumiriClienti[rand.nextInt(denumiriClienti.length)];
            var data = dataMinima.plusDays(rand.nextInt(numarMaximZile));

            var factura = new Factura(denumireClient, data);

            // Adaugam cel putin un rand
            var numarProduse = 1 + rand.nextInt(NUMAR_MAXIM_PRODUSE - 1);
            for (int indexProdus = 0; indexProdus < numarProduse; indexProdus++) {

                // Atentie: produsul si pretul trebuie sa fie corelate (aceeasi pozitie)
                var produsSelectat = rand.nextInt(denumiriProduse.length);
                var produs = denumiriProduse[produsSelectat];
                var pret = preturiProduse[produsSelectat];

                var cantitate = 1 + rand.nextInt(19);

                factura.adaugaLinie(produs, pret, cantitate);
            }
            facturi.add(factura);
        }
        return facturi;
    }

    static void afisareFacturi(List<Factura> facturi) {
        for (var factura : facturi) {
            System.out.println(factura);
        }
    }

    static void salvareFacturi(String caleFisier, List<Factura> facturi) throws IOException {
        // Daca a fost specificat si un folder
        if (new File(caleFisier).getParentFile() != null) {
            // ne asiguram ca acesta exista
            new File(caleFisier).getParentFile().mkdirs();
        }

        try (var fisier = new DataOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(caleFisier)))) {
            for (var factura : facturi) {
                // Scriere informatii globale pentru factura
                fisier.writeUTF(factura.getDenumireClient());
                fisier.writeInt(factura.getDataEmitere().getYear());
                fisier.writeInt(factura.getDataEmitere().getMonthValue());
                fisier.writeInt(factura.getDataEmitere().getDayOfMonth());
                fisier.writeInt(factura.getNumarLinii());

                for (var indexLinie = 0; indexLinie < factura.getNumarLinii(); indexLinie++) {
                    var linie = factura.getLinie(indexLinie);

                    // Scriem datele pentru linia curenta
                    fisier.writeUTF(linie.getProdus());
                    fisier.writeDouble(linie.getPret());
                    fisier.writeInt(linie.getCantitate());
                }
            }
        }
    }

    static List<Factura> incarcareFacturi(String caleFisier) throws IOException {
            List<Factura> facturi = new ArrayList<>();
            try (var fisier = new DataInputStream(
                    new BufferedInputStream(
                            new FileInputStream(caleFisier)))) {
                // Citim din fisier pana la producerea exceptiei EOFException
                while (true) {
                    // Citim datele globale pentru factura

                    var denumireClient = fisier.readUTF();
                    int an = fisier.readInt(), luna = fisier.readInt(), zi = fisier.readInt();
                    int numarLinii = fisier.readInt();

                    var factura = new Factura(denumireClient, LocalDate.of(an, luna, zi));

                    for (var indexLinie = 0; indexLinie < numarLinii; indexLinie++) {
                        // Citim datele pentru linia curenta
                        var denumire = fisier.readUTF();
                        var pret = fisier.readDouble();
                        var cantitate = fisier.readInt();
                        factura.adaugaLinie(denumire, pret, cantitate);
                    }
                    facturi.add(factura);
                }
            } catch (EOFException e) {
                // nu facem nimic
            }
            return facturi;
        }

        static void generareRaport(String caleRaport, List<Factura> facturi) throws IOException {
            // 1. Definim o clasa locala pentru a stoca datele pentru raport
            class DateClient implements Comparable<DateClient> {
                public final String denumireClient;
                public int numarFacturi = 0;
                public double valoareTotala = 0;

                public DateClient(String denumireClient){
                    this.denumireClient = denumireClient;
                }

                public String toString() {
                    return String.format("%-40s %3d facturi, TOTAL: %8.2f RON",
                            denumireClient, numarFacturi, valoareTotala);
                }

                public int compareTo(DateClient o) {
                    return -Double.compare(valoareTotala, o.valoareTotala);
                }
            }

            // Construim un dictionar de forma DenumireClient -> (DenumireClient, NumarFacturi, ValoareTotala)
            Map<String, DateClient> clienti = new HashMap<>();
            for (var factura : facturi) {
                var client = factura.getDenumireClient();

                // 1. Ne asiguram ca avem clientul in dictionar
                if (!clienti.containsKey(client)) {
                    clienti.put(client, new DateClient(client));
                }

                // 2. Extragem obiectul asociat clientului din dictionar
                // si adaugam datele corespunzatoare facturii curente
                var dateClient = clienti.get(client);
                dateClient.numarFacturi += 1;
                dateClient.valoareTotala += factura.getValoare();
            }

            // Punem datele calculate intr-o lista si sortam lista descrescator dupa valoare
            var raport = new ArrayList<DateClient>(clienti.values());
            Collections.sort(raport);

            // Scriem raportul in fisierul text
            try (PrintWriter fisier = new PrintWriter(caleRaport)) {
                for (var dateClient : raport) {
                    fisier.println(dateClient);
                }
            }
        }

        public static void main(String[] args) throws IOException {
         final String caleFisier = "date\\facturi.dat";
         var facturi = generareListaFacturi(30, LocalDate.of(2020, 1, 1));
         System.out.println("Facturi generate: ");
         afisareFacturi(facturi);
         salvareFacturi(caleFisier, facturi);

         System.out.println();
         System.out.println("======================================");
         System.out.println();
         var facturiIncarcate = incarcareFacturi(caleFisier);
         System.out.println("FacturiIncarcate");
         afisareFacturi(facturiIncarcate);

         generareRaport("date\\raport.txt", facturi);
         System.out.println("Raportul a fost generat.");

    }
}
