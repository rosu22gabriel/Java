import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class Persoana {
    private int cod;
    private String nume;

    public Persoana() {
        this(0, "-");
    }

    public Persoana(int cod, String nume){
        this.cod = cod;
        this.nume = nume;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Student{");
        sb.append("cod=").append(cod);
        sb.append(", nume='").append(nume).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public int getCod() {
        return this.cod;
    }
}

public class Program01_OperatiiJDBC {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:sqlite:date\\persoane.db";

        // 1. Cerere simpla (stergerea inregistrarilor existente)
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {
            int numarInregistrari = statement.executeUpdate("DELETE FROM Persoane");
            System.out.printf("Au fost sterse %d inregistrari.%n", numarInregistrari);
        }

        // 2. Cerere cu parametru (inserare inregistrari din lista)
        var persoane = List.of(
                new Persoana(1, "Popescu Maria"),
                new Persoana(2, "Marinescu Ion")
        );

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Persoane(Cod, Nume) VALUES (?, ?)");) {
            for (var persoana : persoane) {
                statement.setInt(1, persoana.getCod());
                statement.setString(2, persoana.getNume());
                statement.executeUpdate();
            }
        }

        // 3. Citire inregistrari din tabela (utilizare ResultSet)

        List<Persoana> persoaneBD = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();) {
            try (ResultSet date = statement.executeQuery("SELECT Cod, Nume FROM Persoane")) {
                while (date.next()) {
                    persoaneBD.add(new Persoana(date.getInt(1), date.getString(2)));
                    // sau: date.getInt("Cod"), date.getString("Nume")
                }
            }
        }

        persoaneBD.stream().forEach(student -> System.out.println(student));

        // 4. Utilizare tranzactii explicite
        persoaneBD.add(new Persoana(3, "Ionescu Titel"));
        try (Connection connection = DriverManager.getConnection(url)) {
            connection.setAutoCommit(false);

            try {
                // Operatia 1: Stergem datele existente
                try (var deleteStatement = connection.createStatement()) {
                    deleteStatement.executeUpdate("DELETE FROM Persoane");
                }

                // Operatia 2: Adaugam inregistrarile din lista
                try (var insertStatement =
                        connection.prepareStatement("INSERT INTO Persoane(Cod, Nume) VALUES (?, ?)")) {
                    for (var persoana : persoaneBD) {
                        insertStatement.setInt(1, persoana.getCod());
                        insertStatement.setString(2, persoana.getNume());
                        insertStatement.executeUpdate();
                    }
                }
            } catch (Exception e) {
                connection.rollback();
                throw e;
            }
        }

        // Verificare date dupa tranzactie
        try (Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement()) {
            try (ResultSet date = statement.executeQuery("SELECT Cod, Nume FROM Persoane")) {
                while (date.next()) {
                    System.out.printf("%d - %s%n",
                            date.getInt("Cod"),
                            date.getString("Nume"));
                }
            }
        }
    }
}
