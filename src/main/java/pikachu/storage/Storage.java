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
     * Load data into a TaskList
     * @throws FileNotFoundException File doesn't exist
     */
    public ArrayList<Task> loadData() throws FileNotFoundException {
        File file = new File(dataPath);
        this.tasks = new ArrayList<>();
        if (!file.exists()) {
            throw new FileNotFoundException("The file at " + dataPath + " does not exist.");
        }

        Scanner sc = new Scanner(file);
        this.tasks = new ArrayList<>(); // Initialize the task list

        while (sc.hasNext()) {
            String[] input = sc.nextLine().split("\\|");

            if (input.length < 3) {
                System.out.println("Skipping malformed line: " + String.join("|", input));
                continue; // Skip malformed lines
            }

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
            default:
                System.out.println("Unknown task type: " + taskType);
            }

            if (task != null) {
                if (isDone.equals("true")) {
                    task.markAsDone();
                }
                tasks.add(task);
            } else {
                System.out.println("Skipping invalid task format: " + String.join("|", input));
            }
        }
        return tasks;
    }

    public ArrayList<Task> saveData(TaskList tasks) throws IOException {
        File saveFile = new File(dataPath);
        boolean hasCreatedNewFile = false;
        if (!saveFile.exists()) {
            hasCreatedNewFile = saveFile.createNewFile();
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
        return this.tasks;
    }
}
