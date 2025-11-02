package seedu.mama.model;

import java.text.DecimalFormat;

public class WeightEntry extends Entry {

    // FIX 1 & 2: Moved to the top and corrected modifier order.
    private static final DecimalFormat DECIMAL_FORMAT= new DecimalFormat("0.00");

    private final double weightInKG;

    /**
     * Constructs WeightEntry with the given weight value
     */
    public WeightEntry(double weightInKG) {
        super("WEIGHT", formatWeight(weightInKG));
        this.weightInKG = weightInKG;
    }

    private static String formatWeight(double weight) {
        return DECIMAL_FORMAT.format(weight) + "kg";
    }

    public double getWeight() {
        return this.weightInKG;
    }

    @Override
    public String toStorageString() {
        return "WEIGHT|" + this.weightInKG;
    }

    /**
     * Creates a WeightEntry from a storage line
     */
    public static WeightEntry fromStorage(String line) {
        String[] parts = line.split("\\|", 2);
        String weightString = parts.length > 1 ? parts[1] : "0.0";

        try {
            double storedWeight = Double.parseDouble(weightString);
            return new WeightEntry(storedWeight);
        } catch (NumberFormatException e) {
            System.err.println("Invalid weight: " + weightString);
            return new  WeightEntry(0.0);
        }
    }
}
