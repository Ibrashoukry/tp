package seedu.mama.model;

public class GoalEntry extends Entry{
    private final int calorieGoal;

    public GoalEntry(int calorieGoal) {
        super("GOAL", "" + calorieGoal);
        this.calorieGoal = calorieGoal;
    }

    @Override
    public String toStorageString() {
        return "GOAL|" + calorieGoal;
    }

    public static GoalEntry fromStorage(String line) {
        String[] parts = line.split("\\|", 2);
        int goal = (parts.length > 1) ? Integer.parseInt(parts[1]) : 0;
        return new GoalEntry(goal);
    }
}
