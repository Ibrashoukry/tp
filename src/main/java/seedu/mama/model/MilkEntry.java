package seedu.mama.model;

public class MilkEntry extends Entry {
    private static int totalMilkVol;

    public static void addTotalMilkVol(int MilkVol) {
        totalMilkVol += MilkVol;
    }

    public static int getMilkVol(String volFromStorage) {
        if (volFromStorage.endsWith("ml")) {
            volFromStorage = volFromStorage.substring(0, volFromStorage.length() - 2);
        }
        return Integer.parseInt(volFromStorage);
    }

    public static void updateTotalMilkVol(String line) {
        int milkVol = getMilkVol(line);
        addTotalMilkVol(milkVol);
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
        updateTotalMilkVol(parts[1]);
        return new MilkEntry(parts.length > 1 ? parts[1] : "");
    }
}
