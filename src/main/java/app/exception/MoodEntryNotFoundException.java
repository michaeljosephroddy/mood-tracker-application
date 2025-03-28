package app.exception;

public class MoodEntryNotFoundException extends RuntimeException {
    public MoodEntryNotFoundException(String message) {
        super(message);
    }

    public MoodEntryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}