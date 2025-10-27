package seedu.mama.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateTimeUtil {
    private DateTimeUtil() {
    }

    public static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");

    public static String format(LocalDateTime dt) {
        return dt.format(FMT);
    }

    public static LocalDateTime parse(String s) {
        return LocalDateTime.parse(s, FMT);
    }
}
