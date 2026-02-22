import java.util.*;

class Student implements Comparable, Cloneable {
    private Map<String, Integer> note;
    private final String nume;

    public Student(String nume) {
        this.nume = nume;
        this.note = new HashMap<>();
    }

    public String getNume() {
        return nume;
    }

    public boolean areNota(String disciplina) {
        return note.containsKey(disciplina);
    }

    public int getNota(String disciplina) {
        if (areNota(disciplina)) {
            return note.get(disciplina);
        } else {
            throw new NoSuchElementException(String.format(
                    "Studentul '%s' nu are nota la disciplina '%s'.", nume, disciplina
            ));
        }
    }

    public Set<String> getDiscipline() {
        return note.keySet();
    }

    public void adaugaNote(String disciplina, int nota) {
        if (nota < 1 || nota > 10) {
            throw new IllegalArgumentException("Nota invalida");
        }
        note.put(disciplina, nota);
    }

    public void stergeNota(String disciplina) {
        note.remove(disciplina);
    }

    @Override
    public int compareTo(Object o) {
        Student altStudent = (Student)o;
        return nume.compareTo(altStudent.nume);
    }

    @Override
    public Student clone() {
        try {
            Student copie = (Student)super.clone();
            copie.note = new HashMap<>(this.note);
            return copie;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Clone nu e suportat.", e);
        }
    }
}



public class Program03_Studenti {
    public static void afiseazaCatalog(List<Student> studenti, String disciplina) {
        var studentiSortati = new ArrayList<>(studenti);
        Collections.sort(studentiSortati);
        System.out.println();
        System.out.println("Catalog" + disciplina);
        System.out.println();

        for (var student : studentiSortati) {
            System.out.printf("%-8s", student.getNume());
            if (student.areNota(disciplina)) {
                System.out.printf("%3d", student.getNota(disciplina));
            } else {
                System.out.print("ABS");
            }
            System.out.println();
        }
        System.out.println("=============");
    }

    public static void main(String[] args) {
        Student ion = new Student("Ion");
        ion.adaugaNote("Java", 6);
        ion.adaugaNote("PAW", 9);
        System.out.println(ion);

        ion.adaugaNote(new String("Java"), 10);
        System.out.println(ion);

        ion.stergeNota("PAW");
        ion.adaugaNote("SGBD", 8);
        System.out.println(ion);

        for (String disciplina : ion.getDiscipline()) {
            System.out.println(disciplina + " -> " + ion.getNota(disciplina));
        }

        Student maria = new Student("Maria");
        maria.adaugaNote("Java", 9);
        maria.adaugaNote("PAW", 10);

        Student vasile = new Student("Vasile");
        List<Student> studenti = new ArrayList<>();
        studenti.add(maria);
        Collections.addAll(studenti, vasile, ion);
        afiseazaCatalog(studenti, "Java");

        var clonaIon = ion.clone();
        clonaIon.adaugaNote("Java", 8);
        clonaIon.adaugaNote("Test", 10);
        System.out.println(ion);
        System.out.println(clonaIon);
    }
}
