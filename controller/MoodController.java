package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.MoodEntry;
import model.User;
import contract.MoodTrackable;
import database.H2DatabaseConnector;

public class MoodController implements MoodTrackable {

    private final H2DatabaseConnector h2DatabaseConnector;

    public MoodController() {
        this.h2DatabaseConnector = new H2DatabaseConnector();
        H2DatabaseConnector.connect();
        H2DatabaseConnector.createStatement();
        H2DatabaseConnector.createMoodEntriesTable();
    }

    @Override
    public String createMoodEntry(MoodEntry moodEntry) {
        // TODO get sql statement working and executing
        // TODO do validation and implement a checked exception
        String sql = "insert into moodentries (moodentryid, userid, moods, date, description) values ('%s', '%s', '%s', '%s', '%s')";
        String fSql = String.format(sql, moodEntry.getMoodEntryId(), moodEntry.getUserId(), moodEntry.serializeMoods(),
                moodEntry.getDate(), moodEntry.getDescription());
        int rowsAffected = h2DatabaseConnector.executeSQLUpdate(fSql);

        String message = "Successfully created " + moodEntry.toString() + " for userId: " + moodEntry.getUserId() + "\n"
                + "Affected " + rowsAffected + " rows";
        return message;

    }

    @Override
    public ArrayList<MoodEntry> readMoodEntries(User user) {
        String sql = "select * from moodentries WHERE userid = '%s'";
        String fSql = String.format(sql, user.getUserId());
        ResultSet resultSet = h2DatabaseConnector.executeSQLRead(fSql);

        ArrayList<MoodEntry> entries = new ArrayList<>();

        try {
            while (resultSet.next()) {
                String moodEntryId = resultSet.getString("moodentryid");
                String userId = resultSet.getString("userid");
                String moods = resultSet.getString("moods");
                String date = resultSet.getString("date");
                String description = resultSet.getString("description");

                MoodEntry moodEntry = new MoodEntry();

                // process moods back to ArrayList
                moodEntry.deserializeMoods(moods);

                moodEntry.setMoodEntryId(String.valueOf(moodEntryId));
                moodEntry.setUserId(String.valueOf(userId));
                moodEntry.setDate(date);
                moodEntry.setDescription(description);

                entries.add(moodEntry);

            }

            return new ArrayList<>(entries); // defensive copying

        } catch (SQLException e) {
            System.out.println("Error reading ResultSet for Query " + sql);
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    @Override
    public MoodEntry readMoodEntry(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readMoodEntry'");
    }

    @Override
    public MoodEntry updateMoodEntry(Long id, MoodEntry moodEntry) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateMoodEntry'");
    }

    @Override
    public MoodEntry deleteMoodEntry(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteMoodEntry'");
    }

    @Override
    public ArrayList<MoodEntry> filterMoodEntries() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'filterMoodEntries'");
    }

}
