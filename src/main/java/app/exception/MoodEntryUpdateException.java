package app.exception;

public class MoodEntryUpdateException extends RuntimeException {
    public MoodEntryUpdateException(String message) {
        super(message);
    }

    public MoodEntryUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}