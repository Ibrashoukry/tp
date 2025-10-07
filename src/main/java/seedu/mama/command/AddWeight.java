package seedu.mama.command;

import seedu.mama.model.Entry;
import seedu.mama.model.EntryList;
import seedu.mama.model.WeightEntry;
import seedu.mama.storage.Storage;

public class AddWeight {

    public void addWeight(WeightEntry weight) {
        // TO BE CONTINUED
    }

    private static String preview(EntryList list) {
        if (list.size() == 0) {
            return "No entries yet.";
        }
        StringBuilder sb = new StringBuilder("Your past weights:");
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains("weight")) {
                sb.append(System.lineSeparator()).append(list.get(i).toListLine());
            }
        }
        return sb.toString();
    }

    @Override
    public String execute(EntryList list, Storage storage) {
        // TO BE CONTINUED
    }
}
