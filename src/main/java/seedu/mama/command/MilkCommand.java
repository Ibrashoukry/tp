package seedu.mama.command;

import seedu.mama.model.Entry;
import seedu.mama.model.EntryList;
import seedu.mama.model.MilkEntry;
import seedu.mama.storage.Storage;

public class MilkCommand implements Command {
    public final int milkVolume;
    public MilkCommand(int milkVolume) {
        this.milkVolume = milkVolume;
    }

    @Override
    public String execute(EntryList list, Storage storage) {

        if (milkVolume <= 0) {
            return "Invalid Volume: " + milkVolume + " | Volume cannot be negative"
                    + System.lineSeparator();
        }
        Entry newMilk = new MilkEntry(milkVolume + "ml");
        list.add(newMilk);
        storage.save(list);

        return "Breast Milk Pumped: " + newMilk.toListLine();
    }
}
