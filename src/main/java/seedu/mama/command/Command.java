package seedu.mama.command;

import seedu.mama.model.EntryList;
import seedu.mama.storage.Storage;

public interface Command {
    /**
     * Executes the command.
     *
     * @param lists The EntryList to operate on.
     * @param storage The Storage.
     */
    String execute(EntryList lists, Storage storage) throws CommandException;

    /**
     * Whether this command should cause the program to exit.
     */
    default boolean isExit() {
        return false;
    }
}
