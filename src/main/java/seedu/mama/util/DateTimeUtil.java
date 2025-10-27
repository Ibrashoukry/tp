package seedu.mama.util;

import java.time.*;
import java.time.format.DateTimeFormatter;

public final class DateTimeUtil {
    private DateTimeUtil() {}

    // One source of truth for your dd/MM/yy HH:mm format
    public static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");

    public static String format(LocalDateTime dt) { return dt.format(FMT); }
    public static LocalDateTime parse(String s) { return LocalDateTime.parse(s, FMT); }

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

    /** Asia/Singapore clock for consistency (handy in tests too). */
    public static Clock sgClock() {
        return Clock.system(ZoneId.of("Asia/Singapore"));
    }
}
