package seedu.mama.model;

public class CalorieGoalEntry extends Entry{
    private final int calorieGoal;

    public CalorieGoalEntry(int calorieGoal) {
        super("CALORIE_GOAL", "" + calorieGoal);
        this.calorieGoal = calorieGoal;
    }

    @Override
    public String toStorageString() {
        return "CALORIE_GOAL|" + calorieGoal;
    }

    public static CalorieGoalEntry fromStorage(String line) {
        String[] parts = line.split("\\|", 2);
        int goal = (parts.length > 1) ? Integer.parseInt(parts[1]) : 0;
        return new CalorieGoalEntry(goal);
    }
}
