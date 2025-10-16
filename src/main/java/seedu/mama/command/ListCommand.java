package seedu.mama.command;

import seedu.mama.model.Entry;
import seedu.mama.model.EntryList;
import seedu.mama.storage.Storage;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Lists all entries to the user.
 */
public class ListCommand implements Command {

    // 1. Set up a logger for this class
    private static final Logger LOGGER = Logger.getLogger(ListCommand.class.getName());

    @Override
    public String execute(EntryList entries, Storage storage) throws CommandException {
        // Log the start of the execution
        LOGGER.log(Level.INFO, "Executing ListCommand.");

        // 2. Use an assertion to check for internal errors.
        // This confirms our assumption that 'entries' should never be null.
        assert entries != null : "The EntryList object passed to ListCommand should not be null.";

        // 3. Use an exception for recoverable errors.
        // If 'entries' is null despite the assertion (e.g., if assertions are disabled),
        // we throw an exception to be handled gracefully by the calling code.
        if (entries == null) {
            LOGGER.log(Level.SEVERE, "EntryList object is null.");
            throw new CommandException("An internal error occurred: EntryList is missing.");
        }

        if (entries.size() == 0) {
            LOGGER.log(Level.INFO, "No entries found to list.");
            return "No entries found.";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < entries.size(); i++) {
            Entry e = entries.get(i);
            sb.append(i + 1) // 1-based numbering
                    .append(". ")
                    .append(e.toListLine())
                    .append(System.lineSeparator());
        }

        LOGGER.log(Level.INFO, "ListCommand successfully executed, listing " + entries.size() + " entries.");
        return sb.toString().trim();
    }
}
