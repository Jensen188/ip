package pikachu.task;

import java.util.ArrayList;

/**
 * Represents a list of tasks. This class provides methods to add, retrieve, remove,
 * and check tasks in the list.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a new {@code TaskList} with the specified list of tasks.
     *
     * @param tasks The initial list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        try{
            this.tasks = tasks;
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Retrieves a task from the list based on its index.
     *
     * @param index The index of the task in the list.
     * @return The task at the specified index.
     * @throws IllegalArgumentException If the index is invalid.
     */
    public Task getTask(int index) throws IllegalArgumentException {
        if (this.tasks.isEmpty()) {
            throw new IllegalArgumentException("Task list is empty! No tasks to retrieve.");
        }
        if (!isValidIndex(index)) {
            throw new IllegalArgumentException("Pikachu needs a valid index!");
        }
        assert index >= 0 && index < this.tasks.size() : "Not a valid index";
        return this.tasks.get(index);
    }

    /**
     * Checks if the given index is within the valid range of the task list.
     *
     * @param index The index to check.
     * @return {@code true} if the index is valid, {@code false} otherwise.
     */
    public boolean isValidIndex(int index) {
        return index >= 0 && index < this.tasks.size();
    }

    public int getSize() {
        return this.tasks.size();
    }

    public ArrayList<Task> getList() {
        return this.tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
        assert (tasks.contains(task)) : "TaskList doesn't perform add function!";
    }

    public void removeTask(Task task) {
        tasks.remove(task);
        assert (!tasks.contains(task)) : "Task not removed from TaskList!";
    }

    public ArrayList<Task> getMatchingTasks(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task: tasks) {
            if (task.hasMatchingKeyword(keyword)) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }

    /**
     * Returns a string representation of the {@code TaskList}.
     * If the list is empty, a message indicating no tasks will be returned.
     *
     * @return A formatted string representation of {@code TaskList}
     */
    @Override
    public String toString() {
        if (this.getSize() == 0) {
            return "No tasks in list currently\n";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Pika~pika! Here is the list:\n");

        for (int i = 0; i < this.getSize(); i++) {
            sb.append(String.format("%d. %s\n", i + 1, this.getTask(i)));
        }

        return sb.toString();
    }
}
