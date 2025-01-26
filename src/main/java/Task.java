public class Task {
	protected String task;
	protected boolean isDone;

	public Task(String task) {
		this.task = task;
		isDone = false;
	}

	public void markAsDone() {
		this.isDone = true;
	}

	public void markAsNotDone() {
		this.isDone = false;
	}

	private String showDoneStatus() {
		return this.isDone ? "X" : " ";
	}

	@Override
	public String toString() {
		return String.format("[%s] %s", this.showDoneStatus(), task);
	}
}
