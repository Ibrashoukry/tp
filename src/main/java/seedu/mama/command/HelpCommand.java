package seedu.mama.command;

import seedu.mama.model.EntryList;
import seedu.mama.storage.Storage;

/**
 * Displays a help message with all available commands and their formats.
 */
public class HelpCommand implements Command {
    public static final String COMMAND_WORD = "help";

    public static final String HELP_MESSAGE_HEADER = "Here are the available commands:\n";

    @Override
    public CommandResult execute(EntryList list, Storage storage) {
        // Get the formatted list of all command usages from the enum
        String allUsages = CommandType.getFormattedUsage();
        return new CommandResult(HELP_MESSAGE_HEADER + "  - " + allUsages);
    }
}
