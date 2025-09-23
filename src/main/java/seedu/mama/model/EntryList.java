package seedu.mama.model;

import java.util.ArrayList;
import java.util.List;

public class EntryList {
    private final List<Entry> items = new ArrayList<>();

    public void add(Entry e) {
        items.add(e);
    }

    public Entry deleteByIndex(int zeroBased) {
        return items.remove(zeroBased);
    }

    public int size() {
        return items.size();
    }

    public Entry get(int i) {
        return items.get(i);
    }

    public List<Entry> asList() {
        return new ArrayList<>(items);
    }
}
