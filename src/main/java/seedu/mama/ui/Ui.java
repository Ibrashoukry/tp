package seedu.mama.ui;

/**
 * Handles all user interface interactions.
 * <p>
 * Responsible for displaying messages, prompts, and visual separators
 * to the user in the console.
 */
public class Ui {

    /**
     * Line separator used to format console output.
     */
    private final String line;

    /**
     * Constructs a {@code Ui} instance with a default line separator.
     */
    public Ui() {
        this.line = "____________________________________________________________";
    }

    /**
     * Prints a horizontal line separator to the console.
     */
    private void printLine() {
        System.out.println(line);
    }

    /**
     * Displays a message to the user.
     */
    public static void showMessage(String s) {
        System.out.println(s);
    }

}
