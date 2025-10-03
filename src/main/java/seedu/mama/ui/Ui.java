package seedu.mama.ui;

/**
 * Handles all user interface interactions, including displaying messages
 *
 */
public class Ui {
    private final String line;

    public Ui() {
        this.line = "____________________________________________________________";
    }

    private void printLine() {
        System.out.println(line);
    }

    public void showMessage(String s) {
        System.out.println(s);
    }

}
