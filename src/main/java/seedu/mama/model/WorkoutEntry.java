package seedu.mama.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class WorkoutEntry extends Entry {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");

    private final int duration; // minutes
    private final String date;

    // New constructor (auto timestamp)
    public WorkoutEntry(String workoutType, int duration) {
        super("WORKOUT", workoutType.trim());
        this.duration = duration;
        this.date = LocalDateTime.now().format(FMT);
    }

    // For storage/tests with explicit timestamp
    public WorkoutEntry(String workoutType, int duration, LocalDateTime when) {
        super("WORKOUT", workoutType.trim());
        this.duration = duration;
        this.date = when.format(FMT);
    }

    public int getDuration() {
        return duration;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toListLine() {
        return "[Workout] " + description() + " (" + duration + " mins) (" + date + ")";
    }

    @Override
    public String toStorageString() {
        // Stable storage: WORKOUT|<type>|<duration>|<date>
        return "WORKOUT|" + description() + "|" + duration + "|" + date;
    }

    public static WorkoutEntry fromStorage(String line) {
        String[] parts = line.split("\\|", 4);
        if (!"WORKOUT".equals(parts[0])) {
            throw new IllegalArgumentException("Bad workout line: " + line);
        }
        String type = parts[1];
        int dur = Integer.parseInt(parts[2]);
        if (parts.length >= 4) {
            LocalDateTime when = LocalDateTime.parse(parts[3], FMT);
            return new WorkoutEntry(type, dur, when);
        }
        // legacy line (no timestamp) → stamp “now” so it joins the current week
        return new WorkoutEntry(type, dur);
    }
}
