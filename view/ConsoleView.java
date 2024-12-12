
package view;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import controller.MoodController;
import database.H2DatabaseConnector;
import model.Mood;
import model.MoodEntry;
import model.MoodType;
import model.User;

public class ConsoleView {
    private final Scanner scanner;
    private final MoodController moodController;
    private final H2DatabaseConnector databaseConnector;
    private User user;

    public ConsoleView(User user) {
        this.user = user;
        this.moodController = new MoodController();
        this.scanner = new Scanner(System.in);
        this.databaseConnector = new H2DatabaseConnector();
    }

    public void start() {
        // Main loop to interact with the user
        boolean running = true;
        while (running) {
            System.out.println("1. Add Mood Entry\n2. View Mood History\n3. Filter Entries By Date\n4. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    var moodEntry = addMoodEntry();
                    System.out.println(MoodEntry.convertToJSON(moodEntry));
                    start();
                case 2:
                    var history = getMoodHistory();
                    printAsJSONArray(history);
                    start();
                case 3:
                    var filteredHistory = filterMoodHistory();
                    printAsJSONArray(filteredHistory);
                    start();
                case 4:
                    databaseConnector.disconnect();
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }

        System.exit(0);
    }

    public String filterMoodHistory() {

        System.out.println("Enter Date (format yyyy-mm-ddT00:00:00): ");
        String date = scanner.nextLine();
        LocalDateTime dateToFilterOn = LocalDateTime.parse(date);

        System.out.println("1. Filter Entries > " + date + "\n2. Filter Entries < " + date);
        int option = scanner.nextInt();
        scanner.nextLine();

        if (option != 1 && option != 2) {
            System.out.println("Invalid option try again");
            filterMoodHistory();
        }

        ArrayList<MoodEntry> filteredList = moodController.filterMoodEntries(user, dateToFilterOn, option);

        if (filteredList.size() == 0) {
            return convertToJSONString(new ArrayList<>());
        }

        return convertToJSONString(filteredList);
    }

    public MoodEntry addMoodEntry() {
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

        MoodEntry entry = moodController.createMoodEntry(moodEntry);
        return entry;
    }

    public String getMoodHistory() {
        ArrayList<MoodEntry> moodEntries = moodController.readMoodEntries(user);

        if (moodEntries.size() == 0) {
            return convertToJSONString(new ArrayList<>());
        }

        return convertToJSONString(moodEntries);

    }

    public static String convertToJSONString(ArrayList<MoodEntry> data) {

        if (data.size() == 0) {
            return "[]";
        }

        if (data.size() == 1) {
            return "[" + MoodEntry.convertToJSON(data.get(0)) + "]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");

        int index = 0;
        for (MoodEntry moodEntry : data) {

            if (index == data.size() - 1) {
                sb.append(MoodEntry.convertToJSON(moodEntry));
                sb.append("]");
                break;
            }

            sb.append(MoodEntry.convertToJSON(moodEntry) + ",");
            index++;
        }

        return sb.toString();
    }

    public static void printAsJSONArray(String data) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray jsonArray = JsonParser.parseString(data).getAsJsonArray();
        System.out.println(gson.toJson(jsonArray));
    }
}