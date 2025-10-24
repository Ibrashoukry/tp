package seedu.mama.storage;

import org.junit.jupiter.api.Test;

import seedu.mama.model.BodyMeasurementEntry;
import seedu.mama.model.Entry;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageRoundTripMeasurementTest {

    @Test
    void roundTrip_withOptionals_ok() {
        Entry e = new BodyMeasurementEntry(75, 95, 88, null, 30);
        String line = e.toStorageString();
        assertEquals("MEASURE|75|95|88|-|30", line);

        Entry back = BodyMeasurementEntry.fromStorage(line);
        assertEquals(e.toListLine(), back.toListLine());
    }

    @Test
    void roundTrip_minimal_ok() {
        Entry e = new BodyMeasurementEntry(80, 100, null, null, null);
        String line = e.toStorageString();
        assertEquals("MEASURE|80|100|-|-|-", line);

        Entry back = BodyMeasurementEntry.fromStorage(line);
        assertEquals(e.toListLine(), back.toListLine());
    }
}
