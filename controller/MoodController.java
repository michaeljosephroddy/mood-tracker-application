package controller;

import java.util.ArrayList;

import model.MoodEntry;
import service.MoodService;

public class MoodController {

    MoodService moodService;

    public MoodController() {
        this.moodService = new MoodService();
    }
    
    public MoodEntry createMoodEnrtry(MoodEntry moodEntry) {
        MoodEntry createdMoodEntry = moodService.createMoodEntry(moodEntry);
        return createdMoodEntry;
    }

    public ArrayList<MoodEntry> readMoodEntries() {
        ArrayList<MoodEntry> moodEntries = moodService.readMoodEntries();
        return new ArrayList<>(moodEntries); //defensive copying
    }

    public MoodEntry updateMoodEntry(Long id, MoodEntry moodEntry) {
        MoodEntry updatedMoodEntry = moodService.updateMoodEntry(id, moodEntry);
        return updatedMoodEntry;
    }

    public MoodEntry deleteMoodEntry(Long id) {
        MoodEntry deleteMoodEntry = moodService.deleteMoodEntry(id);
        return deleteMoodEntry;
    }

}
