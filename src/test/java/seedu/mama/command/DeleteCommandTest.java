package seedu.mama.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.mama.model.EntryList;
import seedu.mama.model.WorkoutEntry;
import seedu.mama.storage.Storage;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link DeleteCommand}.
 * Verifies valid deletion, invalid indices, and defensive constructor.
 */
public class DeleteCommandTest {

    private EntryList list;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        list = new EntryList();
        list.add(new WorkoutEntry("Run", 30));
        list.add(new WorkoutEntry("Swim", 30));
        list.add(new WorkoutEntry("Cycle", 30));

        storage = new Storage(Path.of("build", "test-storage.txt"));
    }

    @Test
    public void execute_validIndex_deletesCorrectEntry() throws CommandException {
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
    public void execute_invalidIndex_throwsCommandException() {
        DeleteCommand cmd = new DeleteCommand(5); // out of bounds

        CommandException ex = assertThrows(
                CommandException.class,
                () -> cmd.execute(list, storage)
        );

        assertTrue(ex.getMessage().startsWith("Error: Invalid index: 5"));
        assertTrue(ex.getMessage().contains("Here are your entries:"));
        assertEquals(3, list.size(), "List should remain unchanged");
    }

    @Test
    public void constructor_illegalIndex_throwsIAE() {
        assertThrows(IllegalArgumentException.class, () -> new DeleteCommand(-2));
        assertThrows(IllegalArgumentException.class, () -> new DeleteCommand(0));
    }
}
