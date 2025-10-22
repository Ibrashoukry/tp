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

    public static AddMealCommand fromInput(String input) throws CommandException {
        String desc = input.substring("meal".length()).trim();

        if (!desc.contains("/cal")) {
            throw new CommandException("Invalid format! Try: meal <mealType> /cal <calories>");
        }

        String[] parsedDesc = desc.split("/cal");
        if (parsedDesc.length < 2) {
            throw new CommandException("Invalid format! Try: meal <mealType> /cal <calories>");
        }

        String mealType = parsedDesc[0].trim();
        String calStr = parsedDesc[1].trim().split("\\s+")[0];

        int calories;
        try {
            calories = Integer.parseInt(calStr);
        } catch (NumberFormatException e) {
            throw new CommandException("Calories must be a number.");
        }

        return new AddMealCommand(mealType, calories);
    }

    @Override
    public CommandResult execute(EntryList list, Storage storage) {
        MealEntry entry = new MealEntry(mealType, calories);
        list.add(entry);
        if (storage != null) {
            storage.save(list);
        }
        int totalCal = list.asList().stream() // gets total calorie count
                .filter(e -> e.type().equals("MEAL"))
                .mapToInt(e -> ((MealEntry) e).getCalories())
                .sum();
        return new CommandResult("Got it. I've logged this meal:\n"
                + "  " + entry.toListLine() + "\n"
                + "You now have a total of " + totalCal
                + " calories recorded! Keep up the good work!");
    }
}
