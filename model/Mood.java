package model;

public class Mood {

    private MoodType mood;
    private int intensity;

    public Mood(MoodType mood, int intensity) {
        this.mood = mood;
        this.intensity = intensity;
    }

    public MoodType getMood() {
        return this.mood;
    }

    public void setMood(MoodType mood) {
        this.mood = mood;
    }

    public int getIntensity() {
        return this.intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

}
