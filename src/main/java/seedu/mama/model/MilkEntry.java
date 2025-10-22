package seedu.mama.model;

import seedu.mama.command.CommandException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static seedu.mama.command.AddMilkCommand.parseDate;

public class MilkEntry extends Entry {
    private static int totalMilkVol;
    private LocalDate dateOfPump;

    public static void addTotalMilkVol(int MilkVol) {
        totalMilkVol += MilkVol;
    }

    public static void minusTotalMilkVol(int MilkVol) {
        totalMilkVol -= MilkVol;
    }

    public LocalDate getDateOfPump() {
        return dateOfPump;
    }

    public static int getMilkVol(String volFromStorage) {
        if (volFromStorage.endsWith("ml")) {
            volFromStorage = volFromStorage.substring(0, volFromStorage.length() - 2);
        }
        return Integer.parseInt(volFromStorage);
    }

    public static int fromList(Entry e) {
        String line = e.description();
        return getMilkVol(line);
    }

    public static String toTotalMilk() {
        return "Total breast milk pumped: " + totalMilkVol + "ml";
    }

    public MilkEntry(String userInput, LocalDate dateOfPump) {
        super("MILK", userInput);
        this.dateOfPump = dateOfPump;
    }

    @Override
    public String toListLine() {
        return "[" + type() + "] " + description() + " (" + getDateOfPump() + ")";
    }

    public String getMilk() {
        return this.description();
    }

    @Override
    public String toStorageString() {
        // Stable Storage: MILK|<volume>|<date>
        return "MILK|" + description() + "|" + getDateOfPump();
    }

    public static MilkEntry fromStorage(String line) {
        // Expected: MILK|<volume>|<date>
        String[] parts = line.split("\\|");
        String volume = parts[1];
        String dateStr = parts[2];

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd");
        LocalDate date =  LocalDate.parse(dateStr, formatter);
        addTotalMilkVol(getMilkVol(volume));

        return new MilkEntry(volume, date);
    }
}
