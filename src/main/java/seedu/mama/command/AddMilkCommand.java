package seedu.mama.command;

import seedu.mama.model.Entry;
import seedu.mama.model.EntryList;
import seedu.mama.model.MilkEntry;
import seedu.mama.storage.Storage;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;

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
    public final LocalDate dateOfPump;
    // Only accept positive volume
    public AddMilkCommand(int milkVolume, LocalDate dateOfPump) throws CommandException {
        if (milkVolume < 0) {
            throw new CommandException("milkVolume must be a positive number!");
        }
        this.milkVolume = milkVolume;
        this.dateOfPump = dateOfPump;
    }

    public static LocalDate parseDate(String date) throws CommandException {
        if (!date.matches("\\d{4}/\\d{2}/\\d{2}")) {
            throw new CommandException("Invalid date format!");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy/MM/dd");
        try {
            return LocalDate.parse(date, formatter);
        } catch (DateTimeParseException ex) {
            throw new CommandException("Please enter a valid date in YYYY/MM/DD format.");
        }
    }

    public static AddMilkCommand fromInput(String input) throws CommandException {
        String desc = input.substring("milk".length()).trim();

        if (!desc.contains("/date")) {
            throw new CommandException("Invalid date format! Try: milk <volume> /date <date>");
        }

        String[] parts = desc.split("/date");
        if (parts.length != 2) {
            throw new CommandException("Invalid date format! Try: milk <volume> /date <date>");
        }

        String volString = parts[0].trim();
        String dateOfPump = parts[1].trim();
        LocalDate date = parseDate(dateOfPump);

        int milkVolume;
        try {
            milkVolume = Integer.parseInt(volString);
        } catch (NumberFormatException e) {
            throw new CommandException("Volume must be a number");
        }
        return new AddMilkCommand(milkVolume, date);
    }

    @Override
    public String execute(EntryList list, Storage storage) {
        // Log the start of the execution
        LOG.log(Level.INFO, "Executing AddMilkCommand.");

        // Use assertion to check for internal errors
        // Confirms that milkVolume is greater than 0
        assert this.milkVolume > 0 : "The milkVolume must be greater than 0!";

        // increment the total milk volume everytime
        MilkEntry.addTotalMilkVol(milkVolume);

        Entry newMilk = new MilkEntry(milkVolume + "ml", dateOfPump);
        list.add(newMilk);
        if (storage != null) {
            storage.save(list);
        }

        LOG.log(Level.INFO, "AddMilkCommand successfully executed, adding: " + milkVolume + "ml");
        return "Breast Milk Pumped: " + newMilk.toListLine() + "\n" + MilkEntry.toTotalMilk();
    }
}
