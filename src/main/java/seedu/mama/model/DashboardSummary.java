package seedu.mama.model;

import seedu.mama.storage.Storage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Gathers and holds all the calculated data required for the health dashboard.
 */
public class DashboardSummary {
    private static final Logger LOGGER = Logger.getLogger(DashboardSummary.class.getName());
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");

    private final int caloriesToday;
    private final Integer calorieGoal;
    private final int milkToday;
    private final int workoutMinutesThisWeek;
    private final WorkoutGoalEntry workoutGoal;

    /**
     * Constructs a summary by analyzing the entry list and storage.
     * @param list The list of all entries.
     * @param storage The storage to load goals from.
     */
    public DashboardSummary(EntryList list, Storage storage) {
        // Assertions for non-null inputs (programming errors)
        assert list != null : "EntryList cannot be null";
        assert storage != null : "Storage cannot be null";

        LOGGER.log(Level.INFO, "Calculating dashboard summary data...");

        LocalDate today = LocalDate.now();
        LocalDateTime weekStart = WeekCheck.weekStartMonday(LocalDateTime.now());

        this.caloriesToday = calculateCaloriesToday(list);
        this.calorieGoal = storage.loadGoal(); // Assumes storage handles potential nulls
        this.milkToday = calculateMilkToday(list, today);
        this.workoutMinutesThisWeek = WorkoutGoalQueries.sumWorkoutMinutesThisWeek(list, weekStart);
        this.workoutGoal = WorkoutGoalQueries.currentWeekGoal(list, weekStart);

        LOGGER.log(Level.INFO, "Dashboard summary calculated.");
    }

    private int calculateCaloriesToday(EntryList list) {
        // Assert list is not null again for defensive programming within the method
        assert list != null : "EntryList cannot be null for calorie calculation";
        return list.asList().stream()
                .filter(entry -> entry.type().equalsIgnoreCase(EntryType.MEAL.name()))
                .mapToInt(entry -> ((MealEntry) entry).getCalories())
                .sum();
    }

    private int calculateMilkToday(EntryList list, LocalDate today) {
        assert list != null : "EntryList cannot be null for milk calculation";
        assert today != null : "Today's date cannot be null";
        int totalMilk = 0;
        for (Entry entry : list.asList()) {
            if (entry.type().equalsIgnoreCase(EntryType.MILK.name())) {
                MilkEntry milkEntry = (MilkEntry) entry;
                try {
                    LocalDateTime entryDateTime = LocalDateTime.parse(milkEntry.getDate(), DATE_FORMATTER);
                    if (entryDateTime.toLocalDate().isEqual(today)) {
                        totalMilk += MilkEntry.getMilkVol(milkEntry.getMilk());
                    }
                } catch (DateTimeParseException e) {
                    LOGGER.log(Level.WARNING, "Could not parse date for milk entry: "
                            + milkEntry.toListLine(), e);
                    // Skip entries with invalid dates
                } catch (NumberFormatException e) {
                    LOGGER.log(Level.WARNING, "Could not parse milk volume for entry: "
                            + milkEntry.toListLine(), e);
                    // Skip entries with invalid volume
                }
            }
        }
        return totalMilk;
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