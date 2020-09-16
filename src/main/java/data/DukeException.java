package data;

/**
 * This exception is thrown for most errors that are encountered by Duke
 */
public class DukeException extends Exception {
    public DukeException(String message) {
        super(message);
    }
}

