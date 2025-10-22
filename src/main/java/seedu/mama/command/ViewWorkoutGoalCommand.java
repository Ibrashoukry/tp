package seedu.mama.command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import seedu.mama.model.Entry;
import seedu.mama.model.EntryList;
import seedu.mama.model.WeekCheck;
import seedu.mama.model.WorkoutEntry;
import seedu.mama.model.WorkoutGoalEntry;
import seedu.mama.model.WorkoutGoalQueries;
import seedu.mama.storage.Storage;

public final class ViewWorkoutGoalCommand implements Command {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");

    @Override
    public CommandResult execute(EntryList list, Storage storage) throws CommandException {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime weekStart = WeekCheck.weekStartMonday(now);

        // 1) Find this week's goal (latest goal set within [Mon..Sun))
        WorkoutGoalEntry goal = WorkoutGoalQueries.currentWeekGoal(list, weekStart);

        // 2) Collect this week's workouts and total minutes
        int minutesThisWeek = 0;
        List<String> thisWeeksWorkouts = new ArrayList<>();

        for (Entry e : list.asList()) {
            if (!"WORKOUT".equals(e.type())) {
                continue;
            }
            WorkoutEntry w = (WorkoutEntry) e;
            LocalDateTime ts = LocalDateTime.parse(w.getDate(), FMT);
            if (WeekCheck.inSameWeek(ts, weekStart)) {
                minutesThisWeek += w.getDuration();
                thisWeeksWorkouts.add("[Workout] " + w.description() + " (" +
                        w.getDuration() + " mins) (" + w.getDate() + ")");
            }
        }

        // 3) Build output
        StringBuilder sb = new StringBuilder();

        if (goal == null) {
            sb.append("No workout goal set for this week. ")
                    .append("You can set one with: `workout goal <minutes>`\n");
            sb.append("Workouts done this week:\n");
            if (thisWeeksWorkouts.isEmpty()) {
                sb.append("  (none yet)\n");
            } else {
                for (int i = 0; i < thisWeeksWorkouts.size(); i++) {
                    sb.append(i + 1).append(") ").append(thisWeeksWorkouts.get(i)).append("\n");
                }
            }
            sb.append("You have completed ").append(minutesThisWeek)
                    .append(" minute").append(minutesThisWeek == 1 ? "" : "s")
                    .append(" so far this week.");
            return new CommandResult(sb.toString());
        }

        // With a goal
        int target = goal.getMinutesPerWeek();
        int remaining = Math.max(0, target - minutesThisWeek);

        sb.append("The weekly goal you set for the week is: ")
                .append(target).append(" minutes.\n");

        sb.append("Workouts done this week:\n");
        if (thisWeeksWorkouts.isEmpty()) {
            sb.append("  (none yet)\n");
        } else {
            for (int i = 0; i < thisWeeksWorkouts.size(); i++) {
                sb.append(i + 1).append(") ").append(thisWeeksWorkouts.get(i)).append("\n");
            }
        }

        if (remaining == 0) {
            sb.append("You have completed ").append(minutesThisWeek)
                    .append(" out of the required ").append(target)
                    .append(" minutes this week. Goal reached—awesome job!");
        } else {
            sb.append("You have completed ").append(minutesThisWeek)
                    .append(" out of the required ").append(target)
                    .append(" minutes this week, ")
                    .append(remaining).append(" more minutes to go! ")
                    .append("Lets get to work Mama!!");
        }

        return new CommandResult(sb.toString());
    }
}
