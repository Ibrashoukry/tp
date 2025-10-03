package seedu.mama.command;

import seedu.mama.model.EntryList;
import seedu.mama.storage.Storage;

/**
 * A user-invoked action.
 */
public interface Command {
    /**
     * Executes and returns a user-visible message.
     */
    String execute(EntryList list, Storage storage);

    default boolean isExit() {
        return false;
    }
}
