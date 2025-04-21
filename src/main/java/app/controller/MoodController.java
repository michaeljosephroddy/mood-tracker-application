package app.controller;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;

import app.model.MoodEntry;
import app.model.User;
import app.service.MoodService;

/**
 * Controller class for managing mood-related operations.
 * Acts as an intermediary between the client and the service layer.
 */
public class MoodController {

    private final MoodService moodService;

    /**
     * Constructs a MoodController with the specified MoodService.
     *
     * @param moodService The service layer to handle mood-related operations.
     */
    public MoodController(MoodService moodService) {
        this.moodService = moodService;
    }

    /**
     * Adds a new mood entry.
     *
     * @param moodEntry The mood entry to be added.
     * @return The added mood entry.
     */
    public MoodEntry addMoodEntry(MoodEntry moodEntry) {
        return moodService.addMoodEntry(moodEntry);
    }

    /**
     * Retrieves all mood entries for a user.
     *
     * @param user The user whose mood entries are to be retrieved.
     * @return A list of all mood entries for the user.
     */
    public ArrayList<MoodEntry> getAllMoodEntries(User user) {
        return moodService.getAllMoodEntries(user);
    }

    /**
     * Retrieves a mood entry by its ID.
     *
     * @param id The ID of the mood entry.
     * @return The mood entry with the specified ID.
     */
    public MoodEntry getMoodEntryById(Long id) {
        return moodService.getMoodEntryById(id);
    }

    /**
     * Updates an existing mood entry.
     *
     * @param id        The ID of the mood entry to be updated.
     * @param moodEntry The updated mood entry.
     * @return The updated mood entry.
     */
    public MoodEntry editMoodEntry(Long id, MoodEntry moodEntry) {
        return moodService.editMoodEntry(id, moodEntry);
    }

    /**
     * Deletes a mood entry by its ID.
     *
     * @param id The ID of the mood entry to be deleted.
     */
    public void removeMoodEntry(Long id) {
        moodService.removeMoodEntry(id);
    }

    /**
     * Filters mood entries based on a date and an option (before or after the
     * date).
     *
     * @param user           The user whose mood entries are to be filtered.
     * @param dateToFilterOn The date to filter by.
     * @param option         1 for entries after the date, 2 for entries before the
     *                       date.
     * @return A list of filtered mood entries.
     */
    public ArrayList<MoodEntry> getFilteredMoodEntries(User user, LocalDateTime dateToFilterOn, int option) {
        return moodService.getFilteredMoodEntries(user, dateToFilterOn, option);
    }

    /**
     * Retrieves a user by their email and password.
     *
     * @param email    The email of the user.
     * @param password The password of the user.
     * @return The user with the specified email and password.
     */
    public User getUserByEmailAndPassword(String email, String password) {
        return moodService.getUserByEmailAndPassword(email, password);
    }

    /**
     * Disconnects the database connection and shuts down the MySQL cleanup thread.
     */
    public void disconnectDatabase() {
        moodService.disconnectDatabase();
        shutdownMySQLCleanupThread();
    }

    /**
     * Shuts down the MySQL cleanup thread.
     */
    private void shutdownMySQLCleanupThread() {
        try {
            com.mysql.cj.jdbc.AbandonedConnectionCleanupThread.checkedShutdown();
            System.out.println("MySQL cleanup thread shut down successfully.");
        } catch (Exception e) {
            System.err.println("Error shutting down MySQL cleanup thread: " + e.getMessage());
        }
    }

    /**
     * Groups mood entries by their date.
     *
     * @param user The user whose mood entries are to be grouped.
     * @return A map where the key is the date and the value is a list of mood
     *         entries for that date.
     */
    public Map<String, List<MoodEntry>> groupMoodEntriesByDate(User user) {
        return moodService.groupMoodEntriesByDate(user);
    }

    /**
     * Counts the total number of mood entries for a user.
     *
     * @param user The user whose mood entries are to be counted.
     * @return The total number of mood entries.
     */
    public long countMoodEntries(User user) {
        return moodService.countMoodEntries(user);
    }

    /**
     * Finds the mood entry with the highest intensity for a user.
     *
     * @param user The user whose mood entries are to be searched.
     * @return An optional containing the mood entry with the highest intensity, or
     *         empty if no entries exist.
     */
    public Optional<MoodEntry> findMoodEntryWithHighestIntensity(User user) {
        return moodService.findMoodEntryWithHighestIntensity(user);
    }

    /**
     * Filters mood entries based on a given condition.
     *
     * @param user      The user whose mood entries are to be filtered.
     * @param condition The condition to filter mood entries.
     * @return A list of mood entries that match the condition.
     */
    public List<MoodEntry> filterMoodEntries(User user, Predicate<MoodEntry> condition) {
        return moodService.filterMoodEntries(user, condition);
    }

    /**
     * Partitions mood entries into two groups based on intensity.
     *
     * @param user      The user whose mood entries are to be partitioned.
     * @param threshold The intensity threshold.
     * @return A map where true represents entries with intensity above the
     *         threshold, and false represents entries below.
     */
    public Map<Boolean, List<MoodEntry>> partitionMoodEntriesByIntensity(User user, int threshold) {
        return moodService.partitionMoodEntriesByIntensity(user, threshold);
    }

    /**
     * Maps mood entry IDs to their descriptions.
     *
     * @param user The user whose mood entries are to be mapped.
     * @return A map where the key is the mood entry ID and the value is the
     *         description.
     */
    public Map<String, String> mapMoodEntriesToDescriptions(User user) {
        return moodService.mapMoodEntriesToDescriptions(user);
    }

    /**
     * Sorts mood entries by their date.
     *
     * @param user The user whose mood entries are to be sorted.
     * @return A list of mood entries sorted by date.
     */
    public List<MoodEntry> sortMoodEntriesByDate(User user) {
        return moodService.sortMoodEntriesByDate(user);
    }

    /**
     * Checks if all mood entries match a given condition.
     *
     * @param user      The user whose mood entries are to be checked.
     * @param condition The condition to check.
     * @return True if all mood entries match the condition, false otherwise.
     */
    public boolean allMoodEntriesMatch(User user, Predicate<MoodEntry> condition) {
        return moodService.allMoodEntriesMatch(user, condition);
    }

    /**
     * Finds any mood entry for a user.
     *
     * @param user The user whose mood entries are to be searched.
     * @return An optional containing any mood entry, or empty if no entries exist.
     */
    public Optional<MoodEntry> findAnyMoodEntry(User user) {
        return moodService.findAnyMoodEntry(user);
    }

    /**
     * Retrieves a limited number of unique mood descriptions for a user.
     *
     * @param user  The user whose mood descriptions are to be retrieved.
     * @param limit The maximum number of unique descriptions to retrieve.
     * @return A list of unique mood descriptions, limited to the specified number.
     */
    public List<String> getUniqueMoodDescriptions(User user, int limit) {
        return moodService.getUniqueMoodDescriptions(user, limit);
    }
}