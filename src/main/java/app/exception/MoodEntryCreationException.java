package app.exception;

public class MoodEntryCreationException extends RuntimeException {
    public MoodEntryCreationException(String message) {
        super(message);
    }

    public MoodEntryCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}