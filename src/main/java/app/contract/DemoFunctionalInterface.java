package app.contract;

import java.util.ArrayList;

import app.model.MoodEntry;
import app.model.User;

@FunctionalInterface
public interface DemoFunctionalInterface {
    public ArrayList<MoodEntry> doStuff(User user);
}
