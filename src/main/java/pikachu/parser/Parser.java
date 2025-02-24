package pikachu.parser;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import pikachu.task.TaskList;
import pikachu.task.Task;
import pikachu.task.Deadline;
import pikachu.task.Event;
import pikachu.task.ToDo;

import pikachu.storage.Storage;

/**
 * The {@code Parser} class processes user commands, modifies the task list accordingly,
 * and interacts with storage to save the task list.
 */
public class Parser {

    private TaskList tasks;
    private Storage storage;
    private String currentMessage;

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

    public String getResponseForUi(String input) {
        boolean isBye = shouldExitAfterProcess(input);

        String response = this.currentMessage;
        if (isBye) {
            response = "Bye. Pikachu wants to see you again soon!\n";
        }
        this.currentMessage = ""; //Reset currentMessage after getting each response
        return response;
    }

    /**
     * Processes a user command and executes the corresponding action.
     * Saves the updated task list to storage after execution.
     *
     * @param command The user's input command.
     * @return {@code true} if the command is "bye" (exit command), {@code false} otherwise.
     */
    public boolean shouldExitAfterProcess(String command) {
        String[] action = command.split(" ");
        boolean isExit = false;
        switch (action[0]) {
        case ("bye"):
            isExit = true;
            break;

        case "list":
            list();
            break;

        case "mark":
            mark(action);
            break;

        case "unmark":
            unmark(action);
            break;

        case "delete":
            delete(action);
            break;

        case "find":
            String keyword = action[1];
            find(keyword);
            break;

        case "deadline":
            deadline(command);
            break;

        case "event":
            event(command);
            break;

        case "todo":
            toDo(command);
            break;

        default:
            currentMessage = "Pikachu doesn't know what to do with this command!";
            System.out.println(currentMessage);
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
        String message  = this.tasks.toString();
        printMessageAndTotalTasks(message);
    }

    /**
     * Marks a task as completed based on the given command input.
     *
     * @param action The command input split into an array.
     */
    public void mark(String[] action) {
        int index = Integer.parseInt(action[1]) - 1;
        tasks.getTask(index).markAsDone();

        String message = String.format("Pika! This task has been marked as done:\n%s\n",
                tasks.getTask(index));
        printMessageAndTotalTasks(message);
    }

    /**
     * Marks a task as not completed based on the given command input.
     *
     * @param action The command input split into an array.
     */
    public void unmark(String[] action) {
        int index = Integer.parseInt(action[1]) - 1;
        tasks.getTask(index).markAsNotDone();

        String message = String.format("Pika! This task has been marked as not done yet:\n%s\n",
                tasks.getTask(index));
        printMessageAndTotalTasks(message);
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

        String message = String.format("Pika! This task has been deleted:\n%s\n",
                deletedTask);
        printMessageAndTotalTasks(message);
    }

    /**
     * Finds and displays all tasks that contain the keyword.
     *
     * @param keyword A {@code String} keyword extracted from command
     */
    public void find(String keyword) {
        ArrayList<Task> matchingTasks = tasks.getMatchingTasks(keyword);
        if (matchingTasks.isEmpty()) {
            currentMessage = "No matching tasks found for keyword: " + keyword;
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Pika! These tasks has been found:\n");

        for (int i = 0; i < matchingTasks.size(); i++) {
            Task task = matchingTasks.get(i);
            String taskString = String.format("%d. %s\n", i + 1, task);
            sb.append(taskString);
        }

        String message = sb.toString();
        printMessageAndTotalTasks(message);
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

            String message = String.format("Added: %s\n", newDeadline);
            printMessageAndTotalTasks(message);
        } catch (DateTimeParseException e) {
            String message = String.format(by + " is not a valid deadline!\n" + "Pls write in YYYY-MM-DD format");
            System.out.println(message);
            currentMessage = message;
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

        //Getting the event description
        String event = command.substring(5, Math.min(fromIndex, toIndex)).trim();
        String from;
        String to;

        //Get the /to argument and /from argument
        if (fromIndex > toIndex) {
            to = command.substring(toIndex + 3, fromIndex).trim();
            from = command.substring(fromIndex + 5).trim();
        } else {
            from = command.substring(fromIndex + 5, toIndex).trim();
            to = command.substring(toIndex + 3).trim();
        }
        Task newEvent = new Event(event, from, to);
        tasks.addTask(newEvent);

        String message = String.format("Added: %s\n", newEvent);
        printMessageAndTotalTasks(message);
    }

    /**
     * Adds a new {@code ToDo} task to the task list.
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

            String message = String.format("Added: %s\n", newTask);
            printMessageAndTotalTasks(message);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            currentMessage = e.getMessage();
        }
    }

    /**
     * Displays all tasks in the current {@code TaskList}
     *
     * @return A {@code String} representation of the current {@code TaskList}
     */
    public String showTotalTasks() {
        StringBuilder sb = new StringBuilder();

        if (tasks.getSize() == 0) {
            sb.append("No tasks in the list!\n");
            return sb.toString();
        }

        sb.append(String.format("⚡⚡⚡ %d tasks in the list ⚡⚡⚡\n", tasks.getSize()));
        return sb.toString();
    }

    private void printMessageAndTotalTasks(String message) {
        System.out.println(message);
        System.out.println(this.showTotalTasks());
        this.currentMessage = message + this.showTotalTasks();
    }
}