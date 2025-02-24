//Solution below adapted from https://nus-cs2103-ay2425s2.github.io/website/schedule/week2/project.html#1-learn-about-the-project, A-Classes
package pikachu.task;

/**
 * Represents an abstract task that contains a description and a completion status.
 * This class serves as the base class for different types of tasks such as {@code ToDo}, {@code Deadline}, and {@code Event}.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

	/**
	 * Constructs
	 *
	 * @param description The description of the task.
	 */
    public Task(String description) {
		this.description = description;
		isDone = false;
	}

	/**
	 * Returns a string representation of the task formatted for saving to a file.
	 * Subclasses must implement this method.
	 *
	 * @return A formatted {@code String} representing the task for file storage.
	 */
	public abstract String saveAsFileFormat();

	/** Marks the task as done. */
	public void markAsDone() {
		this.isDone = true;
	}

	/** Marks the task as not done. */
	public void markAsNotDone() {
		this.isDone = false;
	}

	/**
	 * Returns a string representation of the task's completion status.
	 *
	 * @return A string "X" if task is done, otherwise a space " ".
	 */
	private String showDoneStatus() {
		return (this.isDone ? "X" : " ");
	}

	/**
	 * Checks whether the task description contains the keyword.
	 *
	 * @param keyword The keyword to search in the task description.
	 * @return {@code true} if contains the keyword, {@code false} otherwise.
	 */
	public boolean hasMatchingKeyword(String keyword) {
		return this.description.toLowerCase()
				.contains(keyword.toLowerCase());
	}

	@Override
	public String toString() {
		return String.format("[%s] %s", this.showDoneStatus(), description);
	}
}
