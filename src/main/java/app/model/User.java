package app.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public final class User extends Person {

    private final String userId;
    private ArrayList<MoodEntry> moodHistory;
    private String createdOn;

    // Constructor
    public User(String username, String email, String userId) {
        super(username, email); // Calls the constructor of the Person class
        this.userId = userId;
        this.moodHistory = new ArrayList<>();
        this.createdOn = LocalDateTime.now().toString(); // Sets account creation date to now
    }

    public String getUserId() {
        return this.userId;
    }

    public ArrayList<MoodEntry> getMoodHistory() {
        return new ArrayList<>(moodHistory); // Defensive copy for encapsulation
    }

    public void setMoodHistory(ArrayList<MoodEntry> moodHistory) {
        this.moodHistory = new ArrayList<>(moodHistory);
    }

    public String getAccountCreatedOn() {
        return this.createdOn;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + getName() + '\'' + // Accessing username from Person class
                ", email=" + getEmail() + // Accessing age from Person class
                ", moodHistory=" + moodHistory +
                ", createdOn='" + createdOn + '\'' +
                '}';
    }

}
