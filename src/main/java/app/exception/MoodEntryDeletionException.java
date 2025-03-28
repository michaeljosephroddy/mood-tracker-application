package app.exception;

public class MoodEntryDeletionException extends RuntimeException {
    public MoodEntryDeletionException(String message) {
        super(message);
    }

    public MoodEntryDeletionException(String message, Throwable cause) {
        super(message, cause);
    }
}