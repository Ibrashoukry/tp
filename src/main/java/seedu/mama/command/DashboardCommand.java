package seedu.mama.command;

import seedu.mama.model.Entry;
import seedu.mama.model.EntryList;
import seedu.mama.model.MealEntry;
import seedu.mama.model.MilkEntry;
import seedu.mama.model.WeekCheck;
import seedu.mama.model.WorkoutGoalEntry;
import seedu.mama.model.WorkoutGoalQueries;
import seedu.mama.storage.Storage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Displays a combined health dashboard of milk output, diet, and fitness.
 */
public class DashboardCommand implements Command {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");

    @Override
    public CommandResult execute(EntryList list, Storage storage) {
        // --- 1. GATHER DATA ---
        LocalDate today = LocalDate.now();
        LocalDateTime weekStart = WeekCheck.weekStartMonday(LocalDateTime.now());

        // Diet data
        int caloriesToday = 0;
        for (Entry entry : list.asList()) {
            if (entry instanceof MealEntry) {
                caloriesToday += ((MealEntry) entry).getCalories();
            }
        }
        Integer calorieGoal = storage.loadGoal();

        // Milk data
        int milkToday = 0;
        for (Entry entry : list.asList()) {
            if (entry instanceof MilkEntry) {
                MilkEntry milkEntry = (MilkEntry) entry;
                LocalDateTime entryDateTime = LocalDateTime.parse(milkEntry.getDate(), DATE_FORMATTER);
                if (entryDateTime.toLocalDate().isEqual(today)) {
                    milkToday += MilkEntry.getMilkVol(milkEntry.getMilk());
                }
            }
        }

        // Fitness data
        int workoutMinutesThisWeek = WorkoutGoalQueries.sumWorkoutMinutesThisWeek(list, weekStart);
        WorkoutGoalEntry workoutGoal = WorkoutGoalQueries.currentWeekGoal(list, weekStart);

        // --- 2. BUILD THE DASHBOARD STRING ---
        StringBuilder sb = new StringBuilder();
        sb.append("--- Your Daily Health Dashboard ---\n\n");

        // Diet Section
        sb.append(">> DIET (Today)\n");
        sb.append("   - Calories Consumed: ").append(caloriesToday).append(" kcal\n");
        if (calorieGoal != null) {
            int remaining = calorieGoal - caloriesToday;
            sb.append("   - Calorie Goal:      ").append(calorieGoal).append(" kcal\n");
            sb.append("   - Remaining:         ").append(remaining > 0 ? remaining : 0).append(" kcal\n");
        } else {
            sb.append("   - No calorie goal set. Use 'goal <calories>' to set one.\n");
        }
        sb.append("\n");

        // Milk Section
        sb.append(">> MILK (Today)\n");
        sb.append("   - Total Pumped: ").append(milkToday).append(" ml\n\n");

        // Fitness Section
        sb.append(">> FITNESS (This Week)\n");
        sb.append("   - Workout Minutes: ").append(workoutMinutesThisWeek).append(" mins\n");
        if (workoutGoal != null) {
            int remaining = workoutGoal.getMinutesPerWeek() - workoutMinutesThisWeek;
            sb.append("   - Weekly Goal:     ").append(workoutGoal.getMinutesPerWeek()).append(" mins\n");
            sb.append("   - Remaining:         ").append(remaining > 0 ? remaining : 0).append(" mins\n");
        } else {
            sb.append("   - No weekly workout goal set. Use 'workout goal <minutes>'.\n");
        }

        return new CommandResult(sb.toString());
    }
}