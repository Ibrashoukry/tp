package seedu.mama.command;

import seedu.mama.model.Entry;
import seedu.mama.model.EntryList;
import seedu.mama.storage.Storage;

public class DeleteCommand implements Command {
    private final int indexOneBased; // -1 means preview only

    public DeleteCommand(int indexOneBased) {
        this.indexOneBased = indexOneBased;
    }

    private static String preview(EntryList list) {
        if (list.size() == 0) {
            return "No entries yet.";
        }
        StringBuilder sb = new StringBuilder("Here are your entries:");
        for (int i = 0; i < list.size(); i++) {
            sb.append(System.lineSeparator()).append(i + 1).append(". ").append(list.get(i).toListLine());
        }
        return sb.toString();
    }

    @Override
    public String execute(EntryList list, Storage storage) {
        if (indexOneBased == -1) {
            return preview(list);
        }
        if (indexOneBased <= 0 || indexOneBased > list.size()) {
            return "Invalid index: " + indexOneBased + System.lineSeparator() + preview(list);
        }
        Entry removed = list.deleteByIndex(indexOneBased - 1);
        storage.save(list);
        return "Deleted: " + removed.toListLine();
    }
}
