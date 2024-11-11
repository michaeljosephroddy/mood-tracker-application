package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class H2DatabaseConnector {

    private static String DB_URL = "jdbc:h2:mem:test";
    private static Connection connection;
    private static Statement statement;

    public H2DatabaseConnector() {
    }

    // NB might put these methods in an interface

    public static void connect() {
        try {
            H2DatabaseConnector.connection = DriverManager.getConnection(DB_URL);
            System.out.println("Connected to H2 in-memory database.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createMoodEntriesTable() {
        // NB userid would normally be a foreign here from the users table
        String sql = "create table moodentries (moodentryid varchar(50) primary key, userid varchar(50), moods varchar(1000), date varchar(50), description varchar(1000))";
        try {
            H2DatabaseConnector.statement.execute(sql);
            System.out.println("Created table moodentries");
        } catch (SQLException e) {
            System.out.println("Failed to create table moodentries " + e.getMessage());
            e.printStackTrace();
        }

    }

    public static void disconnect() {
        try {
            H2DatabaseConnector.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return H2DatabaseConnector.connection;
    }

    public static void createStatement() {
        try {
            H2DatabaseConnector.statement = H2DatabaseConnector.connection.createStatement();
        } catch (SQLException e) {
            System.err.println("Error creating Statement: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // SELECT
    public ResultSet executeSQLRead(String sql) {
        try {
            return H2DatabaseConnector.statement.executeQuery(sql);
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    // INSERT UPDATE DELETE
    public int executeSQLUpdate(String sql) {
        try {
            return H2DatabaseConnector.statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
            e.printStackTrace();
        }

        return 0;
    }

}
