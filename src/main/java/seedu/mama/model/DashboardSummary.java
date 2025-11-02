package seedu.mama.model;

import seedu.mama.storage.Storage;
import seedu.mama.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Gathers and holds all the calculated data required for the health dashboard.
 */
public class DashboardSummary {
    private static final Logger LOGGER = Logger.getLogger(DashboardSummary.class.getName());

    private final int caloriesToday;
    private final Integer calorieGoal;
    private final int milkToday;
    private final int workoutMinutesThisWeek;
    private final WorkoutGoalEntry workoutGoal;

    /**
     * Constructs a summary by analyzing the entry list and storage.
     *
     * @param list    The list of all entries.
     * @param storage The storage to load goals from.
     */
    public DashboardSummary(EntryList list, Storage storage) {
        // Assertions for non-null inputs (programming errors)
        assert list != null : "EntryList cannot be null";
        assert storage != null : "Storage cannot be null";

        LOGGER.log(Level.INFO, "Calculating dashboard summary data...");

        LocalDate today = LocalDate.now();
        LocalDateTime weekStart = DateTimeUtil.weekStartMonday(LocalDateTime.now());

        this.caloriesToday = calculateCaloriesToday(list);
        this.calorieGoal = storage.loadGoal(); // Assumes storage handles potential nulls
        this.milkToday = calculateMilkToday(list, today);
        this.workoutMinutesThisWeek = WorkoutGoalQueries.sumWorkoutMinutesThisWeek(list.asList(), weekStart);
        this.workoutGoal = WorkoutGoalQueries.currentWeekGoal(list.asList(), weekStart);

        LOGGER.log(Level.INFO, "Dashboard summary calculated.");
    }

    private int calculateCaloriesToday(EntryList list) {
        assert list != null : "EntryList cannot be null for calorie calculation";
        LocalDate today = LocalDate.now();

        return list.asList().stream()
                .filter(entry -> entry instanceof MealEntry)
                .map(entry -> (MealEntry) entry)
                .filter(meal -> meal.timestamp().toLocalDate().isEqual(today))
                .mapToInt(MealEntry::getCalories)
                .sum();
    }

    private int calculateMilkToday(EntryList list, LocalDate today) {
        assert list != null : "EntryList cannot be null for milk calculation";
        assert today != null : "Today's date cannot be null";

        return list.asList().stream()
                .filter(entry -> entry instanceof MilkEntry)
                .map(entry -> (MilkEntry) entry)
                .filter(milk -> milk.timestamp().toLocalDate().isEqual(today))
                .mapToInt(milk -> {
                    try {
                        String volStr = milk.getMilk().replace("ml", "").trim();
                        return Integer.parseInt(volStr);
                    } catch (NumberFormatException e) {
                        return 0; // Ignore entries with bad formatting
                    }
                })
                .sum();
    }


    // Getters
    public int getCaloriesToday() {
        return caloriesToday;
    }

    public Integer getCalorieGoal() {
        return calorieGoal;
    }

    public int getMilkToday() {
        return milkToday;
    }

    public int getWorkoutMinutesThisWeek() {
        return workoutMinutesThisWeek;
    }

    public WorkoutGoalEntry getWorkoutGoal() {
        return workoutGoal;
    }
}
