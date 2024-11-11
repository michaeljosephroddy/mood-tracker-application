package model;

public class Mood {

    private MoodType emotion;
    private int intensity;

    public Mood() {
    }

    public Mood(MoodType emotion, int intensity) {
        this.emotion = emotion;
        this.intensity = intensity;
    }

    public MoodType getMood() {
        return this.emotion;
    }

    public void setMood(MoodType emotion) {
        this.emotion = emotion;
    }

    public int getIntensity() {
        return this.intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

}
