package seedu.mama.command;

import seedu.mama.model.Entry;
import seedu.mama.model.EntryList;
import seedu.mama.model.WeightEntry;
import seedu.mama.storage.Storage;

public class WeightCommand implements Command {
    private final int weightInput;

    public WeightCommand(int weightInput) {
        this.weightInput = weightInput;
    }

    private static String preview(EntryList list) {
        if (list.size() == 0) {
            return "No entries yet.";
        }
        StringBuilder sb = new StringBuilder("Your past weights:");
        for (int i = 0; i < list.size(); i++) {
            Entry e = list.get(i);
            if (e.type().equals("WEIGHT")) {
                sb.append(System.lineSeparator()).append(e.toListLine());
            }
        }
        return sb.toString();
    }

    @Override
    public String execute(EntryList list, Storage storage) {
        // TO BE CONTINUED


        if (weightInput == -1) {
            return preview(list);
        }

        if (weightInput <= 0) {
            return "Invalid weight: " + weightInput + "| Weight cannot be negative"
                    + System.lineSeparator() + preview(list);
        }
        Entry newWeight = new WeightEntry(weightInput + "kg");
        list.add(newWeight);
        if (storage != null) {
            storage.save(list);
        }
        return "Added new weight entry: " + newWeight.toListLine();
    }
}
