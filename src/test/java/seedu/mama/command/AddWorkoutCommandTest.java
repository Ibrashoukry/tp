package seedu.mama.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.mama.model.Entry;
import seedu.mama.model.EntryList;
import seedu.mama.model.WorkoutEntry;
import seedu.mama.storage.Storage;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddWorkoutCommandTest {

    private EntryList entries;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        entries = new EntryList();
        storage = new Storage(Path.of("build", "test-storage-workout.txt"));
    }

    @Test
    public void execute_validWorkout_addsEntryToList() throws CommandException {
        AddWorkoutCommand command = new AddWorkoutCommand("running", 40);

        CommandResult result = command.execute(entries, storage);

        assertEquals(1, entries.size(), "One workout entry should be added");
        Entry first = entries.get(0);
        assertTrue(first instanceof WorkoutEntry, "Entry should be a WorkoutEntry");
        assertEquals("[Workout] running (40 mins)", first.toListLine());
        assertTrue(result.getFeedbackToUser().toLowerCase().contains("logged")
                        || result.getFeedbackToUser().toLowerCase().contains("added"),
                "Result should confirm the workout was recorded");
    }

    @Test
    public void execute_multipleWorkouts_entriesIncrease() throws CommandException {
        AddWorkoutCommand first = new AddWorkoutCommand("yoga", 60);
        AddWorkoutCommand second = new AddWorkoutCommand("cycling", 30);

        first.execute(entries, storage);
        second.execute(entries, storage);

        assertEquals(2, entries.size(), "Two workout entries should be present");
        assertEquals("[Workout] yoga (60 mins)", entries.get(0).toListLine());
        assertEquals("[Workout] cycling (30 mins)", entries.get(1).toListLine());
    }
}
