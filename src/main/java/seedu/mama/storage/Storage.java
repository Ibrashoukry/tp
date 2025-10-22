package seedu.mama.storage;

import seedu.mama.model.Entry;
import seedu.mama.model.EntryList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final Path file;

    public Storage(Path file) {
        this.file = file;
    }

    public static Storage defaultStorage() {
        Path dir = Paths.get("data");
        try {
            Files.createDirectories(dir);
        } catch (IOException e) {
            System.err.println("Failed to create data directory: " + e.getMessage());
        }
        return new Storage(dir.resolve("mama.txt"));
    }

    public EntryList loadOrEmpty() {
        EntryList list = new EntryList();
        try {
            if (!Files.exists(file)) {
                // No data file yet → start with an empty list
                return list;
            }

            try (BufferedReader br = Files.newBufferedReader(file)) {
                String line;

                while ((line = br.readLine()) != null) {

                    if (line.startsWith("GOAL|")) continue; // skip goal line

                    try {
                        list.add(Entry.fromStorageString(line));
                    } catch (IllegalArgumentException ex) {
                        System.err.println("Skipping bad line in storage: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to read storage: " + e.getMessage());
        }
        return list;
    }

    /**
     * Loads calorie goal if present, otherwise returns null.
     */
    public Integer loadGoal() {
        try {
            if (!Files.exists(file)) return null;

            try (BufferedReader br = Files.newBufferedReader(file)) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.startsWith("GOAL|")) {
                        String[] parts = line.split("\\|");
                        if (parts.length == 2) {
                            return Integer.parseInt(parts[1]);
                        }
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Failed to load calorie goal: " + e.getMessage());
        }
        return null;
    }


    public void save(EntryList list) {
        try {
            Integer existingGoal = loadGoal();
            Path tmp = file.resolveSibling(file.getFileName() + ".tmp");

            try (BufferedWriter bw = Files.newBufferedWriter(tmp)) {
                if (existingGoal != null) {
                    bw.write("GOAL|" + existingGoal);
                    bw.newLine();
                }

                for (Entry e : list.asList()) {
                    bw.write(e.toStorageString());
                    bw.newLine();
                }
            }
            Files.move(tmp, file,
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException e) {
            System.err.println("Failed to save storage: " + e.getMessage());
        }
    }

    /**
     * Writes or updates the goal line at the top of the file.
     */
    public void saveGoal(int goal) {
        try {
            List<String> lines = new ArrayList<>();

            if (Files.exists(file)) {
                lines = Files.readAllLines(file);
                lines.removeIf(line -> line.startsWith("GOAL|"));
            }

            lines.add(0, "GOAL|" + goal);
            Files.write(file, lines);
        } catch (IOException e) {
            System.err.println("Failed to save calorie goal: " + e.getMessage());
        }
    }
}
