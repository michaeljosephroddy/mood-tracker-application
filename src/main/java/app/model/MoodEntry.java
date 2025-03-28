package app.model;

import java.util.ArrayList;
import java.util.UUID;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.GsonBuilder;
import app.util.MoodDeserializer;

public class MoodEntry {

    private String userId;
    private String moodEntryId;
    private ArrayList<Mood> moods;
    private String date;
    private String description;

    // deafult constructor
    public MoodEntry() {
        this.moodEntryId = UUID.randomUUID().toString();

    }

    // overloaded constructor
    public MoodEntry(String userId, ArrayList<Mood> moods, String date, String description) {
        this(); // call default constructor first
        this.userId = userId;
        this.moods = moods;
        this.date = date;
        this.description = description;
    }

    public static String convertToJSON(MoodEntry moodEntry) {
        Gson gson = new Gson();
        String json = gson.toJson(moodEntry);
        return json;
    }

    public String serializeMoods() {
        Gson gson = new Gson();
        return gson.toJson(this.moods);
    }

    public void deserializeMoods(String json) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Mood.class, new MoodDeserializer()) // Register the custom deserializer
                .create();

        Type listType = new TypeToken<ArrayList<Mood>>() {
        }.getType();
        this.moods = gson.fromJson(json, listType);
    }

    public String getMoodEntryId() {
        return moodEntryId;
    }

    public ArrayList<Mood> getMoods() {
        return moods;
    }

    public void setMoods(ArrayList<Mood> moods) {
        this.moods = moods;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setMoodEntryId(String moodEntryId) {
        this.moodEntryId = moodEntryId;
    }

    @Override
    public String toString() {
        return "MoodEntry{" +
                "moodEntryId=" + moodEntryId +
                ", userId=" + userId +
                ", moods=" + moods +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}