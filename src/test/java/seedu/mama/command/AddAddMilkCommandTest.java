package seedu.mama.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.mama.model.EntryList;
import seedu.mama.model.MilkEntry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class AddAddMilkCommandTest {
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
    }

    @Test
    public void execute_multipleMilk_entriesIncrease() throws CommandException {
        AddMilkCommand first = new AddMilkCommand(100);
        AddMilkCommand second = new AddMilkCommand(50);
        first.execute(entries, null);
        second.execute(entries, null);

        assertEquals(2, entries.size());
    }
}
