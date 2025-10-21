package seedu.mama.model;

public class MilkEntry extends Entry {
    private static int totalMilkVol;

    public static void addTotalMilkVol(int MilkVol) {
        totalMilkVol += MilkVol;
    }

    public static void minusTotalMilkVol(int MilkVol) {
        totalMilkVol -= MilkVol;
    }

    public static int getMilkVol(String volFromStorage) {
        if (volFromStorage.endsWith("ml")) {
            volFromStorage = volFromStorage.substring(0, volFromStorage.length() - 2);
        }
        return Integer.parseInt(volFromStorage);
    }

    public static int fromList(Entry e) {
        String line = e.description();
//        String[] parts = line.split("]");
//        parts[1] = parts[1].trim();
        return getMilkVol(line);
    }

    public static String toTotalMilk() {
        return "Total breast milk pumped: " + totalMilkVol + "ml";
    }

    public MilkEntry(String text) {
        super("MILK", text);
    }

    public String getMilk() {
        return this.description();
    }

    @Override
    public String toStorageString() {
        return "MILK|" + description();
    }

    public static MilkEntry fromStorage(String line) {
        String[] parts = line.split("\\|", 2);
        addTotalMilkVol(getMilkVol(parts[1]));
        return new MilkEntry(parts.length > 1 ? parts[1] : "");
    }
}
