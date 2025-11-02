package seedu.mama.command;

import seedu.mama.model.EntryList;
import seedu.mama.model.MealEntry;
import seedu.mama.storage.Storage;

public class AddMealCommand implements Command {
    private final String mealType;
    private final int calories;
    private final Integer protein;
    private final Integer carbs;
    private final Integer fat;

    public AddMealCommand(String mealType, int calories, Integer protein, Integer carbs, Integer fat) {
        this.mealType = mealType;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
    }

    public AddMealCommand(String mealType, int calories) {
        this.mealType = mealType;
        this.calories = calories;
        this.protein = null;
        this.carbs = null;
        this.fat = null;

    }

    public static AddMealCommand fromInput(String input) throws CommandException {
        String desc = input.substring("meal".length()).trim();

        if (!desc.contains("/cal")) {
            throw new CommandException("Invalid format! Try: meal <description> /cal <calories> " +
                    "[/protein <protein>] [/carbs <carbs>] [/fat <fat>]");
        }

        String[] parts = desc.split("/cal");

        if (parts.length == 0) {
            throw new CommandException("Invalid Input! Try: meal <desc> /cal <calories>");
        }

        String mealType = parts[0].trim();

        if (mealType.isEmpty()) {
            throw new CommandException("Meal description missing! Try: meal <desc> /cal <calories>");
        }

        if (parts.length < 2) {
            throw new CommandException("Calories missing! Try: meal <desc> /cal <calories>");
        }

        String rest = parts[1].trim();
        String[] tokens = rest.split("\\s+");

        if (tokens.length == 0) {
            throw new CommandException("Calories missing! Try: meal <desc> /cal <calories>");
        }

        int calories;
        try {
            calories = Integer.parseInt(tokens[0]);
            if (calories < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            throw new CommandException("Calories must be a non-negative integer.");
        }

        Integer protein = null;
        Integer carbs = null;
        Integer fat = null;

        for (int i = 1; i < tokens.length - 1; i++) {
            String macroValueStr = tokens[i + 1];
            int macroValue;
            try {
                macroValue = Integer.parseInt(macroValueStr);
                if (macroValue < 0) {
                    throw new CommandException(tokens[i] + " must be a non-negative number.");
                }
            } catch (NumberFormatException e) {
                throw new CommandException(tokens[i] + " must be a valid number.");
            }

            switch (tokens[i]) {
            case "/protein":
                protein = macroValue;
                break;
            case "/carbs":
                carbs = macroValue;
                break;
            case "/fat":
                fat = macroValue;
                break;
            default:
                throw new CommandException("Unknown macro flag: " + tokens[i]);
            }

            i++; // skip the number token
        }

        return new AddMealCommand(mealType, calories, protein, carbs, fat);
    }

    @Override
    public CommandResult execute(EntryList list, Storage storage) {
        MealEntry entry = new MealEntry(mealType, calories, protein, carbs, fat);
        list.add(entry);
        if (storage != null) {
            storage.save(list);
        }

        int totalCal = list.asList().stream()
                .filter(e -> e.type().equals("MEAL"))
                .mapToInt(e -> ((MealEntry) e).getCalories())
                .sum();

        // Calculate difference from goal
        Integer goal = (storage != null) ? storage.loadGoal() : null;
        String goalMsg = "";
        if (goal != null) {
            int diff = totalCal - goal;
            if (diff > 0) {
                goalMsg = "\nYou are " + diff + " kcal over your goal!";
            } else {
                goalMsg = "\nYou have " + Math.abs(diff) + " kcal left to reach your goal.";
            }
        }

        return new CommandResult("Got it. I've logged this meal:\n"
                + "  " + entry.toListLine() + "\n"
                + "You now have a total of " + totalCal + " kcal recorded!"
                + goalMsg);
    }

}
