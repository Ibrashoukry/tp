package seedu.mama.command;

import seedu.mama.model.EntryList;
import seedu.mama.model.MealEntry;
import seedu.mama.storage.Storage;

public class AddMealCommand implements Command {
    private String mealType;
    private int calories;

    public AddMealCommand(String mealType, int calories) {
        this.mealType = mealType;
        this.calories = calories;
    }

    public static AddMealCommand fromInput(String input) {
        // Error handling when invalid format to be added
        String desc = input.substring("meal".length()).trim();
        String[] parsedDesc = desc.split("/cal");
        String calStr = parsedDesc[1].trim().split("\\s+")[0];
        int calories = Integer.parseInt(calStr);
        return new AddMealCommand(parsedDesc[0].trim(), calories);
    }

    @Override
    public String execute(EntryList list, Storage storage) {
        MealEntry entry = new MealEntry(mealType, calories);
        list.add(entry);
        storage.save(list);
        int totalCal = list.asList().stream() // gets total calorie count
                .filter(e -> e.type().equals("MEAL"))
                .mapToInt(e -> ((MealEntry) e).getCalories())
                .sum();
        return "Got it. I've logged this meal:\n"
                + "  " + entry.toListLine() + "\n"
                + "You now have a total of " + totalCal
                + " calories recorded! Keep up the good work!";
    }
}
