package app.client;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import app.controller.MoodController;
import app.model.Mood;
import app.model.MoodEntry;
import app.model.MoodType;
import app.model.User;

public class MoodTrackerClient {
    private final Scanner scanner;
    private final MoodController moodController;
    private User user;

    public MoodTrackerClient(MoodController moodController) {
        this.moodController = moodController;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome to the Mood Tracker App!");
        signIn();

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getValidatedInteger("Enter your choice: ", 1, 11);

            switch (choice) {
                case 1 -> addMoodEntry();
                case 2 -> viewMoodHistory();
                case 3 -> filterMoodEntries();
                case 4 -> viewMoodStatistics();
                case 5 -> groupMoodEntriesByDate();
                case 6 -> partitionMoodEntriesByIntensity();
                case 7 -> sortMoodEntriesByDate();
                case 8 -> checkAllMoodEntriesMatch();
                case 9 -> findAnyMoodEntry();
                case 10 -> mapMoodEntriesToDescriptions();
                case 11 -> running = confirmExit();
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void signIn() {
        while (true) {
            System.out.println("\nSign In");
            String email = getInput("Enter your email: ");
            String password = getInput("Enter your password: ");

            try {
                User retrievedUser = moodController.getUserByEmailAndPassword(email, password);
                if (retrievedUser != null) {
                    this.user = retrievedUser;
                    System.out.println("Signed in as: " + user.getName() + " (" + user.getEmail() + ")");
                    break;
                } else {
                    System.out.println("Invalid email or password. Please try again.");
                }
            } catch (Exception e) {
                handleError("signing in", e);
            }
        }
    }

    private void displayMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Add Mood Entry");
        System.out.println("2. View Mood History");
        System.out.println("3. Filter Entries By Date");
        System.out.println("4. View Mood Statistics");
        System.out.println("5. Group Mood Entries By Date");
        System.out.println("6. Partition Mood Entries By Intensity");
        System.out.println("7. Sort Mood Entries By Date");
        System.out.println("8. Check if All Mood Entries Match a Condition");
        System.out.println("9. Find Any Mood Entry");
        System.out.println("10. Map Mood Entries to Descriptions");
        System.out.println("11. Exit");
    }

    private void addMoodEntry() {
        ArrayList<Mood> moods = new ArrayList<>();

        while (true) {
            String emotion = getInput("Enter mood (e.g., HAPPY, SAD, ANGRY): ").toUpperCase();
            int intensity = getValidatedInteger("Enter intensity (1-10): ", 1, 10);

            try {
                MoodType moodType = MoodType.valueOf(emotion);
                moods.add(new Mood(moodType, intensity));
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid mood type. Please try again.");
                continue;
            }

            if (!getInput("Add another mood? (y/n): ").equalsIgnoreCase("y")) {
                break;
            }
        }

        String description = getInput("Enter a description for your mood entry: ");
        MoodEntry moodEntry = new MoodEntry(user.getUserId(), moods, LocalDateTime.now().toString(), description);

        try {
            MoodEntry createdEntry = moodController.addMoodEntry(moodEntry);
            System.out.println("Mood entry added successfully!");

            // Print the newly created mood entry
            printMoodEntry(createdEntry);
        } catch (Exception e) {
            handleError("adding mood entry", e);
        }
    }

    private void viewMoodHistory() {
        try {
            ArrayList<MoodEntry> moodEntries = moodController.getAllMoodEntries(user);
            if (moodEntries.isEmpty()) {
                System.out.println("No mood entries found.");
            } else {
                printMoodEntries(moodEntries); // Use the list printing method
            }
        } catch (Exception e) {
            handleError("retrieving mood history", e);
        }
    }

    private void filterMoodEntries() {
        int threshold = getValidatedInteger("Enter the intensity threshold to filter by: ", 1, 10);
        String condition = getInput("Filter moods with intensity (greater/less/equal): ").toLowerCase();

        try {
            List<MoodEntry> filteredEntries = moodController.filterMoodEntries(user, moodEntry -> {
                switch (condition) {
                    case "greater":
                        return moodEntry.getMoods().stream().anyMatch(mood -> mood.intensity() > threshold);
                    case "less":
                        return moodEntry.getMoods().stream().anyMatch(mood -> mood.intensity() < threshold);
                    case "equal":
                        return moodEntry.getMoods().stream().anyMatch(mood -> mood.intensity() == threshold);
                    default:
                        System.out.println("Invalid condition. Please use 'greater', 'less', or 'equal'.");
                        return false;
                }
            });

            if (filteredEntries.isEmpty()) {
                System.out.println("No mood entries found with the specified condition.");
            } else {
                System.out.println("Filtered mood entries:");
                filteredEntries.forEach(System.out::println);
            }
        } catch (Exception e) {
            handleError("filtering mood entries", e);
        }
    }

    private boolean confirmExit() {
        String exitChoice = getInput("Are you sure you want to exit? (y/n): ");
        if (exitChoice.equalsIgnoreCase("y")) {
            System.out.println("Goodbye!");
            moodController.disconnectDatabase(); // Ensure the database connection is closed
            return false;
        }
        return true;
    }

    private String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private int getValidatedInteger(String prompt, int min, int max) {
        while (true) {
            try {
                int value = Integer.parseInt(getInput(prompt));
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("Please enter a value between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private LocalDateTime getValidatedDate(String prompt) {
        while (true) {
            try {
                return LocalDateTime.parse(getInput(prompt));
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use yyyy-MM-ddTHH:mm:ss.");
            }
        }
    }

    private String convertToJSONString(Object data) {
        Gson gson = new Gson();
        return gson.toJson(data);
    }

    private void printAsJSONArray(String data) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray jsonArray = JsonParser.parseString(data).getAsJsonArray();
        System.out.println(gson.toJson(jsonArray));
    }

    private void handleError(String action, Exception e) {
        System.err.println("An error occurred while " + action + ": " + e.getMessage());
    }

    private void checkAllMoodEntriesMatch() {
        int threshold = getValidatedInteger("Enter the intensity threshold to check: ", 1, 10);
        try {
            boolean allMatch = moodController.allMoodEntriesMatch(user,
                    moodEntry -> moodEntry.getMoods().stream().allMatch(mood -> mood.intensity() > threshold));
            if (allMatch) {
                System.out.println("All mood entries have an intensity greater than " + threshold + ".");
            } else {
                System.out.println("Not all mood entries have an intensity greater than " + threshold + ".");
            }
        } catch (Exception e) {
            handleError("checking if all mood entries match", e);
        }
    }

    private void findAnyMoodEntry() {
        moodController.findAnyMoodEntry(user)
                .ifPresentOrElse(
                        this::printMoodEntry, // Use the single-object printing method
                        () -> System.out.println("No mood entries found."));
    }

    private void mapMoodEntriesToDescriptions() {
        Map<String, String> descriptions = moodController.mapMoodEntriesToDescriptions(user);
        descriptions.forEach((id, description) -> {
            System.out.println("ID: " + id);
            System.out.println("Description: " + description);
            System.out.println("--------------------------------------------------");
        });
    }

    private void groupMoodEntriesByDate() {
        try {
            Map<String, List<MoodEntry>> groupedEntries = moodController.groupMoodEntriesByDate(user);
            if (groupedEntries.isEmpty()) {
                System.out.println("No mood entries found.");
            } else {
                groupedEntries.forEach((date, entries) -> {
                    System.out.println("Date: " + date);
                    printMoodEntries(entries); // Use the list printing method
                });
            }
        } catch (Exception e) {
            handleError("grouping mood entries by date", e);
        }
    }

    private void partitionMoodEntriesByIntensity() {
        int threshold = getValidatedInteger("Enter intensity threshold: ", 1, 10);
        try {
            Map<Boolean, List<MoodEntry>> partitioned = moodController.partitionMoodEntriesByIntensity(user, threshold);

            System.out.println("High intensity entries:");
            printMoodEntries(partitioned.get(true)); // Use the list printing method

            System.out.println("Low intensity entries:");
            printMoodEntries(partitioned.get(false)); // Use the list printing method
        } catch (Exception e) {
            handleError("partitioning mood entries by intensity", e);
        }
    }

    private void sortMoodEntriesByDate() {
        try {
            List<MoodEntry> sortedEntries = moodController.sortMoodEntriesByDate(user);
            if (sortedEntries.isEmpty()) {
                System.out.println("No mood entries found.");
            } else {
                System.out.println("Mood entries sorted by date:");
                printMoodEntries(sortedEntries); // Use the list printing method
            }
        } catch (Exception e) {
            handleError("sorting mood entries by date", e);
        }
    }

    private void viewMoodStatistics() {
        try {
            // Count the total number of mood entries
            long totalEntries = moodController.countMoodEntries(user);
            System.out.println("Total mood entries: " + totalEntries);

            // Find the mood entry with the highest intensity
            moodController.findMoodEntryWithHighestIntensity(user)
                    .ifPresentOrElse(
                            this::printMoodEntry, // Use the single-object printing method
                            () -> System.out.println("No mood entries found."));

            // Print all mood entries
            System.out.println("All mood entries:");
            List<MoodEntry> allMoodEntries = moodController.getAllMoodEntries(user);
            printMoodEntries(allMoodEntries);
        } catch (Exception e) {
            handleError("viewing mood statistics", e);
        }
    }

    private void printMoodEntries(List<MoodEntry> moodEntries) {
        if (moodEntries.isEmpty()) {
            System.out.println("No mood entries found.");
            return;
        }

        System.out.println("Mood Entries:");
        for (MoodEntry entry : moodEntries) {
            printMoodEntry(entry); // Reuse the single-object printing method
        }
    }

    private void printMoodEntry(MoodEntry entry) {
        System.out.println("--------------------------------------------------");
        System.out.println("Mood Entry ID: " + entry.getMoodEntryId());
        System.out.println("User ID: " + entry.getUserId());
        System.out.println("Date: " + entry.getDate());
        System.out.println("Description: " + entry.getDescription());
        System.out.println("Moods:");
        for (Mood mood : entry.getMoods()) {
            System.out.println("  - Emotion: " + mood.emotion() + ", Intensity: " + mood.intensity());
        }
        System.out.println("--------------------------------------------------");
    }
}