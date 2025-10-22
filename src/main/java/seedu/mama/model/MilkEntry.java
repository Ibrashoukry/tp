package seedu.mama.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class MilkEntry extends Entry {
    private static int totalMilkVol;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");

    private final String date;

    public MilkEntry(String userInput) {
        super("MILK", userInput);
        this.date = LocalDateTime.now().format(formatter);
    }

    public MilkEntry(String userInput, LocalDateTime dateTime) {
        super("MILK", userInput);
        this.date = formatDate(dateTime);
    }

    public static void addTotalMilkVol(int milkVol) {
        totalMilkVol += milkVol;
    }

    public static void minusTotalMilkVol(int milkVol) {
        totalMilkVol -= milkVol;
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



    public String formatDate(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
        return date.format(formatter);
    }

    @Override
    public String toListLine() {
        return "[" + type() + "] " + description() + " (" + date + ")";
    }

    public String getMilk() {
        return this.description();
    }

    public String getDate() {
        return this.date;
    }

    @Override
    public String toStorageString() {
        // Stable Storage: MILK|<volume>|<date>
        return "MILK|" + description() + "|" + date;
    }

    public static MilkEntry fromStorage(String line) {
        // Expected: MILK|<volume>|<date> <time>
        String[] parts = line.split("\\|");
        String volume = parts[1];
        String dateStr = parts[2]; // e.g., "22/10/25 11:19"

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
        LocalDateTime date = LocalDateTime.parse(dateStr, formatter);

        addTotalMilkVol(getMilkVol(volume));

        return new MilkEntry(volume, date);
    }
}
