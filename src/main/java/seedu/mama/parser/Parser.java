package seedu.mama.parser;

import seedu.mama.command.CommandException;
import seedu.mama.command.WeightCommand;

import seedu.mama.command.AddMealCommand;

import seedu.mama.command.Command;
import seedu.mama.command.DeleteCommand;
import seedu.mama.command.AddWorkoutCommand;
import seedu.mama.command.ListCommand;
import seedu.mama.command.AddMilkCommand;

public class Parser {
    /**
     * Parses raw input into a Command.
     */
    public static Command parse(String input) throws CommandException {
        String trimmed = input.trim();
        if (trimmed.equals("bye")) {
            return (l, s) -> "Bye. Hope to see you again soon!";
        }
        if (trimmed.startsWith("delete")) {
            String[] parts = trimmed.split("\\s+");
            if (parts.length == 2 && parts[1].equals("?")) {
                return new DeleteCommand(-1);
            }
            if (parts.length < 2) {
                return (l, s) -> "Usage: delete INDEX";
            }
            try {
                return new DeleteCommand(Integer.parseInt(parts[1]));
            } catch (NumberFormatException e) {
                return (l, s) -> "INDEX must be a number.";
            }
        } else if (trimmed.startsWith("list")) {
            return new ListCommand();
        }

        if (trimmed.startsWith("milk")) {
//            String[] parts = trimmed.split("\\s+");
//
//            if (parts.length < 2) {
//                return (l, s) -> "Usage: milk VOLUME | How much breast milk did you pump?";
//            }
//            try {
//                return new AddMilkCommand(Integer.parseInt(parts[1]));
//            } catch (NumberFormatException e) {
//                return (l, s) -> "VOLUME must be a number.";
//            }
            return AddMilkCommand.fromInput(trimmed);
        }

        if (trimmed.startsWith("workout ")) {
            return AddWorkoutCommand.fromInput(trimmed);
        }

        if (trimmed.startsWith("weight")) {
            String[] parts = trimmed.split("\\s+");
            if (parts.length == 2 && parts[1].equals("?")) {
                return new WeightCommand(-1);
            }
            if (parts.length < 2) {
                return (l, s) -> "Weight must be a number. Try `weight`+ 'value of weight'";
            }
            try {
                return new WeightCommand(Integer.parseInt(parts[1]));
            } catch (NumberFormatException e) {
                return (l, s) -> "Weight must be a number. Try `weight`+ 'value of weight'";
            }
        }
        
        if (trimmed.startsWith("meal")) {
            return AddMealCommand.fromInput(trimmed);
        }

        return (l, s) -> "Unknown command.";
    }
}
