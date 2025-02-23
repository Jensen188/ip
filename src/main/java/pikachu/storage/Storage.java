package pikachu.storage;

import pikachu.task.Deadline;
import pikachu.task.Event;
import pikachu.task.Task;
import pikachu.task.ToDo;
import pikachu.task.TaskList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private String dataPath;
    private ArrayList<Task> tasks;

    public Storage(String filepath) {
        this.dataPath = filepath;
    }

    /**
     * Loads tasks from a file specified by {@code dataPath} and returns them as an {@code ArrayList} of {@code Task}.
     * The method reads the file line by line, processes each line to identify the task type, and creates the appropriate
     * {@code Task} object (either {@code ToDo}, {@code Deadline}, or {@code Event}). It also marks tasks as completed
     * if indicated in the file.
     *
     * @return An {@code ArrayList} of {@code Task} objects loaded from the file.
     * @throws FileNotFoundException If the file at {@code dataPath} does not exist.
     */
    public ArrayList<Task> loadData() throws FileNotFoundException {
        File file = new File(dataPath);
        this.tasks = new ArrayList<>();
        if (!file.exists()) {
            throw new FileNotFoundException("The file at " + dataPath + " does not exist.");
        }

        Scanner sc = new Scanner(file);
        this.tasks = new ArrayList<>();

        while (sc.hasNext()) {
            String[] input = sc.nextLine().split("\\|");

            String taskType = input[0];
            String isDone = input[1];
            String taskName = input[2];
            Task task = null;

            switch (taskType) {
            case "T":
                task = new ToDo(taskName);
                break;
            case "D":
                if (input.length > 3) {
                    String by = input[3];
                    task = new Deadline(taskName, by);
                }
                break;
            case "E":
                if (input.length > 4) {
                    String from = input[3];
                    String to = input[4];
                    task = new Event(taskName, from, to);
                }
                break;
            }
            if (task != null) {
                if (isDone.equals("true")) {
                    task.markAsDone();
                }
                tasks.add(task);
            }
        }
        return tasks;
    }

    /**
     * Saves the tasks from the given {@code TaskList} to a file specified by {@code dataPath}.
     * If the file does not exist, it attempts to create the necessary directories and the file.
     *
     * @param tasks The {@code TaskList} containing tasks to be saved.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public void saveData(TaskList tasks) throws IOException {
        File saveFile = new File(dataPath);
        boolean hasCreatedNewFile = false;
        if (!saveFile.exists()) {
            hasCreatedNewFile = saveFile.getParentFile().mkdirs();
        }
        if (hasCreatedNewFile) {
            System.out.println("Created new file at: " + dataPath);
        }
        FileWriter fw = new FileWriter(dataPath);
        for (Task task : tasks.getList()) {
            fw.write(task.saveAsFileFormat());
            fw.append("\n");
        }
        fw.close();
    }
}
