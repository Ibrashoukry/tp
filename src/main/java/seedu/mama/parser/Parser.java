package seedu.mama.parser;

import seedu.mama.command.CommandException;
import seedu.mama.command.CommandResult;
import seedu.mama.command.WeightCommand;

import seedu.mama.command.AddMealCommand;

import seedu.mama.command.Command;
import seedu.mama.command.DeleteCommand;
import seedu.mama.command.AddWorkoutCommand;
import seedu.mama.command.MilkCommand;

/**
 * Parses raw user input strings into the appropriate {@link Command} objects.
 * <p>
 * The {@code Parser} is responsible for interpreting user commands and returning
 * executable {@code Command} instances. It performs basic input validation and
 * provides feedback through {@link CommandResult} messages when invalid syntax
 * or arguments are detected.
 * <p>
 * Example usages:
 * <ul>
 *     <li>{@code delete 2} → returns a {@link DeleteCommand}</li>
 *     <li>{@code milk 150} → returns a {@link MilkCommand}</li>
 *     <li>{@code weight 5} → returns a {@link WeightCommand}</li>
 * </ul>
 */
public class Parser {

    /**
     * Parses the given raw user input and returns the corresponding {@link Command}.
     * <p>
     * The parser identifies the command keyword (e.g., {@code delete}, {@code list}, {@code milk})
     * and constructs an appropriate {@code Command} instance. If the command is invalid or
     * arguments are missing, the parser returns a command that produces a {@link CommandResult}
     * containing an error message.
     *
     * @param input Raw user input entered by the user.
     * @return A {@link Command} representing the parsed user intent.
     * @throws CommandException If a parsing error occurs that cannot be handled internally.
     */
    public static Command parse(String input) throws CommandException {
        String trimmed = input.trim();

        // Handles the "bye" command (terminates the program)
        if (trimmed.equals("bye")) {
            return (l, s) -> new CommandResult("Bye. Hope to see you again soon!");
        }

        // Handles "delete" commands
        if (trimmed.startsWith("delete")) {
            String[] parts = trimmed.split("\\s+");
            if (parts.length == 2 && parts[1].equals("?")) {
                return new DeleteCommand(-1);
            }
            if (parts.length < 2) {
                return (l, s) -> new CommandResult("Usage: delete INDEX");
            }
            try {
                return new DeleteCommand(Integer.parseInt(parts[1]));
            } catch (NumberFormatException e) {
                return (l, s) -> new CommandResult("INDEX must be a number.");
            }
        }

        // Handles "list" command
        if (trimmed.startsWith("list")) {
            String arguments = trimmed.substring("list".length());
            return ListCommandParser.parseListCommand(arguments);
        }

        // Handles "milk" command
        if (trimmed.startsWith("milk")) {
            String[] parts = trimmed.split("\\s+");

            if (parts.length < 2) {
                return (l, s) -> new CommandResult("Usage: milk VOLUME | How much breast milk did you pump?");
            }
            try {
                return new MilkCommand(Integer.parseInt(parts[1]));
            } catch (NumberFormatException e) {
                return (l, s) -> new CommandResult("VOLUME must be a number.");
            }
        }

        // Handles "workout" command
        if (trimmed.startsWith("workout ")) {
            return AddWorkoutCommand.fromInput(trimmed);
        }

        // Handles "weight" command
        if (trimmed.startsWith("weight")) {
            String[] parts = trimmed.split("\\s+");
            if (parts.length == 2 && parts[1].equals("?")) {
                return new WeightCommand(-1);
            }
            if (parts.length < 2) {
                return (l, s) -> new CommandResult("Weight must be a number. Try `weight`+ 'value of weight'");
            }
            try {
                return new WeightCommand(Integer.parseInt(parts[1]));
            } catch (NumberFormatException e) {
                return (l, s) -> new CommandResult("Weight must be a number. Try `weight`+ 'value of weight'");
            }
        }

        // Handles "meal" command
        if (trimmed.startsWith("meal")) {
            return AddMealCommand.fromInput(trimmed);
        }

        // Handles "measure" command
        if (trimmed.startsWith("measure")) {
            String[] parts = trimmed.split("\\s+");
            if (parts.length == 2 && "?".equals(parts[1])) {
                return (l, s) -> new CommandResult(
                        "Usage: measure waist/<cm> hips/<cm> [chest/<cm>] [thigh/<cm>] [arm/<cm>]");
            }

            Integer waist = null;
            Integer hips = null;
            Integer chest = null;
            Integer thigh = null;
            Integer arm = null;

            for (int i = 1; i < parts.length; i++) {
                String p = parts[i].trim().toLowerCase();
                try {
                    if (p.startsWith("waist/")) {
                        waist = Integer.parseInt(p.substring(6));
                    } else if (p.startsWith("hips/")) {
                        hips = Integer.parseInt(p.substring(5));
                    } else if (p.startsWith("chest/")) {
                        chest = Integer.parseInt(p.substring(6));
                    } else if (p.startsWith("thigh/")) {
                        thigh = Integer.parseInt(p.substring(6));
                    } else if (p.startsWith("arm/")) {
                        arm = Integer.parseInt(p.substring(4));
                    } else {
                        return (l, s) -> new CommandResult("Unknown field: " + p);
                    }
                } catch (NumberFormatException e) {
                    return (l, s) -> new CommandResult("Invalid number format for: " + p);
                }
            }


            try {
                return new seedu.mama.command.AddMeasurementCommand(waist, hips, chest, thigh, arm);
            } catch (seedu.mama.command.CommandException e) {
                return (l, s) -> new CommandResult(e.getMessage());
            }
        }


        return (l, s) -> new CommandResult("Unknown command.");
    }
}
