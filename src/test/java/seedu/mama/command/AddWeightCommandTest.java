package seedu.mama.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.mama.model.EntryList;
import seedu.mama.model.WeightEntry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class AddWeightCommandTest {
    private EntryList entries;


    @BeforeEach
    public void setUp() {
        entries = new EntryList();
    }

    @Test
    public void execute_validMeal_addsEntryToList() {
        WeightCommand command = new WeightCommand(80);
        String result = command.execute(entries, null);

        assertEquals(1, entries.size());
        assertEquals("80kg", ((WeightEntry) entries.get(0)).getWeight());
        assertTrue(result.contains("Added new weight entry"));
        assertTrue(result.contains("80"));
    }

    @Test
    public void execute_multipleMeals_entriesIncrease() {
        WeightCommand first = new WeightCommand(100);
        WeightCommand second = new WeightCommand(50);
        first.execute(entries, null);
        second.execute(entries, null);

        assertEquals(2, entries.size());
    }
}
