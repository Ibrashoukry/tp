package seedu.mama.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class WorkoutGoalQueries {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
    private WorkoutGoalQueries() {}

    /** Latest WORKOUT_GOAL set within [weekStart, weekStart+7d). Returns null if none. */
    public static WorkoutGoalEntry currentWeekGoal(EntryList list, LocalDateTime weekStart) {
        WorkoutGoalEntry latest = null;
        LocalDateTime latestTs = null;

        for (Entry e : list.asList()) {
            if (!"WORKOUT_GOAL".equals(e.type())) {
                continue;
            }
            WorkoutGoalEntry g = (WorkoutGoalEntry) e;
            LocalDateTime ts = LocalDateTime.parse(g.getDate(), FMT);
            if (WeekCheck.inSameWeek(ts, weekStart)) {
                if (latest == null || ts.isAfter(latestTs)) {
                    latest = g;
                    latestTs = ts;
                }
            }
        }
        return latest;
    }

    /** Sum of WORKOUT minutes within [weekStart, weekStart+7d). */
    public static int sumWorkoutMinutesThisWeek(EntryList list, LocalDateTime weekStart) {
        int sum = 0;
        for (Entry e : list.asList()) {
            if (!"WORKOUT".equals(e.type())) {
                continue;
            }
            WorkoutEntry w = (WorkoutEntry) e;
            LocalDateTime ts = LocalDateTime.parse(w.getDate(), FMT);
            if (WeekCheck.inSameWeek(ts, weekStart)) {
                sum += w.getDuration();
            }
        }
        return sum;
    }
}
