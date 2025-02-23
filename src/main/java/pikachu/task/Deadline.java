package pikachu.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a deadline task, which is a type of {@code Task} with a specific deadline date.
 * Inherits from the {@code Task} class and adds functionality for storing and displaying the deadline.
 */
public class Deadline extends Task {
    protected LocalDate deadline;
    protected static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy");

    /**
     * Constructs a new {@code Deadline} task with the specified description and deadline.
     *
     * @param description The description of the task.
     * @param by The deadline date as a {@code String} in the format "yyyy-MM-dd".
     */
    public Deadline(String description, String by) {
        super(description);
        this.deadline = LocalDate.parse(by);
    }

    /**
     * Returns a string representation of the task formatted for saving to a file.
     * The format is "D|isDone|taskDescription|deadline".
     *
     * @return A {@code String} representing the task in a format suitable for file storage.
     */
    @Override
    public String saveAsFileFormat() {
        return "D|" + this.isDone + "|" + this.description + "|" + this.deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.deadline.format(formatter) + ")";
    }
}
