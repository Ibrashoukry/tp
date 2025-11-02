package seedu.mama.command;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Defines the available commands and their basic usage format for display.
 */
public enum CommandType {
    WORKOUT("workout <description> /dur <duration (mins)> /feel <feeling (out of 5)>"),
    MEAL("meal <meal description> /cal <calories> [/p <protein>] [/c <carbs>] [/f <fat>]"),
    WEIGHT("weight <weight>"),
    MILK("milk <volume>"),
    MEASURE("measure waist/<cm> hips/<cm> [chest/<cm>] [thigh/<cm>] [arm/<cm>]"),
    DELETE("delete <index>"),
    LIST("list [/t TYPE]"),
    DASHBOARD("dashboard"),
    WORKOUT_GOAL_SET("workout goal <minutes>"),
    WORKOUT_GOAL_VIEW("workout goal"), // Separate entry for viewing
    CALORIE_GOAL_SET("calorie goal <calories>"),
    CALORIE_GOAL_VIEW("calorie goal"),
    BYE("bye");

    private final String usage;

    CommandType(String usage) {
        this.usage = usage;
    }

    public String getUsage() {
        return usage;
    }

    /**
     * Generates a string listing all command usages, separated by commas.
     * @return A string like "(command1, command2, ...)"
     */
    public static String getAllUsageString() {
        CommandType[] commands = CommandType.values();
        final String INDENT = "     ";

        // Use an IntStream to generate indices (0, 1, 2, ...)
        return java.util.stream.IntStream.range(0, commands.length)
                .mapToObj(i -> {
                    // 'i' is the zero-based index. Add 1 for the list number (1., 2., 3., ...)
                    int index = i + 1;

                    // Get the corresponding CommandType and its usage string
                    String usage = commands[i].getUsage();

                    // Format the output: "1. usage_string"
                    return String.format("%s%d. %s", INDENT, index, usage);
                })
                // Join the indexed strings with a newline character
                .collect(java.util.stream.Collectors.joining("\n"));
    }
}
