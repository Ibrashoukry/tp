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

        CommandResult output = cmd.execute(list, storage);

        assertEquals(2, list.size());
        assertFalse(output.getFeedbackToUser().isEmpty());
        assertTrue(output.getFeedbackToUser().startsWith("Deleted:"));
        assertTrue(output.getFeedbackToUser().contains("[Workout] Swim (30 mins)"));

        String first = list.get(0).toListLine();
        String second = list.get(1).toListLine();
        assertTrue(first.startsWith("[Workout] Run (30 mins)"), first);
        assertTrue(second.startsWith("[Workout] Cycle (30 mins)"), second);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        DeleteCommand cmd = new DeleteCommand(5); // out of bounds

        CommandException ex = assertThrows(
                CommandException.class,
                () -> cmd.execute(list, storage)
        );

        assertTrue(ex.getMessage().startsWith("Invalid delete index (shown view): 5"));
        assertTrue(ex.getMessage().contains("Here are your entries:"));
        assertEquals(3, list.size(), "List should remain unchanged");
    }

    @Test
    public void constructor_illegalIndex_throwsIAE() {
        assertThrows(IllegalArgumentException.class, () -> new DeleteCommand(-2));
        assertThrows(IllegalArgumentException.class, () -> new DeleteCommand(0));
    }
}
