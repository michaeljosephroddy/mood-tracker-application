package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import contract.DBConnector;
import exception.DatabaseConnectionException;

public class H2DatabaseConnector implements DBConnector {

    String dbFilePath = System.getenv("MOOD_TRACKER_DB_FILE_PATH");
    String user = System.getenv("DATABASE_USER");
    String password = System.getenv("DATABASE_PASSWORD");

    private String dbUrl = "jdbc:h2:./database/mood_tracker_db";
    private Connection connection;
    private Statement statement;

    public void connect() {
        try {
            System.out.println(dbFilePath);
            connection = DriverManager.getConnection(dbUrl, user, password);
            System.out.println("Connected to H2 in-memory database.");
        } catch (SQLException e) {
            var exception = new DatabaseConnectionException("Failed to connect to database", e);
            System.err.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            var exception = new DatabaseConnectionException("Error closing connection", e);
            System.err.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    public void createStatement() {
        try {
            if (connection != null) {
                statement = connection.createStatement();
            }
        } catch (SQLException e) {
            var exception = new DatabaseConnectionException("Error creating Statement", e);
            System.err.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    public ResultSet executeSQLRead(String sql) {
        try {
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            var exception = new DatabaseConnectionException("Error executing sql query", e);
            System.err.println(exception.getMessage());
            exception.printStackTrace();
        }

        return null;
    }

    public int executeSQLUpdate(String sql) {
        try {
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            var exception = new DatabaseConnectionException("Error executing sql update", e);
            System.err.println(exception.getMessage());
            exception.printStackTrace();
        }

        return 0;
    }

    public void createMoodEntriesTable() {
        String sql = "create table if not exists moodentries (moodentryid VARCHAR(50) PRIMARY KEY, "
                + "userid VARCHAR(50), moods VARCHAR(1000), date VARCHAR(50), "
                + "description VARCHAR(1000))";
        try {
            statement.execute(sql);
            System.out.println("Created table moodentries");
        } catch (SQLException e) {
            var exception = new DatabaseConnectionException("Error creating table moodentries", e);
            System.err.println(exception.getMessage());
            exception.printStackTrace();
        }
    }
}
