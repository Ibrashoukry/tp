package seedu.mama.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.mama.model.EntryList;
import seedu.mama.model.WorkoutEntry;
import seedu.mama.storage.Storage;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteCommandTest {

    private EntryList list;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        list = new EntryList();
        list.add(new WorkoutEntry("Run", 30));     // index 1
        list.add(new WorkoutEntry("Swim", 30));    // index 2
        list.add(new WorkoutEntry("Cycle", 30));  // index 3

        storage = new Storage(Path.of("build", "test-storage.txt"));
    }

    @Test
    public void execute_previewMode_returnsFormattedList() {
        DeleteCommand cmd = new DeleteCommand(-1);
        String result = cmd.execute(list, storage);

        assertTrue(result.startsWith("Here are your entries:"));
        assertTrue(result.contains("1. [Workout] Run (30 mins)"));
        assertTrue(result.contains("2. [Workout] Swim (30 mins)"));
        assertTrue(result.contains("3. [Workout] Cycle (30 mins)"));
    }

    @Test
    public void execute_validIndex_deletesCorrectEntry() {
        DeleteCommand cmd = new DeleteCommand(2);

        String output = cmd.execute(list, storage);

        assertEquals(2, list.size());
        assertFalse(output.isEmpty());
        assertTrue(output.startsWith("Deleted:"));
        assertTrue(output.contains("[Workout] Swim (30 mins)"));
        assertEquals("[Workout] Run (30 mins)", list.get(0).toListLine());
        assertEquals("[Workout] Cycle (30 mins)", list.get(1).toListLine());
    }

    @Test
    public void execute_invalidIndex_returnsErrorMessage() {
        DeleteCommand cmd = new DeleteCommand(5); // out of bounds

        String output = cmd.execute(list, storage);

        assertTrue(output.startsWith("Invalid index:"));
        assertTrue(output.contains("Here are your entries:"));
        assertEquals(3, list.size(), "List should remain unchanged");
    }

    @Test
    public void execute_emptyList_previewShowsNoEntries() {
        EntryList emptyList = new EntryList();
        DeleteCommand cmd = new DeleteCommand(-1);

        String result = cmd.execute(emptyList, storage);

        assertEquals("No entries yet.", result);
    }

    @Test
    public void execute_zeroIndex_returnsInvalidMessage() {
        DeleteCommand cmd = new DeleteCommand(0);
        String output = cmd.execute(list, storage);

        assertTrue(output.startsWith("Invalid index: 0"));
    }
}
