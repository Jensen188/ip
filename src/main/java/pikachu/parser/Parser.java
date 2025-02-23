package pikachu.parser;

import pikachu.task.Deadline;
import pikachu.task.Event;
import pikachu.task.Task;
import pikachu.task.ToDo;
import pikachu.task.TaskList;

import pikachu.storage.Storage;

import java.io.IOException;
import java.time.format.DateTimeParseException;

/**
 * The {@code Parser} class processes user commands, modifies the task list accordingly,
 * and interacts with storage to save the task list.
 */

public class Parser {

    private TaskList tasks;
    private Storage storage;

    /**
     * Constructs a {@code Parser} with the given storage and task list.
     *
     * @param storage The storage system for saving and loading tasks.
     * @param tasks   The list of tasks to be modified based on user commands.
     */
    public Parser(Storage storage, TaskList tasks) {
        this.storage = storage;
        this.tasks = tasks;
    }

    /**
     * Processes a user command and executes the corresponding action.
     * Saves the updated task list to storage after execution.
     *
     * @param command The user's input command.
     * @return {@code true} if the command is "bye" (exit command), {@code false} otherwise.
     */
    public boolean handleCommand(String command) {
        String[] action = command.split(" ");
        boolean isExit = false;
        switch (action[0]) {
        case ("bye"):
            isExit = true;
            break;

        case "list":
            list();
            showTotalTasks();
            break;

        case "mark":
            mark(action);
            showTotalTasks();
            break;

        case "unmark":
            unmark(action);
            showTotalTasks();
            break;

        case "delete":
            delete(action);
            showTotalTasks();
            break;

        case "deadline":
            deadline(command);
            showTotalTasks();
            break;

        case "event":
            event(command);
            showTotalTasks();
            break;

        case "todo":
            toDo(command);
            showTotalTasks();
            break;

        default:
            System.out.println("Pikachu doesn't know what to do with this command!");
        }

        try {
            this.storage.saveData(this.tasks);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return isExit;
    }

    /**
     * Displays the list of tasks.
     * If the list is empty, no tasks are displayed.
     */
    public void list() {
        if (tasks.getSize() == 0) {
            return;
        }
        System.out.println("Pika~pika! Here is the list:");
        for (int i = 0; i < tasks.getSize(); i++) {
            System.out.printf("%d. %s\n", i + 1, tasks.getTask(i));
        }
    }

    /**
     * Marks a task as completed based on the given command input.
     *
     * @param action The command input split into an array.
     */
    public void mark(String[] action) {
        int index = Integer.parseInt(action[1]) - 1;
        tasks.getTask(index).markAsDone();
        System.out.printf("Pika! This task has been marked as done:\n%s\n", tasks.getTask(index));

    }

    /**
     * Marks a task as not completed based on the given command input.
     *
     * @param action The command input split into an array.
     */
    public void unmark(String[] action) {
        int index = Integer.parseInt(action[1]) - 1;
        tasks.getTask(index).markAsNotDone();
        System.out.printf("Pika! This task has been marked as not done yet:\n%s\n", tasks.getTask(index));
    }

    /**
     * Deletes a task from the task list based on the given command input.
     *
     * @param action The command input split into an array.
     */
    public void delete(String[] action) {
        int deleteIndex = Integer.parseInt(action[1]) - 1;
        Task deletedTask = tasks.getTask(deleteIndex);
        tasks.removeTask(deletedTask);
        System.out.printf("Pika! This task has been deleted:\n%s\n", deletedTask);
    }

    /**
     * Adds a new deadline task to the task list.
     *
     * @param command The full command input from the user.
     */
    public void deadline(String command) {
        //Solution below inspired by ChatGPT
        int byIndex = command.indexOf("/by");
        String deadline = command.substring(8, byIndex).trim();
        String by = command.substring(byIndex + 3).trim();
        try {
            Task newDeadline = new Deadline(deadline, by);
            tasks.addTask(newDeadline);
            System.out.printf("Added: %s\n", newDeadline);
        } catch (DateTimeParseException e) {
            System.out.println(by + " is not a valid deadline!\n" + "Pls write in YYYY-MM-DD format");
        }
    }

    /**
     * Adds a new event task to the task list.
     *
     * @param command The full command input from the user.
     */
    public void event(String command) {
        int toIndex = command.indexOf("/to");
        int fromIndex = command.indexOf("/from");

        String event = command.substring(5, Math.min(fromIndex, toIndex)).trim();
        String from = "";
        String to = "";
        if (fromIndex > toIndex) {
            to = command.substring(toIndex + 3, fromIndex).trim();
            from = command.substring(fromIndex + 5).trim();
        } else {
            from = command.substring(fromIndex + 5, toIndex).trim();
            to = command.substring(toIndex + 3).trim();
        }
        Task newEvent = new Event(event, from, to);
        tasks.addTask(newEvent);
        System.out.printf("Added: %s\n", newEvent);
    }

    /**
     * Adds a new ToDo task to the task list.
     *
     * @param command The full command input from the user.
     */
    public void toDo(String command) {
        try {
            String description = command.substring(4).trim();
            if (description.isEmpty()) {
                throw new IllegalArgumentException("Pikachu needs description of TODO!!");
            }
            Task newTask = new ToDo(description);
            tasks.addTask(newTask);
            System.out.printf("Added: %s\n", newTask);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }


    /** Displays the total number of tasks in the task list. */
    public void showTotalTasks() {
        if (tasks.getSize() == 0) {
            System.out.println("No tasks in the list!");
        } else {
            System.out.printf("⚡⚡⚡ %d tasks in the list ⚡⚡⚡\n", tasks.getSize());
        }
    }

}