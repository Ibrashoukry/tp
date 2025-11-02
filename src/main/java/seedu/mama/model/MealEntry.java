package seedu.mama.model;

import seedu.mama.util.DateTimeUtil;

import java.time.LocalDateTime;

public class MealEntry extends TimestampedEntry {
    private int calories; // kcal
    private Integer protein; // grams or null
    private Integer carbs;
    private Integer fat;

    public MealEntry(String mealType, int calories) {
        super("MEAL", mealType.trim());
        this.calories = calories;
    }

    public MealEntry(String mealType, int calories, LocalDateTime when) {
        super("MEAL", mealType.trim(), when);
        this.calories = calories;
    }

    public MealEntry(String mealType, int calories, Integer protein, Integer carbs, Integer fat) {
        super("MEAL", mealType.trim());
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
    }

    public MealEntry(String mealType, int calories, Integer protein, Integer carbs, Integer fat, LocalDateTime when) {
        super("MEAL", mealType.trim(), when);
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
    }

    public int getCalories() {
        return calories;
    }

    public Integer getProtein() {
        return protein;
    }

    public Integer getCarbs() {
        return carbs;
    }

    public Integer getFat() {
        return fat;
    }

    @Override
    public String toListLine() {
        String macroInfo = "";
        if (protein != null || carbs != null || fat != null) {
            macroInfo = String.format(" [protein:%sg carbs:%sg fat:%sg]",
                    protein == null ? "-" : protein,
                    carbs == null ? "-" : carbs,
                    fat == null ? "-" : fat);
        }
        return "[Meal] " + description() + " (" + calories + " kcal)" + macroInfo + " (" + timestampString() + ")";
    }

    @Override
    public String toStorageString() {
        // Stable storage: MEAL|<type>|<calories>|<protein>|<carbs>|<fat>|timestamp
        return withTimestamp("MEAL|" + description() + "|" + calories
                + "|" + (protein != null ? protein : "-")
                + "|" + (carbs != null ? carbs : "-")
                + "|" + (fat != null ? fat : "-"));
    }

    public static MealEntry fromStorage(String line) {
        // Expected: MEAL|<type>|<calories>|<protein>|<carbs>|<fat>|timestamp
        String[] parts = line.split("\\|");
        String type = parts.length > 1 ? parts[1] : "";
        int cal = parts.length > 2 ? Integer.parseInt(parts[2]) : 0;

        Integer protein = null;
        Integer carbs = null;
        Integer fat = null;

        if (parts.length > 3 && !parts[3].equals("-")) {
            protein = Integer.parseInt(parts[3]);
        }
        if (parts.length > 4 && !parts[4].equals("-")) {
            carbs = Integer.parseInt(parts[4]);
        }
        if (parts.length > 5 && !parts[5].equals("-")) {
            fat = Integer.parseInt(parts[5]);
        }
        LocalDateTime ts = DateTimeUtil.parse(parts[6].trim());

        return new MealEntry(type, cal, protein, carbs, fat, ts);
    }
}
