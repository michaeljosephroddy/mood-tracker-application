package app.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

// Ensure the correct package path for DBConnector is imported
import app.contract.DBConnector;

public class MySQLDatabaseConnector implements DBConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/mood_tracker_app";
    private static final String USERNAME = "demouser";
    private static final String PASSWORD = "demouserpassword";

    private Connection connection;

    @Override
    public void connect() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connected to the database.");
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    @Override
    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Disconnected from the database.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error closing the database connection", e);
        }
    }

    @Override
    public ResultSet executeSQLRead(String sql) {
        if (!isConnected()) {
            throw new RuntimeException("Database connection is not established.");
        }

        try {
            System.out.println("Executing SQL read operation.");
            return connection.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL read", e);
        }
    }

    @Override
    public int executeSQLUpdate(String sql) {
        try {
            System.out.println("Executing SQL update operation.");
            return connection.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL update", e);
        }
    }

    public Connection getConnection() {
        System.out.println("Returning the database connection.");
        return connection;
    }

    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}