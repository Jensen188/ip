//Solution below adapted from https://nus-cs2103-ay2425s2.github.io/website/schedule/week2/project.html#1-learn-about-the-project, A-Classes
package task;

public abstract class Task {
	protected String task;
	protected boolean isDone;

	public Task(String task) {
		this.task = task;
		isDone = false;
	}

	public abstract String saveAsFileFormat();

	public void markAsDone() {
		this.isDone = true;
	}

	public void markAsNotDone() {
		this.isDone = false;
	}

	private String showDoneStatus() {
		return (this.isDone ? "X" : " ");
	}

	@Override
	public String toString() {
		return String.format("[%s] %s", this.showDoneStatus(), task);
	}
}
