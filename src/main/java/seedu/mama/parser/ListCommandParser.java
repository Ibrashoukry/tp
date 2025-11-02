package seedu.mama.parser;

import seedu.mama.command.Command;
import seedu.mama.command.CommandException;
import seedu.mama.command.CommandType; // Import CommandType
import seedu.mama.command.ListCommand;
import seedu.mama.model.Entry;
import seedu.mama.model.EntryType;

import java.util.function.Predicate;

/**
 * Parses arguments for the ListCommand.
 */
public class ListCommandParser {

    /**
     * Parses the arguments part of a list command string.
     * @param arguments The string containing arguments after the "list" keyword.
     * @return A ListCommand configured with the appropriate filter.
     * @throws CommandException If the arguments are invalid.
     */
    public static Command parseListCommand(String arguments) throws CommandException {
        if (arguments.trim().isEmpty()) {
            return new ListCommand();
        }

        String[] parts = arguments.trim().split("\\s+");
        if (parts.length != 2 || !parts[0].equals("/t")) {
            // Use the consistent error message from the enum
            throw new CommandException("Invalid format! " + CommandType.LIST.getUsage());
        }

        String typeInput = parts[1].toLowerCase();
        try {
            EntryType entryType = EntryType.valueOf(typeInput.toUpperCase());
            Predicate<Entry> predicate = entry -> entry.type().equalsIgnoreCase(entryType.name());
            return new ListCommand(predicate, typeInput);
        } catch (IllegalArgumentException e) {
            // Use the consistent error message from the enum
            throw new CommandException(String.format("Unknown type: '%s'. %s",
                    typeInput, CommandType.LIST.getUsage()));
        }
    }
}
