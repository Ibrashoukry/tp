package seedu.mama.command;

import seedu.mama.model.Entry;
import seedu.mama.model.EntryList;
import seedu.mama.storage.Storage;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Lists entries to the user, either all or filtered by a specific criteria.
 */
public class ListCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(ListCommand.class.getName());

    private final Predicate<Entry> predicate;
    private final String displayType;

    /**
     * Constructor for a ListCommand that shows all entries.
     */
    public ListCommand() {
        this.predicate = entry -> true; // Predicate that shows all entries
        this.displayType = "all";
    }

    /**
     * Constructor for a ListCommand that filters entries.
     * @param predicate The condition to filter entries by.
     * @param displayType A string representing the filter type for the success message.
     */
    public ListCommand(Predicate<Entry> predicate, String displayType) {
        // Assert that constructor parameters are not null, as this is a programming error.
        assert predicate != null : "Predicate cannot be null";
        assert displayType != null && !displayType.isEmpty() : "Display type cannot be null or empty";

        this.predicate = predicate;
        this.displayType = displayType;
    }

    @Override
    public String execute(EntryList entries, Storage storage) throws CommandException {
        // Assert that method parameters are not null.
        assert entries != null : "The EntryList object passed to ListCommand should not be null.";
        assert storage != null : "The Storage object passed to ListCommand should not be null.";

        LOGGER.log(Level.INFO, "Executing ListCommand with filter for: " + displayType);

        // Get the list from the EntryList object first, then stream it.
        List<Entry> filteredEntries = entries.asList().stream()
                .filter(predicate)
                .toList();

        if (filteredEntries.isEmpty()) {
            LOGGER.log(Level.INFO, "No entries found for type: " + displayType);
            return "No " + displayType + " entries found.";
        }

        // Build the output string from the filtered list
        StringBuilder sb = new StringBuilder("Here are your " + displayType + " entries:\n");
        for (int i = 0; i < filteredEntries.size(); i++) {
            Entry e = filteredEntries.get(i);
            sb.append(i + 1)
                    .append(". ")
                    .append(e.toListLine())
                    .append(System.lineSeparator());
        }

        LOGGER.log(Level.INFO, "Successfully listed " + filteredEntries.size() + " entries of type: " + displayType);
        return sb.toString().trim();
    }
}