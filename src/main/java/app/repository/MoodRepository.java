package app.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import app.database.MySQLDatabaseConnector;
import app.model.MoodEntry;
import app.model.User;

public class MoodRepository {
    private final MySQLDatabaseConnector databaseConnector;

    public MoodRepository(MySQLDatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
        this.databaseConnector.connect(); // Establish the connection
    }

    public void saveMoodEntry(MoodEntry moodEntry) {
        String sql = String.format(
                "INSERT INTO mood_entries (mood_entry_id, user_id, moods, date, description) VALUES ('%s', '%s', '%s', '%s', '%s')",
                moodEntry.getMoodEntryId(),
                moodEntry.getUserId(),
                moodEntry.serializeMoods(),
                moodEntry.getDate(),
                moodEntry.getDescription());

        databaseConnector.executeSQLUpdate(sql);
    }

    public ArrayList<MoodEntry> findMoodEntriesByUserId(String userId) {
        String sql = String.format("SELECT * FROM mood_entries WHERE user_id = '%s'", userId);

        ResultSet resultSet = databaseConnector.executeSQLRead(sql);
        ArrayList<MoodEntry> entries = new ArrayList<>();

        try {
            if (!resultSet.isBeforeFirst()) { // Check if ResultSet is empty
                System.out.println("No mood entries found for user_id: " + userId);
                return entries;
            }

            while (resultSet.next()) {
                entries.add(mapResultSetToMoodEntry(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error mapping ResultSet to MoodEntry: " + e.getMessage());
            throw new RuntimeException("Error mapping ResultSet to MoodEntry", e);
        }

        return entries;
    }

    public Optional<MoodEntry> findMoodEntryById(Long id) {
        String sql = String.format("SELECT * FROM mood_entries WHERE mood_entry_id = '%d'", id);
        ResultSet resultSet = databaseConnector.executeSQLRead(sql);
        System.out.println("Done");
        try {
            if (resultSet.next()) {
                return Optional.of(mapResultSetToMoodEntry(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error mapping ResultSet to MoodEntry: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    public boolean updateMoodEntry(Long id, MoodEntry moodEntry) {
        String sql = String.format(
                "UPDATE mood_entries SET moods = '%s', date = '%s', description = '%s' WHERE mood_entry_id = '%d'",
                moodEntry.serializeMoods(),
                moodEntry.getDate(),
                moodEntry.getDescription(),
                id);

        return databaseConnector.executeSQLUpdate(sql) > 0;
    }

    public boolean deleteMoodEntry(Long id) {
        String sql = String.format("DELETE FROM mood_entries WHERE mood_entry_id = '%d'", id);
        return databaseConnector.executeSQLUpdate(sql) > 0;
    }

    public User findUserByEmailAndPassword(String email, String password) {
        // Use the DBConnector's executeSQLRead method to query the database
        String sql = String.format("SELECT * FROM users WHERE email = '%s'", email);
        ResultSet resultSet = databaseConnector.executeSQLRead(sql);

        try {
            if (resultSet.next()) {
                String userId = resultSet.getString("user_id");
                String username = resultSet.getString("username");
                return new User(username, email, userId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving user from database: " + e.getMessage(), e);
        }

        return null;
    }

    public void disconnectDatabase() {
        databaseConnector.disconnect();
    }

    private MoodEntry mapResultSetToMoodEntry(ResultSet resultSet) throws SQLException {
        String moodEntryId = resultSet.getString("mood_entry_id");
        String userId = resultSet.getString("user_id");
        String moods = resultSet.getString("moods"); // JSON string of moods
        String date = resultSet.getString("date");
        String description = resultSet.getString("description");

        // Create a new MoodEntry object
        MoodEntry moodEntry = new MoodEntry();
        moodEntry.setMoodEntryId(moodEntryId);
        moodEntry.setUserId(userId);
        moodEntry.setDate(date);
        moodEntry.setDescription(description);

        // Deserialize the moods JSON string into a list of Mood objects
        try {
            moodEntry.deserializeMoods(moods);
        } catch (Exception e) {
            System.err.println("Error deserializing moods JSON: " + e.getMessage());
            throw new RuntimeException("Error deserializing moods JSON", e);
        }

        return moodEntry;
    }
}