
package view;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import controller.MoodController;
import model.Mood;
import model.MoodEntry;
import model.MoodType;
import model.User;

public class ConsoleView {
    private final Scanner scanner;
    private final MoodController moodController;
    private User user;

    public ConsoleView(User user) {
        this.user = user;
        this.moodController = new MoodController();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        // Main loop to interact with the user
        boolean running = true;
        while (running) {
            System.out.println("1. Add Mood Entry\n2. View Mood History\n3. Filter Entries on Intensity\n4. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    String moodEntry = addMoodEntry();
                    System.out.println(moodEntry);
                    start();
                case 2:
                    String moodHistory = viewMoodHistory();
                    System.out.println(moodHistory);
                    start();
                case 3:
                    String filteredHistory = filter();
                    System.out.println("These results are filtered");
                    System.out.println(filteredHistory);
                    start();
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }

        System.exit(0);
    }

    public String filter() {

        System.out.println("Enter Date (format yyyy-mm-ddT00:00:00): ");
        String date = scanner.nextLine();
        LocalDateTime dateToFilterOn = LocalDateTime.parse(date);

        System.out.println("1. Filter Entries > " + date + "\n 2. Filter Entries < " + date);
        int option = scanner.nextInt();
        scanner.nextLine();

        if (option != 1 && option != 2) {
            System.out.println("Invalid option try again");
            filter();
        }

        ArrayList<MoodEntry> filteredList = moodController.filterMoodEntries(user, dateToFilterOn, option);
        StringBuilder sb = new StringBuilder();

        for (MoodEntry moodEntry : filteredList) {
            sb.append(moodEntry.toString() + "\n");
        }

        // remove last \n for display purposes
        return sb.toString().substring(0, sb.toString().length() - 1);

    }

    public String addMoodEntry() {
        ArrayList<Mood> moods = new ArrayList<>();

        boolean done = true;
        while (done) {
            // NB do validation
            System.out.println(
                    "Enter mood (allowable values: HAPPY, SAD, ANGRY, EMOTIONAL, IRRITABLE, TIRED, ENERGETIC): ");
            String emotion = scanner.nextLine();

            // NB do validation
            System.out.println("Enter intensity: (1-10)");
            int intensity = scanner.nextInt();
            scanner.nextLine();

            MoodType moodType = MoodType.valueOf(emotion.toUpperCase());

            Mood mood = new Mood(moodType, intensity);
            moods.add(mood);

            // NB do validation
            System.out.println("Enter another mood? (y/n): ");
            String answer = scanner.nextLine();
            if (answer.equals("n") || answer.equals("no")) {
                done = false;
            }

        }

        System.out.println("Enter a description for your mood entry: ");
        String description = scanner.nextLine();

        MoodEntry moodEntry = new MoodEntry();

        moodEntry.setUserId(user.getUserId());
        moodEntry.setMoods(moods);
        moodEntry.setDescription(description);
        moodEntry.setDate(LocalDateTime.now().toString());

        String response = moodController.createMoodEntry(moodEntry);
        return response;
    }

    public String viewMoodHistory() {
        ArrayList<MoodEntry> moodEntries = moodController.readMoodEntries(user);

        if (moodEntries == null) {
            return "No entries";
        }

        StringBuilder sb = new StringBuilder();

        for (MoodEntry moodEntry : moodEntries) {
            sb.append(moodEntry.toString() + "\n");
        }

        // remove last \n for display purposes
        return sb.toString().substring(0, sb.toString().length() - 1);
    }
}