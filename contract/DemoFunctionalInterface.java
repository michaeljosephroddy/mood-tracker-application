package contract;

import java.util.ArrayList;

import model.MoodEntry;
import model.User;

@FunctionalInterface
public interface DemoFunctionalInterface {
    public ArrayList<MoodEntry> doStuff(User user);
}
