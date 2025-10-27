// src/main/java/seedu/mama/model/WorkoutGoalQueries.java
package seedu.mama.model;

import seedu.mama.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.util.List;

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
                    sum += we.duration();
                }
            }
        }
        return sum;
    }

    /**
     * The goal in effect for this week:
     * - pick the latest goal whose timestamp is within this week; else
     * - pick the latest goal strictly before weekStart (carry-forward).
     * Returns null if no goal exists.
     */
    public static WorkoutGoalEntry currentWeekGoal(List<Entry> all, LocalDateTime weekStart) {
        WorkoutGoalEntry bestInWeek = null;
        WorkoutGoalEntry latestBefore = null;

        for (Entry e : all) {
            if (e instanceof WorkoutGoalEntry g) {
                LocalDateTime ts = ((TimestampedEntry) g).timestamp();
                if (DateTimeUtil.inSameWeek(ts, weekStart)) {
                    if (bestInWeek == null ||
                            ts.isAfter(((TimestampedEntry) bestInWeek).timestamp())) {
                        bestInWeek = g;
                    }
                } else if (ts.isBefore(weekStart)) {
                    if (latestBefore == null ||
                            ts.isAfter(((TimestampedEntry) latestBefore).timestamp())) {
                        latestBefore = g;
                    }
                }
            }
        }
        return (bestInWeek != null) ? bestInWeek : latestBefore;
    }
}
