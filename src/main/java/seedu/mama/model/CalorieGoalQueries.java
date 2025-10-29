package seedu.mama.model;

import seedu.mama.command.Command;
import seedu.mama.command.CommandResult;
import seedu.mama.command.CommandException;
import seedu.mama.command.SetCalorieGoalCommand;

/**
 * Provides helper methods for calorie goal-related commands, such as viewing
 * or setting the user's calorie goal.
 */
public final class CalorieGoalQueries {

    private CalorieGoalQueries() {}

    /**
     * Creates a command that displays the current calorie goal and progress.
     *
     * @return a {@link Command} to view the current calorie goal
     */
    public static Command viewCalorieGoal() {
        return (list, storage) -> {
            Integer goalValue = (storage != null) ? storage.loadGoal() : null;
            if (goalValue == null) {
                return new CommandResult("No calorie goal set yet. Use: calorie goal <calories>");
            }

            int totalCal = list.asList().stream()
                    .filter(e -> e.type().equals("MEAL"))
                    .mapToInt(e -> ((seedu.mama.model.MealEntry) e).getCalories())
                    .sum();

            String progress = "Your current calorie goal is: " + goalValue + " kcal."
                    + " | Progress: " + totalCal + " kcal logged.";
            return new CommandResult(progress);
        };
    }

    /**
     * Parses and returns a command to set a new calorie goal.
     *
     * @param trimmed the full user input (e.g. "calorie goal 2000")
     * @return a {@link Command} that sets a new calorie goal
     * @throws CommandException if the input is invalid
     */
    public static Command setCalorieGoal(String trimmed) throws CommandException {
        // "calorie goal <calorie goal>" -> set new goal
        String desc = trimmed.substring("calorie goal".length()).trim();

        if (desc.isEmpty()) {
            throw new CommandException("Invalid format! Try: calorie goal <calories>");
        }

        try {
            int goal = Integer.parseInt(desc.split("\\s+")[0]);
            return new SetCalorieGoalCommand(goal);
        } catch (NumberFormatException e) {
            throw new CommandException("Calorie goal must be a number.");
        }
    }
}
