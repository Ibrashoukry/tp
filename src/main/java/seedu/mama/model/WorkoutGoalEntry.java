// src/main/java/seedu/mama/model/WorkoutGoalEntry.java
package seedu.mama.model;

import seedu.mama.util.DateTimeUtil;

import java.time.LocalDateTime;

public final class WorkoutGoalEntry extends TimestampedEntry {
    private final int minutesPerWeek;

    /**
     * New entries created from user input. Timestamp = now().
     */
    public WorkoutGoalEntry(int minutesPerWeek) {
        super("WORKOUT_GOAL", minutesPerWeek + " mins per week");
        if (minutesPerWeek <= 0) {
            throw new IllegalArgumentException("Goal must be positive minutes per week.");
        }
        this.minutesPerWeek = minutesPerWeek;
    }

    /**
     * Deserialization with explicit timestamp.
     */
    public WorkoutGoalEntry(int minutesPerWeek, LocalDateTime when) {
        super("WORKOUT_GOAL", minutesPerWeek + " mins per week", when);
        this.minutesPerWeek = minutesPerWeek;
    }

    public int getMinutesPerWeek() {
        return minutesPerWeek();
    }

    public int minutesPerWeek() {
        return minutesPerWeek;
    }

    public String getDate() {
        return timestampString();
    }

    @Override
    public String toListLine() {
        // [GOAL] 150 mins/week (28/10/25 23:59)
        return "[GOAL] " + minutesPerWeek + " mins/week (" + timestampString() + ")";
    }

    @Override
    public String toStorageString() {
        // WORKOUT_GOAL|150|28/10/25 23:59
        return withTimestamp("WORKOUT_GOAL|" + minutesPerWeek);
    }

    /**
     * STRICT: WORKOUT_GOAL|minutes|timestamp
     */
    public static WorkoutGoalEntry fromStorage(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length != 3 || !"WORKOUT_GOAL".equals(p[0])) {
            throw new IllegalArgumentException("Bad WORKOUT_GOAL line (expect 3 parts): " + line);
        }
        int mins = Integer.parseInt(p[1].trim());
        LocalDateTime ts = DateTimeUtil.parse(p[2].trim());
        return new WorkoutGoalEntry(mins, ts);
    }
}
