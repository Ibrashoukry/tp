package seedu.mama.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.mama.model.EntryList;
import seedu.mama.model.MilkEntry;

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
        AddMilkCommand command = new AddMilkCommand(80);
        String result = command.execute(entries, null);

        assertEquals(1, entries.size());
        assertEquals("80ml", ((MilkEntry) entries.get(0)).getMilk());
        assertTrue(result.contains("Breast Milk Pumped:"));
        assertTrue(result.contains("80ml"));

        AddMilkCommand command2 = new AddMilkCommand(70);
        String result2 = command2.execute(entries, null);
        assertEquals(2, entries.size());
        assertEquals("70ml", ((MilkEntry) entries.get(1)).getMilk());
        assertTrue(result2.contains("Total breast milk pumped: 150ml"));
    }

    @Test
    public void execute_multipleMilk_entriesIncrease() throws CommandException {
        AddMilkCommand first = new AddMilkCommand(100);
        AddMilkCommand second = new AddMilkCommand(30);
        first.execute(entries, null);

        String result2 = second.execute(entries, null);
        assertEquals(2, entries.size());
    }
}
