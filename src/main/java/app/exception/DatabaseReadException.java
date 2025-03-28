package app.exception;

/**
 * Exception thrown when an error occurs while reading from the database.
 */
public class DatabaseReadException extends RuntimeException {

    /**
     * Constructs a new DatabaseReadException with the specified detail message.
     *
     * @param message The detail message.
     */
    public DatabaseReadException(String message) {
        super(message);
    }

    /**
     * Constructs a new DatabaseReadException with the specified detail message and
     * cause.
     *
     * @param message The detail message.
     * @param cause   The cause of the exception.
     */
    public DatabaseReadException(String message, Throwable cause) {
        super(message, cause);
    }
}