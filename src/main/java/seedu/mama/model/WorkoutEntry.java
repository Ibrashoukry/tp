package seedu.mama.model;

import seedu.mama.util.DateTimeUtil;

import java.time.LocalDateTime;

/**
 * Represents a workout entry — e.g., "running (40 mins)" with timestamp.
 */
public final class WorkoutEntry extends TimestampedEntry {

    private final String workoutType;   // e.g., "running", "yoga"
    private final int durationMins;     // duration in minutes

    /**
     * Constructor for new user-created workouts (timestamp = now()).
     */
    public WorkoutEntry(String workoutType, int durationMins) {
        super("WORKOUT", workoutType.trim());
        if (durationMins <= 0) {
            throw new IllegalArgumentException("Workout duration must be positive minutes.");
        }
        this.workoutType = workoutType.trim();
        this.durationMins = durationMins;
    }

    /**
     * Constructor used when reading from storage (explicit timestamp).
     */
    public WorkoutEntry(String workoutType, int durationMins, LocalDateTime when) {
        super("WORKOUT", workoutType.trim(), when);
        this.workoutType = workoutType.trim();
        this.durationMins = durationMins;
    }

    public String getWorkoutType() {
        return workoutType;
    }

    public int getDuration() {
        return durationMins;
    }

    /**
     * Display line shown to the user.
     * Must start exactly like "[Workout] running (40 mins)" to satisfy tests.
     */
    @Override
    public String toListLine() {
        return "[Workout] " + workoutType + " (" + durationMins + " mins)" + " (" + timestampString() + ")";
    }

    /**
     * Storage format: WORKOUT|type|duration|timestamp
     */
    @Override
    public String toStorageString() {
        return withTimestamp("WORKOUT|" + workoutType + "|" + durationMins);
    }

    public static WorkoutEntry fromStorage(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length != 4 || !"WORKOUT".equals(parts[0])) {
            throw new IllegalArgumentException("Bad WORKOUT line (expect 4 parts): " + line);
        }

        String type = parts[1].trim();
        int mins = Integer.parseInt(parts[2].trim());
        LocalDateTime ts = DateTimeUtil.parse(parts[3].trim());
        return new WorkoutEntry(type, mins, ts);
    }
}
