package seedu.mama.model;

public class MilkEntry extends Entry {
    public MilkEntry(String text) {
        super("MILK", text);
    }

    @Override
    public String toStorageString() {
        return "MILK|" + description();
    }

    public static MilkEntry fromStorage(String line) {
        String[] parts = line.split("\\|", 2);
        return new MilkEntry(parts.length > 1 ? parts[1] : "");
    }
}
