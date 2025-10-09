package seedu.mama.parser;

import seedu.mama.command.Command;
import seedu.mama.command.DeleteCommand;
import seedu.mama.command.AddWorkoutCommand;
import seedu.mama.command.ListCommand;

public class Parser {
    /**
     * Parses raw input into a Command.
     */
    public Command parse(String input) {
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
                return (l, s) -> "Usage: delete INDEX | delete ?";
            }
            try {
                return new DeleteCommand(Integer.parseInt(parts[1]));
            } catch (NumberFormatException e) {
                return (l, s) -> "INDEX must be a number. Try `delete ?`.";
            }
        } else if (trimmed.startsWith("list")) {
            return new ListCommand();
        }
        if (trimmed.startsWith("workout")) {
            return AddWorkoutCommand.fromInput(trimmed);
        }
        return (l, s) -> "Unknown command.";
    }
}
