package seedu.mama.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.mama.model.EntryList;
import seedu.mama.model.WeightEntry;

public class AddWeightCommandTest {
    private EntryList entries;
    // Define a small delta for comparing double values due to floating-point arithmetic
    private static final double DELTA = 0.0001;


    @BeforeEach
    public void setUp() {
        entries = new EntryList();
    }

    @Test
    public void execute_validWeight_addsEntryToList() throws CommandException {
        // Test a decimal input to confirm rounding/precision works as expected
        AddWeightCommand command = new AddWeightCommand(80.123);
        CommandResult result = command.execute(entries, null);

        // 1. Check size
        assertEquals(1, entries.size());

        // 2. Check the raw weight value returned by getWeight()
        //    It should be the rounded value: 80.12
        double actualWeight = ((WeightEntry) entries.get(0)).getWeight();
        assertEquals(80.12, actualWeight, DELTA);

        // 3. Check the feedback message (formatted output)
        assertTrue(result.getFeedbackToUser().contains("Added new weight entry"));
        // The output string will be "80.12kg" based on WeightEntry's formatting
        assertTrue(result.getFeedbackToUser().contains("80.12kg"));
    }

    @Test
    public void execute_multipleWeight_entriesIncrease() throws CommandException {
        // Test two simple inputs
        AddWeightCommand first = new AddWeightCommand(100.0);
        AddWeightCommand second = new AddWeightCommand(50.5);
        first.execute(entries, null);
        second.execute(entries, null);

        // Check if both entries were added
        assertEquals(2, entries.size());

        // Optional: Verify the values of the added entries
        assertEquals(100.00, ((WeightEntry) entries.get(0)).getWeight(), DELTA);
        assertEquals(50.50, ((WeightEntry) entries.get(1)).getWeight(), DELTA);
    }

    @Test
    public void execute_edgeCaseRounding_correctlyRoundsUp() throws CommandException {
        // Test an input that should round up: 80.125 -> 80.13
        AddWeightCommand command = new AddWeightCommand(80.125);
        command.execute(entries, null);

        // The internal value should be 80.13
        double actualWeight = ((WeightEntry) entries.get(0)).getWeight();
        assertEquals(80.13, actualWeight, DELTA);

        // The output should display 80.13
        assertTrue(command.execute(entries, null).getFeedbackToUser().contains("80.13kg"));
    }
}