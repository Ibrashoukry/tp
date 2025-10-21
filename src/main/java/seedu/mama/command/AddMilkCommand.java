package seedu.mama.command;

import seedu.mama.model.Entry;
import seedu.mama.model.EntryList;
import seedu.mama.model.MilkEntry;
import seedu.mama.storage.Storage;
import seedu.mama.ui.Ui;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Adds how much breast milk has been pumped and returns the user
 * the amount of breast milk pumped in ml
 */
public class AddMilkCommand implements Command {
    // 1. Set up logger for this class
    private static final Logger LOG = Logger.getLogger(AddMilkCommand.class.getName());

    public final int milkVolume;
    // Only accept positive volume
    public AddMilkCommand(int milkVolume) throws CommandException {
        if (milkVolume < 0) {
            throw new CommandException("milkVolume must be a positive number!");
        }
        this.milkVolume = milkVolume;
    }

    @Override
    public String execute(EntryList list, Storage storage) {
        // Log the start of the execution
        LOG.log(Level.INFO, "Executing AddMilkCommand.");

        // Use assertion to check for internal errors
        // Confirms that milkVolume is greater than 0
        assert this.milkVolume > 0 : "The milkVolume must be greater than 0!";

        MilkEntry.addTotalMilkVol(milkVolume);

        Entry newMilk = new MilkEntry(milkVolume + "ml", );
        list.add(newMilk);
        if (storage != null) {
            storage.save(list);
        }

        LOG.log(Level.INFO, "AddMilkCommand successfully executed, adding: " + milkVolume + "ml");
        return "Breast Milk Pumped: " + newMilk.toListLine() + "\n" +  MilkEntry.toTotalMilk();
    }
}
