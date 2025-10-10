package seedu.mama.model;

public class WeightEntry extends Entry {
    public WeightEntry(String description) {
        super("WEIGHT", description);
    }

    public String getWeight() {
        return this.description();
    }

    @Override
    public String toStorageString() {
        return "WEIGHT|" + description();
    }

    public static WeightEntry fromStorage(String line) {
        String[] parts = line.split("\\|", 2);
        return new WeightEntry(parts.length > 1 ? parts[1] : "");
    }
}
