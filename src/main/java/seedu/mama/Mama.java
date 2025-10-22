package seedu.mama;

import seedu.mama.command.Command;
import seedu.mama.command.CommandException;
import seedu.mama.command.CommandResult;
import seedu.mama.model.EntryList;
import seedu.mama.parser.Parser;
import seedu.mama.storage.Storage;
import seedu.mama.ui.Ui;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Mama {
    /**
     * Main entry-point for the MAMA app.
     */
    private static final Logger LOG = Logger.getLogger(Mama.class.getName());

    public static void main(String[] args) {
        System.out.println("Hello from MAMA");
        System.out.println("Enter a command ("
                + "workout <description> /dur <duration>, "
                + "meal <meal description> /cal <calories>, "
                + "weight <weight>, "
                + "milk <milk volume>, "
                + "measure waist/<cm> hips/<cm> [chest/<cm>] [thigh/<cm>] [arm/<cm>], "
                + "delete <index>, list, bye)");

        Storage storage = Storage.defaultStorage();
        EntryList list = storage.loadOrEmpty();

        Scanner sc = new Scanner(System.in);

        while (true) {
            // ✅ Prevent NoSuchElementException if input file ends (important for runtest.sh)
            if (!sc.hasNextLine()) {
                break;
            }

            String userInput = sc.nextLine().trim();
            if (userInput.isEmpty()) {
                continue; // ignore blank lines
            }

            if (userInput.equalsIgnoreCase("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            }

            try {
                Command command = Parser.parse(userInput);
                CommandResult result = command.execute(list, storage);
                System.out.println(result.getFeedbackToUser());
            } catch (CommandException ce) {
                LOG.log(Level.WARNING, "Command failed: " + ce.getMessage());
                System.out.println(ce.getMessage()); // show user-friendly message
            } catch (Exception e) {
                // safeguard for any other unforeseen error
                System.out.println("An unexpected error occurred: " + e.getMessage());
                LOG.log(Level.SEVERE, "Unexpected exception", e);
            }
        }

        sc.close();
    }
}
