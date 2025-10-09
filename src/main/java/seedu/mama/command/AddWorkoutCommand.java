package seedu.mama.command;

import seedu.mama.model.EntryList;
import seedu.mama.model.WorkoutEntry;
import seedu.mama.storage.Storage;

public final class AddWorkoutCommand implements Command {
    private final String workoutType;
    private final int duration; // minutes

    public AddWorkoutCommand(String workoutType, int duration) {
        this.workoutType = workoutType;
        this.duration = duration;
    }

    public static AddWorkoutCommand fromInput(String input) {
        // Assumes lowercase "workout" and exact format, error handling not added yet
        String after = input.substring("workout".length()).trim();
        String[] parts = after.split("/dur", 2);
        String type = parts[0].trim();
        String durStr = parts[1].trim().split("\\s+")[0];
        int duration = Integer.parseInt(durStr);
        return new AddWorkoutCommand(type, duration);
    }

    @Override
    public String execute(EntryList list, Storage storage) {
        WorkoutEntry entry = new WorkoutEntry(workoutType, duration);
        list.add(entry);
        storage.save(list);
        long workoutCount = list.asList().stream()
                .filter(e -> e.type().equals("WORKOUT"))
                .count();
        return "Got it. I've logged this workout:\n"
                + "  " + entry.toListLine() + "\n"
                + "Great job Mama! You now have a total of " + workoutCount
                + " workouts completed! Lets keep it up!!";
    }
}
