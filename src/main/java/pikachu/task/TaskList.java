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
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }
}
