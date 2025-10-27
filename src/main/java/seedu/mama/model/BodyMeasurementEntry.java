package seedu.mama.model;

import seedu.mama.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a set of body measurements in centimeters.
 *
 * <p><strong>Storage format (new)</strong>:</p>
 * <pre>
 * MEASURE|<waist>|<hips>|<chest or ->|<thigh or ->|<arm or ->|<dd/MM/yy HH:mm>
 * </pre>
 *
 * <p><strong>Storage format (legacy, still accepted)</strong>:</p>
 * <pre>
 * MEASURE|<waist>|<hips>|<chest or ->|<thigh or ->|<arm or ->
 * </pre>
 */
public class BodyMeasurementEntry extends TimestampedEntry {
    private static final Logger LOG = Logger.getLogger(BodyMeasurementEntry.class.getName());

    private final int waistCm;
    private final int hipsCm;
    private final Integer chestCm; // optional
    private final Integer thighCm; // optional
    private final Integer armCm;   // optional

    /** Public constructor for new entries created by user input (uses now()). */
    public BodyMeasurementEntry(int waistCm, int hipsCm,
                                Integer chestCm, Integer thighCm, Integer armCm) {
        super("MEASURE", "Body measurements");

        if (waistCm <= 0 || hipsCm <= 0) {
            LOG.warning(() -> "Non-positive waist/hips: waist=" + waistCm + ", hips=" + hipsCm);
            throw new IllegalArgumentException("Waist and hips must be positive integers (cm).");
        }
        if (chestCm != null && chestCm <= 0) {
            LOG.warning(() -> "Non-positive chest: " + chestCm);
            throw new IllegalArgumentException("Chest must be positive (cm) if provided.");
        }
        if (thighCm != null && thighCm <= 0) {
            LOG.warning(() -> "Non-positive thigh: " + thighCm);
            throw new IllegalArgumentException("Thigh must be positive (cm) if provided.");
        }
        if (armCm != null && armCm <= 0) {
            LOG.warning(() -> "Non-positive arm: " + armCm);
            throw new IllegalArgumentException("Arm must be positive (cm) if provided.");
        }

        assert waistCm > 0 : "waist must be > 0";
        assert hipsCm > 0 : "hips must be > 0";

        this.waistCm = waistCm;
        this.hipsCm = hipsCm;
        this.chestCm = chestCm;
        this.thighCm = thighCm;
        this.armCm = armCm;

        LOG.fine(() -> String.format("Created BodyMeasurementEntry w=%d h=%d c=%s t=%s a=%s",
                waistCm, hipsCm, v(chestCm), v(thighCm), v(armCm)));
    }

    /** Private constructor for deserialization (restore exact timestamp from file). */
    private BodyMeasurementEntry(int waistCm, int hipsCm,
                                 Integer chestCm, Integer thighCm, Integer armCm,
                                 LocalDateTime when) {
        super("MEASURE", "Body measurements", when);
        this.waistCm = waistCm;
        this.hipsCm = hipsCm;
        this.chestCm = chestCm;
        this.thighCm = thighCm;
        this.armCm = armCm;
    }

    private static String v(Integer x) {
        return x == null ? "-" : String.valueOf(x);
    }

    @Override
    public String toListLine() {
        List<String> parts = new ArrayList<>();
        parts.add("waist=" + waistCm + "cm");
        parts.add("hips=" + hipsCm + "cm");
        if (chestCm != null) parts.add("chest=" + chestCm + "cm");
        if (thighCm != null) parts.add("thigh=" + thighCm + "cm");
        if (armCm != null)   parts.add("arm=" + armCm + "cm");
        return "[MEASURE] " + String.join(", ", parts) + " (" + timestampString() + ")";
    }

    @Override
    public String toStorageString() {
        return withTimestamp(String.join("|", "MEASURE",
                String.valueOf(waistCm), String.valueOf(hipsCm),
                v(chestCm), v(thighCm), v(armCm)));
    }

    public static BodyMeasurementEntry fromStorage(String line) {
        try {
            String[] p = line.split("\\|", -1); // keep empties
            if (p.length < 3) {
                throw new IllegalArgumentException("Bad MEASURE line: " + line);
            }
            int waist = Integer.parseInt(p[1]);
            int hips = Integer.parseInt(p[2]);
            Integer chest = parseNullable(p, 3);
            Integer thigh = parseNullable(p, 4);
            Integer arm = parseNullable(p, 5);

            // New format includes timestamp at index 6.
            if (p.length >= 7 && p[6] != null && !p[6].isEmpty()) {
                LocalDateTime ts = DateTimeUtil.parse(p[6].trim());
                return new BodyMeasurementEntry(waist, hips, chest, thigh, arm, ts);
            }
            // Legacy path (no timestamp)
            return new BodyMeasurementEntry(waist, hips, chest, thigh, arm);
        } catch (RuntimeException ex) {
            Logger.getLogger(BodyMeasurementEntry.class.getName())
                    .log(Level.WARNING, "Failed to parse MEASURE line: " + line, ex);
            throw ex;
        }
    }

    private static Integer parseNullable(String[] parts, int idx) {
        if (parts.length <= idx) return null;
        String t = parts[idx];
        if (t == null || t.isEmpty() || "-".equals(t)) return null;
        return Integer.valueOf(t);
    }
}
