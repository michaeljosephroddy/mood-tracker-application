package contract;

import java.util.ArrayList;

import model.MoodEntry;

public interface MoodTrackable {
    public MoodEntry createMoodEntry(MoodEntry moodEntry);
    public ArrayList<MoodEntry> readMoodEntries();
    public MoodEntry readMoodEntry(Long id);
    public MoodEntry updateMoodEntry(Long id, MoodEntry moodEntry);
    public MoodEntry deleteMoodEntry(Long id);
    public ArrayList<MoodEntry> filterMoodEntries();
}

