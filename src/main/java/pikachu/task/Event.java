package pikachu.task;

/**
 * Represents an event task, which is a type of {@code Task} with a start and end time.
 * Inherits from the {@code Task} class and adds functionality for storing and displaying the event details.
 */
public class Event extends Task {

    /** The starting time of the event. */
    protected String from;

    /** The ending time of the event. */
    protected String to;

    /**
     * Constructs a new {@code Event} task with the specified description, start time, and end time.
     *
     * @param description The description of the event.
     * @param from The start time of the event.
     * @param to The end time of the event.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns a string representation of the task formatted for saving to a file.
     * The format is "E|isDone|taskDescription|fromDate|toDate|tag1 tag2 ...".
     *
     * @return A {@code String} representing the task in a format suitable for file storage.
     */
    @Override
    public String saveAsFileFormat() {
        return "E|" + this.isDone + "|" + this.description + "|"
                + this.from + "|" + this.to + "|" + this.saveTagsAsFileFormat();
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from + ", to: " + this.to + ")"
                + " " + this.printTags();
    }


}
