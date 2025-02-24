package pikachu.task;

/**
 * Represents a to-do task, which is a type of {@code Task} without any specific deadline or time constraints.
 * Inherits from the {@code Task} class and provides functionality for storing and displaying a simple task.
 */
public class ToDo extends Task{

    /**
     * Constructs a new {@code ToDo} task with the specified description.
     *
     * @param description The description of the to-do task.
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of the to-do task formatted for saving to a file.
     * The format is "T|isDone|taskDescription|tag1 tag2 ...".
     *
     * @return A {@code String} representing the to-do task in a format suitable for file storage.
     */
    @Override
    public String saveAsFileFormat() {
        return "T|" + this.isDone + "|" + this.description + "|" + this.saveTagsAsFileFormat();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString() + " " + this.printTags();
    }
}
