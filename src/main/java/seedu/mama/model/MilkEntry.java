package seedu.mama.model;

import seedu.mama.util.DateTimeUtil;

import java.time.LocalDateTime;

/**
 * Represents a milk-pumping session.
 * <p>
 * Storage format (strict):
 * MILK|<volume>|<dd/MM/yy HH:mm>
 */
public class MilkEntry extends TimestampedEntry {

    // ---- Back-compat static counters used elsewhere (e.g., AddMilkCommand, EntryList) ----
    private static int totalMilkVol = 0;

    public static void addTotalMilkVol(int milkVol) {
        totalMilkVol += milkVol;
    }

    public static void minusTotalMilkVol(int milkVol) {
        totalMilkVol -= milkVol;
    }

    public static String toTotalMilk() {
        return "Total breast milk pumped: " + totalMilkVol + "ml";
    }

    private final int volumeMl;

    /**
     * New entries created from user input. Timestamp = now().
     */
    public MilkEntry(String userInput) {
        super("MILK", normalizeVolume(userInput));
        this.volumeMl = parseVolumeMl(userInput);
    }

    /**
     * Deserialization path with explicit timestamp.
     */
    private MilkEntry(String userInput, LocalDateTime when) {
        super("MILK", normalizeVolume(userInput), when);
        this.volumeMl = parseVolumeMl(userInput);
    }

    public String getMilk() {
        return this.description();
    }

    @Override
    public String toListLine() {
        // Example: [MILK] 150ml (28/10/25 01:14)
        return "[" + type() + "] " + description() + " (" + timestampString() + ")";
    }

    @Override
    public String toStorageString() {
        // MILK|150ml|28/10/25 01:14
        return withTimestamp("MILK|" + description());
    }

    /**
     * Strict parser: MILK|<volume>|<dd/MM/yy HH:mm>
     */
    public static MilkEntry fromStorage(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length != 3 || !"MILK".equals(parts[0])) {
            throw new IllegalArgumentException("Invalid MILK entry line: " + line);
        }
        String volume = parts[1];
        LocalDateTime ts = DateTimeUtil.parse(parts[2].trim());

        // keep your running total behavior during load
        addTotalMilkVol(parseVolumeMl(volume));

        return new MilkEntry(volume, ts);
    }

    // -------- helpers --------

    private static int parseVolumeMl(String input) {
        String s = input.trim().toLowerCase();
        if (s.endsWith("ml")) s = s.substring(0, s.length() - 2);
        return Integer.parseInt(s);
    }

    private static String normalizeVolume(String input) {
        String s = input.trim().toLowerCase();
        return s.endsWith("ml") ? s : s + "ml";
    }
}
