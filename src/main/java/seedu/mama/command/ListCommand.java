package seedu.mama.command;

import seedu.mama.model.Entry;
import seedu.mama.model.EntryList;
import seedu.mama.storage.Storage;

public class ListCommand implements Command {

    @Override
    public String execute(EntryList entries, Storage storage) {
        if (entries.size() == 0) {
            return "No entries found.";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < entries.size(); i++) {
            Entry e = entries.get(i);
            sb.append(i + 1) // 1-based numbering
                    .append(". ")
                    .append(e.toListLine())
                    .append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
