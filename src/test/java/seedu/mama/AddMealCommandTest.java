 package seedu.mama;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.mama.command.AddMealCommand;
import seedu.mama.model.EntryList;
import seedu.mama.model.MealEntry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddMealCommandTest {

    private EntryList entries;

    @BeforeEach
    public void setUp() {
        entries = new EntryList();
    }

    @Test
    public void execute_validMeal_addsEntryToList() {
        AddMealCommand command = new AddMealCommand("breakfast", 350);
        String result = command.execute(entries, null);

        assertEquals(1, entries.size());
        assertEquals("breakfast", ((MealEntry) entries.get(0)).description());
        assertEquals(350, ((MealEntry) entries.get(0)).getCalories());
        assertTrue(result.toLowerCase().contains("added"));
    }

    @Test
    public void execute_multipleMeals_entriesIncrease() {
        AddMealCommand first = new AddMealCommand("lunch", 600);
        AddMealCommand second = new AddMealCommand("snack", 200);
        first.execute(entries, null);
        second.execute(entries, null);

        assertEquals(2, entries.size());
    }
}
