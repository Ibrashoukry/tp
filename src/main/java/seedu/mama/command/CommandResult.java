package seedu.mama.command;

import java.util.Objects;

/**
 * Represents the result of executing a {@link Command}.
 * <p>
 * Encapsulates feedback that should be displayed to the user, as well as
 * control flags (e.g., whether the program should exit).
 * <p>
 * This class is immutable.
 */
public final class CommandResult {

    /**
     * Message to be shown to the user.
     */
    private final String feedbackToUser;

    /**
     * Flag indicating whether the application should terminate.
     */
    private final boolean isExit;

    /**
     * Constructs a {@code CommandResult} with the specified feedback message
     * and a default {@code isExit = false}.
     *
     * @param feedbackToUser Feedback message to show to the user.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified feedback message
     * and exit flag.
     *
     * @param feedbackToUser Feedback message to show to the user.
     * @param isExit         {@code true} if the program should terminate after this command,
     *                       {@code false} otherwise.
     */
    public CommandResult(String feedbackToUser, boolean isExit) {
        this.feedbackToUser = Objects.requireNonNull(feedbackToUser, "feedbackToUser");
        this.isExit = isExit;
    }

    /**
     * Returns the feedback message intended for the user.
     *
     * @return Feedback message as a {@code String}.
     */
    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    /**
     * Returns whether the application should exit after executing this command.
     *
     * @return {@code true} if the app should exit, {@code false} otherwise.
     */
    public boolean isExit() {
        return isExit;
    }

    /**
     * Returns a string representation of this {@code CommandResult}
     * for debugging purposes.
     *
     * @return String containing the feedback message and exit flag.
     */
    @Override
    public String toString() {
        return "CommandResult{feedbackToUser='" + feedbackToUser + "', isExit=" + isExit + "}";
    }
}
