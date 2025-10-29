package seedu.mama.model;

public class MealEntry extends Entry {
    private int calories; // kcal
    private Integer protein; // grams or null
    private Integer carbs;
    private Integer fat;

    public MealEntry(String mealType, int calories) {
        super("MEAL", mealType.trim());
        this.calories = calories;
    }

    public MealEntry(String mealType, int calories, Integer protein, Integer carbs, Integer fat) {
        super("MEAL", mealType.trim());
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
        return "[Meal] " + description() + " (" + calories + " kcal)" + macroInfo;
    }

    @Override
    public String toStorageString() {
        // Stable storage: MEAL|<type>|<calories>|<protein>|<carbs>|<fat>
        return "MEAL|" + description() + "|" + calories
                + "|" + (protein != null ? protein : "-")
                + "|" + (carbs != null ? carbs : "-")
                + "|" + (fat != null ? fat : "-");
    }

    public static MealEntry fromStorage(String line) {
        // Expected: MEAL|<type>|<calories>|<protein>|<carbs>|<fat>
        String[] parts = line.split("\\|");
        String type = parts.length > 1 ? parts[1] : "";
        int cal = parts.length > 2 ? Integer.parseInt(parts[2]) : 0;

        Integer protein = null, carbs = null, fat = null;

        if (parts.length > 3 && !parts[3].equals("-")) protein = Integer.parseInt(parts[3]);
        if (parts.length > 4 && !parts[4].equals("-")) carbs = Integer.parseInt(parts[4]);
        if (parts.length > 5 && !parts[5].equals("-")) fat = Integer.parseInt(parts[5]);

        return new MealEntry(type, cal, protein, carbs, fat);
    }
}
