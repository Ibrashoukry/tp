package seedu.mama.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.mama.model.EntryList;
import seedu.mama.model.NoteEntry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListCommandTest {

    private EntryList entries;

    @BeforeEach
    public void setUp() {
        entries = new EntryList();
    }

    @Test
    public void execute_emptyList_returnsNoEntriesMessage() throws CommandException {
        ListCommand command = new ListCommand();
        String result = command.execute(entries, null);

        assertEquals("No entries found.", result);
    }

    @Test
    public void execute_singleEntry_returnsOneLine() throws CommandException {
        entries.add(new NoteEntry("Buy milk"));
        ListCommand command = new ListCommand();
        String result = command.execute(entries, null);

        assertTrue(result.contains("[NOTE] Buy milk"));
        assertTrue(result.startsWith("1. "));
    }

    @Test
    public void execute_multipleEntries_returnsNumberedList() throws CommandException {
        entries.add(new NoteEntry("Buy milk"));
        entries.add(new NoteEntry("Finish assignment"));

        ListCommand command = new ListCommand();
        String result = command.execute(entries, null);

        String[] lines = result.split(System.lineSeparator());
        assertEquals(2, lines.length);
        assertTrue(lines[0].startsWith("1. "));
        assertTrue(lines[1].startsWith("2. "));
        assertTrue(lines[0].contains("Buy milk"));
        assertTrue(lines[1].contains("Finish assignment"));
    }
}
