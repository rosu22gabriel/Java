import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

public class ReflectionSimple {
    public static void main(String[] args) throws Exception {
        Persoana pers = new Persoana("ion", 1);
        Class<?> clasaPersoana = pers.getClass();
        Class<?> clasaString = Class.forName("java.lang.String");
        Class<?> clasaInteger = Integer.class;

        System.out.println(clasaPersoana);
        System.out.println(clasaString);
        System.out.println(clasaInteger);

        System.out.println("Modificatori pentru " + clasaString.getName());
        int modificatori = clasaString.getModifiers();
        System.out.println(Modifier.toString(modificatori));

        for (Field camp: clasaPersoana.getDeclaredFields()) {
            camp.setAccessible(true);

            System.out.printf("%s %s %s = %s%n",
                    Modifier.toString(camp.getModifiers()),
                    camp.getType().getName(),
                    camp.getName(),
                    camp.get(pers));
        }

        var campCod = clasaPersoana.getDeclaredField("cod");
        campCod.setAccessible(true);
        campCod.set(pers, 3);
        System.out.println(pers);

        for (Method metoda : clasaPersoana.getDeclaredMethods()) {
            String modif = Modifier.toString(metoda.getModifiers());
            Class<?> tipReturnat = metoda.getReturnType();
            String denumire = metoda.getName();
            System.out.printf("%s %s %s, parametri: ",
                    modif, tipReturnat.getName(), denumire);
            for (Parameter param : metoda.getParameters()) {
                System.out.printf("%s ", param.getType().getName());
            }
            System.out.println();
        }

        Method metodaSetCod = clasaPersoana.getDeclaredMethod("setCod", int.class);
        metodaSetCod.setAccessible(true);
        metodaSetCod.invoke(pers, 3);
        System.out.println(pers);
    }
}

class Persoana {
    public transient String nume;
    private int cod;

    public Persoana(String nume, int cod) {
        this.nume = nume;
        this.cod = cod;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    @Override
    public String toString() {
        return "Persoana{" +
                "nume='" + nume + '\'' +
                ", cod=" + cod +
                '}';
    }
}