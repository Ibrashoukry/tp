package seedu.mama.command;

/**
 * Represents an error that occurs during command execution.
 */
public class CommandException extends Exception {
    public CommandException(String message) {
        super("Error: " + message);
    }

    public CommandException(String message, Throwable cause) {
        super("Error: " + message, cause);
    }
}
