package seedu.mama.parser;

import seedu.mama.command.Command;
import seedu.mama.command.CommandException;
import seedu.mama.command.ListCommand;
import seedu.mama.model.Entry;
import seedu.mama.model.MealEntry;
import seedu.mama.model.MilkEntry;
import seedu.mama.model.WeightEntry;
import seedu.mama.model.WorkoutEntry;

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
            throw new CommandException("Invalid format! Usage: list /t [meal|workout|milk|weight]");
        }

        String type = parts[1].toLowerCase();
        Predicate<Entry> predicate;

        switch (type) {
        case "meal":
            predicate = entry -> entry instanceof MealEntry;
            break;
        case "workout":
            predicate = entry -> entry instanceof WorkoutEntry;
            break;
        case "milk":
            predicate = entry -> entry instanceof MilkEntry;
            break;
        case "weight":
            predicate = entry -> entry instanceof WeightEntry;
            break;
        default:
            throw new CommandException("Unknown type: '" + type + "'. Please use 'meal', 'workout', 'milk', or 'weight'.");
        }

        return new ListCommand(predicate, type);
    }
}