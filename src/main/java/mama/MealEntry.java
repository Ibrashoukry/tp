package mama;

import java.time.LocalDateTime;

public class MealEntry {
    private final String name;
    private final int calories;
    private final LocalDateTime timestamp;

    public MealEntry(String name, int calories, LocalDateTime timestamp) {
        // calorie validation
        if (calories <= 0) {
            throw new IllegalArgumentException("calories must be greater than 0.");
        }

        this.name = name;
        this.calories = calories;
        this.timestamp = timestamp;
    }

    // add getters for calories and timestamp once meal entries are
    // stored somewhere



}
