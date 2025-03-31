package app.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import app.database.MySQLDatabaseConnector;
import app.model.MoodEntry;
import app.model.User;

/**
 * Repository class for managing mood entries and user data in the database.
 * Provides methods for CRUD operations on mood entries and user retrieval.
 */
public class MoodRepository {
    private final MySQLDatabaseConnector databaseConnector;

    /**
     * Constructs a MoodRepository with the specified database connector.
     * Establishes a connection to the database.
     *
     * @param databaseConnector the database connector to use
     */
    public MoodRepository(MySQLDatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
        this.databaseConnector.connect(); // Establish the connection
    }

    /**
     * Saves a mood entry to the database.
     *
     * @param moodEntry the mood entry to save
     */
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

    /**
     * Retrieves all mood entries for a specific user from the database.
     *
     * @param userId the ID of the user whose mood entries are to be retrieved
     * @return a list of mood entries for the specified user
     */
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

    /**
     * Retrieves a mood entry by its ID.
     *
     * @param id the ID of the mood entry to retrieve
     * @return an Optional containing the mood entry if found, or an empty Optional
     *         if not found
     */
    public Optional<MoodEntry> findMoodEntryById(Long id) {
        String sql = String.format("SELECT * FROM mood_entries WHERE mood_entry_id = '%d'", id);
        ResultSet resultSet = databaseConnector.executeSQLRead(sql);

        try {
            if (resultSet.next()) {
                return Optional.of(mapResultSetToMoodEntry(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error mapping ResultSet to MoodEntry: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    /**
     * Updates an existing mood entry in the database.
     *
     * @param id        the ID of the mood entry to update
     * @param moodEntry the updated mood entry data
     * @return true if the update was successful, false otherwise
     */
    public boolean updateMoodEntry(Long id, MoodEntry moodEntry) {
        String sql = String.format(
                "UPDATE mood_entries SET moods = '%s', date = '%s', description = '%s' WHERE mood_entry_id = '%d'",
                moodEntry.serializeMoods(),
                moodEntry.getDate(),
                moodEntry.getDescription(),
                id);

        return databaseConnector.executeSQLUpdate(sql) > 0;
    }

    /**
     * Deletes a mood entry from the database.
     *
     * @param id the ID of the mood entry to delete
     * @return true if the deletion was successful, false otherwise
     */
    public boolean deleteMoodEntry(Long id) {
        String sql = String.format("DELETE FROM mood_entries WHERE mood_entry_id = '%d'", id);
        return databaseConnector.executeSQLUpdate(sql) > 0;
    }

    /**
     * Retrieves a user from the database by their email and password.
     *
     * @param email    the email of the user
     * @param password the password of the user
     * @return the User object if found, or null if no matching user is found
     */
    public User findUserByEmailAndPassword(String email, String password) {
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

    /**
     * Disconnects the database connection.
     */
    public void disconnectDatabase() {
        databaseConnector.disconnect();
    }

    /**
     * Maps a ResultSet row to a MoodEntry object.
     *
     * @param resultSet the ResultSet containing the mood entry data
     * @return the mapped MoodEntry object
     * @throws SQLException if an error occurs while accessing the ResultSet
     */
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