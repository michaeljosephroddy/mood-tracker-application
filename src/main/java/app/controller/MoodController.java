package app.controller;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;

import app.model.MoodEntry;
import app.model.User;
import app.service.MoodService;

public class MoodController {

    private final MoodService moodService;

    public MoodController(MoodService moodService) {
        this.moodService = moodService;
    }

    public MoodEntry addMoodEntry(MoodEntry moodEntry) {
        return moodService.addMoodEntry(moodEntry);
    }

    public ArrayList<MoodEntry> getAllMoodEntries(User user) {
        return moodService.getAllMoodEntries(user);
    }

    public MoodEntry getMoodEntryById(Long id) {
        return moodService.getMoodEntryById(id);
    }

    public MoodEntry editMoodEntry(Long id, MoodEntry moodEntry) {
        return moodService.editMoodEntry(id, moodEntry);
    }

    public void removeMoodEntry(Long id) {
        moodService.removeMoodEntry(id);
    }

    public ArrayList<MoodEntry> getFilteredMoodEntries(User user, LocalDateTime dateToFilterOn, int option) {
        return moodService.getFilteredMoodEntries(user, dateToFilterOn, option);
    }

    public User getUserByEmailAndPassword(String email, String password) {
        return moodService.getUserByEmailAndPassword(email, password);
    }

    public void disconnectDatabase() {
        moodService.disconnectDatabase();
        shutdownMySQLCleanupThread();
    }

    private void shutdownMySQLCleanupThread() {
        try {
            com.mysql.cj.jdbc.AbandonedConnectionCleanupThread.checkedShutdown();
            System.out.println("MySQL cleanup thread shut down successfully.");
        } catch (Exception e) {
            System.err.println("Error shutting down MySQL cleanup thread: " + e.getMessage());
        }
    }

    // New Methods

    public Map<String, List<MoodEntry>> groupMoodEntriesByDate(User user) {
        return moodService.groupMoodEntriesByDate(user);
    }

    public long countMoodEntries(User user) {
        return moodService.countMoodEntries(user);
    }

    public Optional<MoodEntry> findMoodEntryWithHighestIntensity(User user) {
        return moodService.findMoodEntryWithHighestIntensity(user);
    }

    public List<MoodEntry> filterMoodEntries(User user, Predicate<MoodEntry> condition) {
        return moodService.filterMoodEntries(user, condition);
    }

    public Map<Boolean, List<MoodEntry>> partitionMoodEntriesByIntensity(User user, int threshold) {
        return moodService.partitionMoodEntriesByIntensity(user, threshold);
    }

    public Map<String, String> mapMoodEntriesToDescriptions(User user) {
        return moodService.mapMoodEntriesToDescriptions(user);
    }

    public List<MoodEntry> sortMoodEntriesByDate(User user) {
        return moodService.sortMoodEntriesByDate(user);
    }

    public boolean allMoodEntriesMatch(User user, Predicate<MoodEntry> condition) {
        return moodService.allMoodEntriesMatch(user, condition);
    }

    public Optional<MoodEntry> findAnyMoodEntry(User user) {
        return moodService.findAnyMoodEntry(user);
    }

}