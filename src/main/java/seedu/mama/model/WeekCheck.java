package seedu.mama.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

public final class WeekCheck {
    private WeekCheck() {}

    /** Monday 00:00 of the week that contains `dt`. */
    public static LocalDateTime weekStartMonday(LocalDateTime dt) {
        LocalDate d = dt.toLocalDate();
        LocalDate monday = d.with(DayOfWeek.MONDAY);
        return monday.atStartOfDay();
    }

    /** True if `t` is in [weekStart, weekStart+7d). */
    public static boolean inSameWeek(LocalDateTime t, LocalDateTime weekStart) {
        LocalDateTime weekEnd = weekStart.plusDays(7);
        return !t.isBefore(weekStart) && t.isBefore(weekEnd);
    }
}
