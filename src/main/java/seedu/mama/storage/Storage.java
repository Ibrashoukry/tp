package seedu.mama.storage;

import seedu.mama.model.Entry;
import seedu.mama.model.EntryList;

import java.io.*;
import java.nio.file.*;

public class Storage {
    private final Path file;

    public Storage(Path file) {
        this.file = file;
    }

    public static Storage defaultStorage() {
        Path dir = Paths.get("data");
        try {
            Files.createDirectories(dir);
        } catch (IOException ignored) {
        }
        return new Storage(dir.resolve("mama.txt"));
    }

    public EntryList loadOrEmpty() {
        EntryList list = new EntryList();
        try {
            if (!Files.exists(file)) {
                Files.createDirectories(file.getParent());
                Files.writeString(file, "NOTE|first\nNOTE|second\n");
            }
            try (BufferedReader br = Files.newBufferedReader(file)) {
                String line;
                while ((line = br.readLine()) != null) {
                    try {
                        list.add(Entry.fromStorageString(line));
                    } catch (Exception ignored) {
                    }
                }
            }
        } catch (IOException ignored) {
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
            Files.move(tmp, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException ignored) {
        }
    }
}
