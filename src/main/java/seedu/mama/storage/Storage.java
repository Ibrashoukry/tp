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
                try {
                    Files.createDirectories(file.getParent());
                    Files.writeString(file, "NOTE|first\nNOTE|second\n");
                } catch (IOException e) {
                    System.err.println("Failed to seed data file: " + e.getMessage());
                }
            }
            try (BufferedReader br = Files.newBufferedReader(file)) {
                String line;
                while ((line = br.readLine()) != null) {
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

    public void save(EntryList list) {
        try {
            Path tmp = file.resolveSibling(file.getFileName() + ".tmp");
            try (BufferedWriter bw = Files.newBufferedWriter(tmp)) {
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
}
