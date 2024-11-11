package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public final class User extends Person {

    private final String userId;
    private ArrayList<MoodEntry> moodHistory;
    private String createdOn;

    // Constructor
    public User(String name, int age, String dob) {
        super(name, age, dob); // Calls the constructor of the Person class
        this.userId = UUID.randomUUID().toString();

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
                ", name='" + getName() + '\'' + // Accessing name from Person class
                ", age=" + getAge() + // Accessing age from Person class
                ", dob='" + getDob() + '\'' + // Accessing dob from Person class
                ", moodHistory=" + moodHistory +
                ", createdOn='" + createdOn + '\'' +
                '}';
    }

}
