package seedu.mama.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class WorkoutGoalEntry extends Entry {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");

    private final int minutesPerWeek;
    private final String date; // formatted timestamp

    public WorkoutGoalEntry(int minutesPerWeek) {
        super("WORKOUT_GOAL", minutesPerWeek + " mins per week");
        this.minutesPerWeek = minutesPerWeek;
        this.date = LocalDateTime.now().format(FMT);
    }

    public WorkoutGoalEntry(int minutesPerWeek, LocalDateTime setAt) {
        super("WORKOUT_GOAL", minutesPerWeek + " mins per week");
        this.minutesPerWeek = minutesPerWeek;
        this.date = setAt.format(FMT);
    }

    public int getMinutesPerWeek() { return minutesPerWeek; }
    public String getDate() { return date; }

    @Override
    public String toListLine() {
        return "[" + type() + "] " + description() + " (" + date + ")";
    }

    @Override
    public String toStorageString() {
        // WORKOUT_GOAL|<minutes>|<date>
        return "WORKOUT_GOAL|" + minutesPerWeek + "|" + date;
    }

    public static WorkoutGoalEntry fromStorage(String line) {
        String[] parts = line.split("\\|", 3);
        if (parts.length < 2) {
            throw new IllegalArgumentException("Bad workout goal line: " + line);
        }
        int mins = Integer.parseInt(parts[1].trim());
        if (parts.length == 3) {
            LocalDateTime ts = LocalDateTime.parse(parts[2].trim(), FMT);
            return new WorkoutGoalEntry(mins, ts);
        }
        return new WorkoutGoalEntry(mins);
    }
}
