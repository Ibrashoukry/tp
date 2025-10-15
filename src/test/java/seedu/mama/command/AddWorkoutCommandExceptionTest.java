package seedu.mama.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddWorkoutCommandExceptionTest {

    @Test
    public void fromInput_extraTokensAfterDuration_throws() {
        CommandException ex = assertThrows(
                CommandException.class,
                () -> AddWorkoutCommand.fromInput("workout run /dur 50 50")
        );
        assertTrue(ex.getMessage().toLowerCase().contains("unexpected input after duration"));
    }

    @Test
    public void fromInput_missingDurSegment_throws() {
        CommandException ex = assertThrows(
                CommandException.class,
                () -> AddWorkoutCommand.fromInput("workout run")
        );
        assertTrue(ex.getMessage().toLowerCase().contains("missing '/dur' segment"));
    }

    @Test
    public void fromInput_missingType_throws() {
        CommandException ex = assertThrows(
                CommandException.class,
                () -> AddWorkoutCommand.fromInput("workout /dur 50")
        );
        assertTrue(ex.getMessage().toLowerCase().contains("workout type cannot be empty"));
    }

    @Test
    public void fromInput_multipleDurSegments_throws() {
        CommandException ex = assertThrows(
                CommandException.class,
                () -> AddWorkoutCommand.fromInput("workout run /dur 50 /dur 40")
        );
        assertTrue(ex.getMessage().toLowerCase().contains("too many '/dur' segments"));
    }

    @Test
    public void fromInput_nonNumericDuration_throws() {
        CommandException ex = assertThrows(
                CommandException.class,
                () -> AddWorkoutCommand.fromInput("workout run /dur forty")
        );
        assertTrue(ex.getMessage().toLowerCase().contains("duration must be a whole number"));
    }

    @Test
    public void fromInput_missingDurationToken_throws() {
        // "/dur /dur" should be caught as multiple '/dur' segments first
        CommandException ex = assertThrows(
                CommandException.class,
                () -> AddWorkoutCommand.fromInput("workout run /dur /dur")
        );
        // Depending on your implementation order it could be either message below.
        String msg = ex.getMessage().toLowerCase();
        boolean matchesMultipleDur = msg.contains("too many '/dur' segments");
        boolean matchesMissingDuration = msg.contains("missing duration")
                || msg.contains("whole number");
        assertTrue(matchesMultipleDur || matchesMissingDuration,
                "Expected message about multiple '/dur' or missing/invalid duration, but was: " + ex.getMessage());
    }
}
