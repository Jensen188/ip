package pikachu.task;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        try{
            this.tasks = tasks;
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task getTask(int index) throws IllegalArgumentException {
        if (!isValidIndex(index)) {
            throw new IllegalArgumentException("Pikachu needs a valid index!");
        }
        return this.tasks.get(index);
    }

    public boolean isValidIndex(int index) {
        if (this.getSize() == 0) {
            return false;
        }
        return index >= 0 && index < this.tasks.size();
    }
    public int getSize() {
        return this.tasks.size();
    }

    public ArrayList<Task> getList() {
        return this.tasks;
    }

    public void removeTask(Task task) {
        tasks.remove(task);
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
}
