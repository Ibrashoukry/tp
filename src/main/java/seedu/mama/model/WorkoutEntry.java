package seedu.mama.model;

public final class WorkoutEntry extends Entry {
    private final int duration; // minutes

    public WorkoutEntry(String workoutType, int duration) {
        super("WORKOUT", workoutType.trim());
        this.duration = duration;
    }

    public int duration() {
        return duration;
    }

    @Override
    public String toListLine() {
        return "[Workout] " + description() + " (" + duration + " mins)";
    }

    @Override
    public String toStorageString() {
        // Stable storage: WORKOUT|<type>|<duration>
        return "WORKOUT|" + description() + "|" + duration;
    }

    public static WorkoutEntry fromStorage(String line) {
        // Expected: WORKOUT|<type>|<duration>
        String[] parts = line.split("\\|");
        String type = parts.length > 1 ? parts[1] : "";
        int dur = parts.length > 2 ? Integer.parseInt(parts[2]) : 0;
        return new WorkoutEntry(type, dur);
    }
}
