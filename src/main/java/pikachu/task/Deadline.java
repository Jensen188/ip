package pikachu.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected LocalDate deadline;
    protected static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy");

    public Deadline(String description, String by) {
        super(description);
        this.deadline = LocalDate.parse(by);
    }

    @Override
    public String saveAsFileFormat() {
        return "D|" + this.isDone + "|" + this.task + "|" + this.deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.deadline.format(formatter) + ")";
    }
}
