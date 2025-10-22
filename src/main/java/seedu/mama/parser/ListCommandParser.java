package seedu.mama.parser;

import seedu.mama.command.Command;
import seedu.mama.command.CommandException;
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
        // Case 1: No arguments are provided (e.g., "list")
        if (arguments.trim().isEmpty()) {
            return new ListCommand();
        }

        // Case 2: Arguments are provided, expect a type filter (e.g., "list /t meal")
        String[] parts = arguments.trim().split("\\s+");
        if (parts.length != 2 || !parts[0].equals("/t")) {
            throw new CommandException("Invalid format! Usage: list /t ["
                    + EntryType.getValidTypesString() + "]");
        }

        String typeInput = parts[1].toLowerCase();
        try {
            // Let the enum handle the validation automatically
            EntryType entryType = EntryType.valueOf(typeInput.toUpperCase());

            // Create a more robust predicate by checking the entry's own type string
            Predicate<Entry> predicate = entry -> entry.type().equalsIgnoreCase(entryType.name());

            return new ListCommand(predicate, typeInput);
        } catch (IllegalArgumentException e) {
            // This catch block runs if the string is not a valid enum constant
            throw new CommandException(String.format("Unknown type: '%s'. Please use one of: %s.",
                    typeInput, EntryType.getValidTypesString()));
        }
    }
}
