package app;

import view.ConsoleView;
import model.User;

public class MainApp {
    public static void main(String[] args) {
        // simulate a logged in user
        User user = new User("Michael Roddy", 27, "01/05/1997");
        ConsoleView consoleView = new ConsoleView(user);
        consoleView.start();

    }
}