// src/main/java/seedu/mama/model/WorkoutGoalQueries.java
package seedu.mama.model;

import seedu.mama.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Helper methods for querying workout goals and workouts.
 */
public final class WorkoutGoalQueries {
    private WorkoutGoalQueries() {
    }

    /**
     * Sum workout minutes within [weekStart, weekStart+7d).
     */
    public static int sumWorkoutMinutesThisWeek(List<Entry> all, LocalDateTime weekStart) {
        int sum = 0;
        for (Entry e : all) {
            if (e instanceof WorkoutEntry we) {
                LocalDateTime ts = ((TimestampedEntry) we).timestamp();
                if (DateTimeUtil.inSameWeek(ts, weekStart)) {
                    sum += we.getDuration();
                }
            }
        }
        return sum;
    }

    /**
     * Returns the workout goal set within this week only.
     * <p>
     * If multiple goals exist this week, return the latest one.
     * Returns null if no goal was set during this week.
     */
    public static WorkoutGoalEntry currentWeekGoal(List<Entry> all, LocalDateTime weekStart) {
        WorkoutGoalEntry latestThisWeek = null;

        for (Entry e : all) {
            if (e instanceof WorkoutGoalEntry g) {
                LocalDateTime ts = ((TimestampedEntry) g).timestamp();
                if (DateTimeUtil.inSameWeek(ts, weekStart)) {
                    if (latestThisWeek == null || ts.isAfter(((TimestampedEntry) latestThisWeek).timestamp())) {
                        latestThisWeek = g;
                    }
                }
            }
        }
        return latestThisWeek;
    }
}
