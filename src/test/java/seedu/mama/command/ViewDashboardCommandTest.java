package seedu.mama.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.mama.model.EntryList;
import seedu.mama.model.MealEntry;
import seedu.mama.model.MilkEntry;
import seedu.mama.model.WorkoutEntry;
import seedu.mama.model.WorkoutGoalEntry;
import seedu.mama.storage.Storage;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ViewDashboardCommandTest {

    private EntryList entries;
    private Storage storageStub;

    // A stub to simulate storage without file I/O.
    private static class StorageStub extends Storage {
        private Integer calorieGoal = null;
        public StorageStub() {
            super(null);
        }

        @Override
        public Integer loadGoal() {
            return calorieGoal;
        }

        public void setCalorieGoal(int goal) {
            this.calorieGoal = goal;
        }
    }

    @BeforeEach
    public void setUp() {
        entries = new EntryList();
        storageStub = new StorageStub();
    }

    @Test
    public void execute_dashboardWithNoData_showsEmptyStateMessages() throws CommandException {
        ViewDashboardCommand command = new ViewDashboardCommand();
        CommandResult result = command.execute(entries, storageStub);
        String output = result.getFeedbackToUser();

        assertTrue(output.contains("Calories Consumed: 0 kcal"));
        assertTrue(output.contains("No calorie goal set."));
        assertTrue(output.contains("Total Pumped: 0 ml"));
        assertTrue(output.contains("Workout Minutes: 0 mins"));
        assertTrue(output.contains("No weekly workout goal set."));
    }

    @Test
    public void execute_dashboardWithMixedData_calculatesCorrectly() throws CommandException {
        // Arrange: Add data for today and other days
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime yesterday = today.minusDays(1);
        LocalDateTime lastWeek = today.minusWeeks(1);

        // Today's data (750 kcal, 270 ml, 75 mins)
        entries.add(new MealEntry("Lunch", 500, null, null, null, today));
        entries.add(new MealEntry("Snack", 250, null, null, null, today));
        entries.add(new MilkEntry("120ml", today));
        entries.add(new MilkEntry("150ml", today));
        entries.add(new WorkoutEntry("Run", 30, 4, today));
        entries.add(new WorkoutEntry("Yoga", 45, 5, today));

        // Data from other days (should be ignored by the dashboard)
        entries.add(new MealEntry("Old Dinner", 800, null, null, null, yesterday));
        entries.add(new MilkEntry("100ml", yesterday));
        entries.add(new WorkoutEntry("Old Run", 60, 3, lastWeek));

        // Set goals
        ((StorageStub) storageStub).setCalorieGoal(2000);
        LocalDateTime startOfWeek = today.with(DayOfWeek.MONDAY);
        entries.add(new WorkoutGoalEntry(200, startOfWeek));

        // Act
        ViewDashboardCommand command = new ViewDashboardCommand();
        CommandResult result = command.execute(entries, storageStub);
        String output = result.getFeedbackToUser();

        // Assert
        assertTrue(output.contains("Calories Consumed: 750 kcal"), "Calories should be 750 for today.");
        assertTrue(output.contains("Calorie Goal:      2000 kcal"));
        assertTrue(output.contains("Remaining:         1250 kcal"));
        assertTrue(output.contains("Total Pumped: 270 ml"), "Milk should be 120 + 150 = 270.");
        assertTrue(output.contains("Workout Minutes: 75 mins"), "Workout minutes should be 30 + 45 = 75.");
        assertTrue(output.contains("Weekly Goal:     200 mins"));
        assertTrue(output.contains("Remaining:         125 mins"));
    }
    @Test
    public void execute_dashboardWithMultipleWorkoutGoals_usesLatestOne() throws CommandException {
        // Arrange: Set two goals this week
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime startOfWeek = today.with(DayOfWeek.MONDAY);
        entries.add(new WorkoutGoalEntry(150, startOfWeek.plusHours(1))); // Earlier goal
        entries.add(new WorkoutGoalEntry(300, startOfWeek.plusHours(5))); // Latest goal

        entries.add(new WorkoutEntry("Cycling", 50, 4, today));

        // Act
        ViewDashboardCommand command = new ViewDashboardCommand();
        CommandResult result = command.execute(entries, storageStub);
        String output = result.getFeedbackToUser();

        // Assert that the latest goal (300) is used
        assertTrue(output.contains("Weekly Goal:     300 mins"));
        assertTrue(output.contains("Workout Minutes: 50 mins"));
        assertTrue(output.contains("Remaining:         250 mins"));
    }
}
