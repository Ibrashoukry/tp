package seedu.mama.model;

/**
 * Minimal concrete entry so Delete can be demoed end-to-end.
 */
public class NoteEntry extends Entry {
    public NoteEntry(String text) {
        super("NOTE", text);
    }

    @Override
    public String toStorageString() {
        return "NOTE|" + description();
    }

    public static NoteEntry fromStorage(String line) {
        String[] parts = line.split("\\|", 2);
        return new NoteEntry(parts.length > 1 ? parts[1] : "");
    }
}
