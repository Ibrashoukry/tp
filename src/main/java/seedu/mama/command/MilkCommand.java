package seedu.mama.command;

import seedu.mama.model.Entry;
import seedu.mama.model.EntryList;
import seedu.mama.model.MilkEntry;
import seedu.mama.storage.Storage;

/**
 * Adds how much breast milk has been pumped and returns the user
 * the amount of breast milk pumped in ml
 */
public class MilkCommand implements Command {

    public final int milkVolume;

    // Only accept positive volume
    public MilkCommand(int milkVolume) throws CommandException {
        if (milkVolume < 0) {
            throw new CommandException("milkVolume must be a positive number!");
        }
        this.milkVolume = milkVolume;
    }

    @Override
    public CommandResult execute(EntryList list, Storage storage) {
        assert this.milkVolume > 0 : "The milkVolume must be greater than 0!";

        Entry newMilk = new MilkEntry(milkVolume + "ml");
        list.add(newMilk);
        if (storage != null) {
            storage.save(list);
        }

        return new CommandResult("Breast Milk Pumped: " + newMilk.toListLine());
    }
}
