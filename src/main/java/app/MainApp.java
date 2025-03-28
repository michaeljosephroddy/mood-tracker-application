package app;

import app.client.MoodTrackerClient;
import app.controller.MoodController;
import app.database.MySQLDatabaseConnector;
import app.repository.MoodRepository;
import app.service.MoodService;

public class MainApp {
    public static void main(String[] args) {
        MySQLDatabaseConnector databaseConnector = new MySQLDatabaseConnector();
        MoodRepository moodRepository = new MoodRepository(databaseConnector);
        MoodService moodService = new MoodService(moodRepository);
        MoodController moodController = new MoodController(moodService);

        MoodTrackerClient client = new MoodTrackerClient(moodController);
        client.start();
    }
}