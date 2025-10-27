package seedu.mama.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Integer.parseInt;

public class EntryList {
    private final List<Entry> items = new ArrayList<>();

    public void add(Entry e) {
        items.add(e);
    }

    public Entry deleteByIndex(int zeroBased) {
        Entry removed = items.get(zeroBased);
        if (removed instanceof MilkEntry) {
            String volStr = ((MilkEntry) removed).getMilk();
            String numberVol = volStr.replace("ml", "");
            int volume = parseInt(numberVol);
            MilkEntry.minusTotalMilkVol(volume);
        }
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
