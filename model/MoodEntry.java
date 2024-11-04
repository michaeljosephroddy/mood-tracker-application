package model;

import java.util.ArrayList;

public class MoodEntry {

    private Long id;
    private ArrayList<Mood> moods;
    private String date;
    private String description;

    // deafult constructor
    public MoodEntry() {

    }

    // overloaded constructor
    public MoodEntry(Long id, ArrayList<Mood> moods, String date, String description) {
        this.id = id;
        this. moods = moods;
        this.date = date;
        this.description = description;
    }
   
     public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

}