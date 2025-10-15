package seedu.mama.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.mama.model.EntryList;
import seedu.mama.model.MilkEntry;
import seedu.mama.model.WeightEntry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class AddMilkCommandTest {
    private EntryList entries;


    @BeforeEach
    public void setUp() {
        entries = new EntryList();
    }

    @Test
    public void execute_validMilk_addsEntryToList() throws CommandException {
        MilkCommand command = new MilkCommand(80);
        String result = command.execute(entries, null);

        assertEquals(1, entries.size());
        assertEquals("80ml", ((MilkEntry) entries.get(0)).getMilk());
        assertTrue(result.contains("Added new weight entry"));
        assertTrue(result.contains("80"));
    }

    @Test
    public void execute_multipleWeight_entriesIncrease() throws CommandException {
        WeightCommand first = new WeightCommand(100);
        WeightCommand second = new WeightCommand(50);
        first.execute(entries, null);
        second.execute(entries, null);

        assertEquals(2, entries.size());
    }
}
