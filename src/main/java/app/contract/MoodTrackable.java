package app.contract;

import java.time.LocalDateTime;
import java.util.ArrayList;

import app.model.MoodEntry;
import app.model.User;

public interface MoodTrackable {
    public MoodEntry createMoodEntry(MoodEntry moodEntry);

    public ArrayList<MoodEntry> readMoodEntries(User user);

    public MoodEntry readMoodEntry(Long id);

    public MoodEntry updateMoodEntry(Long id, MoodEntry moodEntry);

    public MoodEntry deleteMoodEntry(Long id);

    public ArrayList<MoodEntry> filterMoodEntries(User user, LocalDateTime dateToFilterOn, int option);
}
