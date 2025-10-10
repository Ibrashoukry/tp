package seedu.mama;

import seedu.mama.command.Command;
import seedu.mama.model.EntryList;
import seedu.mama.parser.Parser;
import seedu.mama.storage.Storage;

import java.util.Scanner;

public class Mama {
    /**
     * Main entry-point for the MAMA app.
     */
    public static void main(String[] args) {
        System.out.println("Hello from MAMA");

        System.out.println("Enter a command (workout <description> /dur <duration>, delete <index>, " +
                "list, milk <volume>, weight <weight>, bye)");

        Storage storage = Storage.defaultStorage();
        EntryList list = storage.loadOrEmpty();
        Parser parser = new Parser();
        Scanner sc = new Scanner(System.in);

        while (true) {
            String line = sc.nextLine();
            Command cmd = parser.parse(line);
            String out = cmd.execute(list, storage);
            System.out.println(out);
            if (out.startsWith("Bye.")) {
                break;
            }
        }
    }
}
