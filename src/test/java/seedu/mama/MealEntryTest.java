package seedu.mama;

import org.junit.jupiter.api.Test;
import seedu.mama.model.MealEntry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MealEntryTest {

    @Test
    public void constructor_validInput_fieldsInitializedCorrectly() {
        MealEntry entry = new MealEntry("lunch", 600);
        assertEquals("lunch", entry.description());
        assertEquals(600, entry.getCalories());
    }

    @Test
    public void toListLine_returnsFormattedString() {
        MealEntry entry = new MealEntry("dinner", 800);
        String result = entry.toListLine();
        assertTrue(result.contains("dinner"));
        assertTrue(result.contains("800"));
    }
}
