package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public final class User extends Person {

    private String userName;
    private ArrayList<MoodEntry> moodHistory;
    private String createdOn;
    
    // Constructor
    public User(String name, int age, String dob, String userName) {
        super(name, age, dob); // Calls the constructor of the Person class
        this.userName = userName;
        this.moodHistory = new ArrayList<>();
        this.createdOn = LocalDateTime.now().toString(); // Sets account creation date to now
    }

    public String getUsername() {
        return this.userName;
    }

    public void setUsername(String username) {
        this.userName = username;
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

}
