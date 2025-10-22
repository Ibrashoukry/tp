package seedu.mama.command;

import seedu.mama.model.Entry;
import seedu.mama.model.EntryList;
import seedu.mama.model.MilkEntry;
import seedu.mama.storage.Storage;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import static seedu.mama.model.MilkEntry.*;

public class DeleteCommand implements Command {
    private static final Logger LOG = Logger.getLogger(DeleteCommand.class.getName());
    private final int indexOneBased;

    public DeleteCommand(int indexOneBased) {
        // Only accept positive indices
        if (indexOneBased <= 0) {
            throw new IllegalArgumentException("indexOneBased must be greater than 0");
        }
        this.indexOneBased = indexOneBased;
    }

    @Override
    public CommandResult execute(EntryList list, Storage storage) throws CommandException {
        Objects.requireNonNull(list, "EntryList is null");
        Objects.requireNonNull(storage, "Storage is null");
        assert list.size() >= 0 : "EntryList size must be non-negative";

        // Invalid index → CommandException
        if (indexOneBased > list.size()) {
            LOG.warning(() -> "Invalid delete index: " + indexOneBased);
            throw new CommandException("Invalid index: " + indexOneBased + System.lineSeparator() + previewList(list));
        }

        // Valid delete
        int zeroBased = indexOneBased - 1;

        Entry removed = list.deleteByIndex(zeroBased);
        try {
            storage.save(list);
        } catch (RuntimeException e) {
            LOG.log(Level.SEVERE, "Failed to persist after delete index=" + indexOneBased, e);
            throw new CommandException("Failed to save after delete", e);
        }
        LOG.info(() -> "Deleted entry at index " + indexOneBased + ": " + removed.toListLine());
        return new CommandResult("Deleted: " + removed.toListLine());
    }

    private static String previewList(EntryList list) {
        if (list.size() == 0) {
            return "No entries yet.";
        }
        StringBuilder sb = new StringBuilder("Here are your entries:");
        for (int i = 0; i < list.size(); i++) {
            sb.append(System.lineSeparator())
                    .append(i + 1)
                    .append(". ")
                    .append(list.get(i).toListLine());
        }
        return sb.toString();
    }
}
