package app.service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import app.exception.*;
import app.model.MoodEntry;
import app.model.User;
import app.repository.MoodRepository;

public class MoodService {
    private final MoodRepository moodRepository;

    public MoodService(MoodRepository moodRepository) {
        this.moodRepository = moodRepository;
    }

    /**
     * Adds a new mood entry for a user.
     *
     * @param moodEntry The mood entry to be added.
     * @return The added mood entry.
     */
    public MoodEntry addMoodEntry(MoodEntry moodEntry) {
        try {
            moodRepository.saveMoodEntry(moodEntry);
            return moodEntry;
        } catch (Exception e) {
            throw new MoodEntryCreationException("Error creating mood entry for user: " + moodEntry.getUserId(), e);
        }
    }

    /**
     * Retrieves all mood entries for a user.
     *
     * @param user The user whose mood entries are to be retrieved.
     * @return A list of all mood entries for the user.
     */
    public ArrayList<MoodEntry> getAllMoodEntries(User user) {
        try {
            return moodRepository.findMoodEntriesByUserId(user.getUserId());
        } catch (Exception e) {
            throw new DatabaseReadException("Error retrieving mood entries for user: " + user.getUserId(), e);
        }
    }

    /**
     * Retrieves a mood entry by its ID.
     *
     * @param id The ID of the mood entry.
     * @return The mood entry with the specified ID.
     */
    public MoodEntry getMoodEntryById(Long id) {
        try {
            return moodRepository.findMoodEntryById(id)
                    .orElseThrow(() -> new MoodEntryNotFoundException("Mood entry with ID " + id + " not found."));
        } catch (Exception e) {
            throw new DatabaseReadException("Error retrieving mood entry with ID: " + id, e);
        }
    }

    /**
     * Updates an existing mood entry.
     *
     * @param id        The ID of the mood entry to be updated.
     * @param moodEntry The updated mood entry.
     * @return The updated mood entry.
     */
    public MoodEntry editMoodEntry(Long id, MoodEntry moodEntry) {
        try {
            boolean updated = moodRepository.updateMoodEntry(id, moodEntry);
            if (!updated) {
                throw new MoodEntryUpdateException("Failed to update mood entry with ID: " + id);
            }
            return moodEntry;
        } catch (Exception e) {
            throw new MoodEntryUpdateException("Error updating mood entry with ID: " + id, e);
        }
    }

    /**
     * Deletes a mood entry by its ID.
     *
     * @param id The ID of the mood entry to be deleted.
     */
    public void removeMoodEntry(Long id) {
        try {
            boolean deleted = moodRepository.deleteMoodEntry(id);
            if (!deleted) {
                throw new MoodEntryDeletionException("Failed to delete mood entry with ID: " + id);
            }
        } catch (Exception e) {
            throw new MoodEntryDeletionException("Error deleting mood entry with ID: " + id, e);
        }
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
        ArrayList<MoodEntry> results = getAllMoodEntries(user);

        return new ArrayList<>(results.stream()
                .filter(moodEntry -> (option == 1)
                        ? LocalDateTime.parse(moodEntry.getDate()).isAfter(dateToFilterOn)
                        : LocalDateTime.parse(moodEntry.getDate()).isBefore(dateToFilterOn))
                .collect(Collectors.toList()));
    }

    /**
     * Retrieves a user by their email and password.
     *
     * @param email    The user's email.
     * @param password The user's password.
     * @return The user with the specified email and password.
     */
    public User getUserByEmailAndPassword(String email, String password) {
        return moodRepository.findUserByEmailAndPassword(email, password);
    }

    /**
     * Disconnects the database connection.
     */
    public void disconnectDatabase() {
        moodRepository.disconnectDatabase();
    }

    /**
     * Groups mood entries by their date.
     *
     * @param user The user whose mood entries are to be grouped.
     * @return A map where the key is the date and the value is a list of mood
     *         entries for that date.
     */
    public Map<String, List<MoodEntry>> groupMoodEntriesByDate(User user) {
        ArrayList<MoodEntry> moodEntries = getAllMoodEntries(user);
        return moodEntries.stream()
                .collect(Collectors.groupingBy(MoodEntry::getDate));
    }

    /**
     * Counts the total number of mood entries for a user.
     *
     * @param user The user whose mood entries are to be counted.
     * @return The total number of mood entries.
     */
    public long countMoodEntries(User user) {
        ArrayList<MoodEntry> moodEntries = getAllMoodEntries(user);
        return moodEntries.stream().count();
    }

    /**
     * Finds the mood entry with the highest intensity for a user.
     *
     * @param user The user whose mood entries are to be searched.
     * @return An optional containing the mood entry with the highest intensity, or
     *         empty if no entries exist.
     */
    public Optional<MoodEntry> findMoodEntryWithHighestIntensity(User user) {
        ArrayList<MoodEntry> moodEntries = getAllMoodEntries(user);
        return moodEntries.stream()
                .max(Comparator.comparingInt(moodEntry -> moodEntry.getMoods().stream()
                        .mapToInt(mood -> mood.intensity())
                        .max()
                        .orElse(0)));
    }

    /**
     * Filters mood entries based on a given condition.
     *
     * @param user      The user whose mood entries are to be filtered.
     * @param condition The condition to filter mood entries.
     * @return A list of mood entries that match the condition.
     */
    public List<MoodEntry> filterMoodEntries(User user, Predicate<MoodEntry> condition) {
        ArrayList<MoodEntry> moodEntries = getAllMoodEntries(user);
        return moodEntries.stream()
                .filter(condition)
                .collect(Collectors.toList());
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
        ArrayList<MoodEntry> moodEntries = getAllMoodEntries(user);
        return moodEntries.stream()
                .collect(Collectors.partitioningBy(moodEntry -> moodEntry.getMoods().stream()
                        .anyMatch(mood -> mood.intensity() > threshold)));
    }

    /**
     * Maps mood entry IDs to their descriptions.
     *
     * @param user The user whose mood entries are to be mapped.
     * @return A map where the key is the mood entry ID and the value is the
     *         description.
     */
    public Map<String, String> mapMoodEntriesToDescriptions(User user) {
        ArrayList<MoodEntry> moodEntries = getAllMoodEntries(user);
        return moodEntries.stream()
                .collect(Collectors.toMap(MoodEntry::getMoodEntryId, MoodEntry::getDescription));
    }

    /**
     * Sorts mood entries by their date.
     *
     * @param user The user whose mood entries are to be sorted.
     * @return A list of mood entries sorted by date.
     */
    public List<MoodEntry> sortMoodEntriesByDate(User user) {
        ArrayList<MoodEntry> moodEntries = getAllMoodEntries(user);
        return moodEntries.stream()
                .sorted(Comparator.comparing(MoodEntry::getDate))
                .collect(Collectors.toList());
    }

    /**
     * Checks if all mood entries match a given condition.
     *
     * @param user      The user whose mood entries are to be checked.
     * @param condition The condition to check.
     * @return True if all mood entries match the condition, false otherwise.
     */
    public boolean allMoodEntriesMatch(User user, Predicate<MoodEntry> condition) {
        ArrayList<MoodEntry> moodEntries = getAllMoodEntries(user);
        return moodEntries.stream().allMatch(condition);
    }

    /**
     * Finds any mood entry for a user.
     *
     * @param user The user whose mood entries are to be searched.
     * @return An optional containing any mood entry, or empty if no entries exist.
     */
    public Optional<MoodEntry> findAnyMoodEntry(User user) {
        ArrayList<MoodEntry> moodEntries = getAllMoodEntries(user);
        return moodEntries.stream().findAny();
    }

}