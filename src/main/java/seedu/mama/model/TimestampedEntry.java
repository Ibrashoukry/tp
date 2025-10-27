package seedu.mama.model;

import seedu.mama.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Common base for entries that are created with a timestamp.
 * Unifies date/time handling and formatting.
 */
public abstract class TimestampedEntry extends Entry {
    private final LocalDateTime timestamp;

    protected TimestampedEntry(String type, String description) {
        super(type, description);
        this.timestamp = LocalDateTime.now();
    }

    protected TimestampedEntry(String type, String description, LocalDateTime timestamp) {
        super(type, description);
        this.timestamp = Objects.requireNonNull(timestamp);
    }

    public LocalDateTime timestamp() {
        return timestamp;
    }

    public String timestampString() {
        return DateTimeUtil.format(timestamp);
    }

    /**
     * Helper so subclasses can append "|<timestamp>" consistently in storage.
     */
    protected String withTimestamp(String storagePrefix) {
        return storagePrefix + "|" + DateTimeUtil.format(timestamp);
    }
}
