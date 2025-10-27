// src/main/java/seedu/mama/model/WorkoutEntry.java
package seedu.mama.model;

import seedu.mama.util.DateTimeUtil;

import java.time.LocalDateTime;

/**
 * Storage (strict):
 * WORKOUT|<type>|<durationMins>|<dd/MM/yy HH:mm>
 */
public final class WorkoutEntry extends TimestampedEntry {
    private final String workoutType;   // e.g., "run", "yoga"
    private final int durationMins;     // minutes

    /**
     * New entries created from user input. Timestamp = now().
     */
    public WorkoutEntry(String workoutType, int durationMins) {
        super("WORKOUT", workoutType.trim());
        this.workoutType = workoutType.trim();
        this.durationMins = durationMins;
        if (durationMins <= 0) {
            throw new IllegalArgumentException("Workout duration must be positive minutes.");
        }
    }

    /**
     * Deserialization with explicit timestamp.
     */
    public WorkoutEntry(String workoutType, int durationMins, LocalDateTime when) {
        super("WORKOUT", workoutType.trim(), when);
        this.workoutType = workoutType.trim();
        this.durationMins = durationMins;
    }

    public int duration() {
        return durationMins;
    }

    public int getDuration() {
        return durationMins;
    }


    /**
     * Keep for any existing code that printed a date string.
     */
    public String getDate() {
        return timestampString();
    }

    @Override
    public String toListLine() {
        // [WORKOUT] run - 30 mins (28/10/25 23:59)
        return "[WORKOUT] " + workoutType + " - " + durationMins + " mins (" + timestampString() + ")";
    }

    @Override
    public String toStorageString() {
        // WORKOUT|run|30|28/10/25 23:59
        return withTimestamp("WORKOUT|" + workoutType + "|" + durationMins);
    }

    /**
     * STRICT: WORKOUT|type|duration|timestamp
     */
    public static WorkoutEntry fromStorage(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length != 4 || !"WORKOUT".equals(p[0])) {
            throw new IllegalArgumentException("Bad WORKOUT line (expect 4 parts): " + line);
        }
        String type = p[1];
        int mins = Integer.parseInt(p[2].trim());
        LocalDateTime ts = DateTimeUtil.parse(p[3].trim());
        return new WorkoutEntry(type, mins, ts);
    }
}
