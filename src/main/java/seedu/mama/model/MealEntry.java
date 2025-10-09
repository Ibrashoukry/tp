package seedu.mama.model;

public class MealEntry extends Entry {
    private int calories; //kcal

    public MealEntry(String mealType, int calories) {
        super("MEAL", mealType.trim());
        this.calories = calories;
    }

    public int getCalories() {return calories;}

    @Override
    public String toListLine() {
        return "[Meal] " + description() + " (" + calories + " kcal)";
    }

    @Override
    public String toStorageString() {
        // Stable storage: WORKOUT|<type>|<duration>
        return "WORKOUT|" + description() + "|" + calories;
    }

    public static WorkoutEntry fromStorage(String line) {
        // Expected: WORKOUT|<type>|<duration>
        String[] parts = line.split("\\|");
        String type = parts.length > 1 ? parts[1] : "";
        int cal = parts.length > 2 ? Integer.parseInt(parts[2]) : 0;
        return new WorkoutEntry(type, cal);
    }
}
