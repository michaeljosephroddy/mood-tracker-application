package model;

// custom immutable type
// data members are implicitly final
public record Mood(MoodType emotion, int intensity) {
}
