import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

import java.io.IOException;


import task.Task;
import task.Deadline;
import task.ToDo;
import task.Event;

public class Pikachu {
    private static final String DATAFILE_PATH = "./data/pikachu.txt";
    private static final String LINE = "--------------------------------------";
    private static final String NAME = "Pikachu";
    private static boolean isExit = false;
    private static final ArrayList<Task> TASKS = new ArrayList<>();

    //Printing lines
    private static void line() {
        System.out.println(LINE);
    }

    private static void greet() {
        line();
        System.out.printf("""
                It's me %s!!
                What can I do for you?
                
                """, NAME);
        line();
    }

    private static void loadData() throws FileNotFoundException{
        File file = new File(DATAFILE_PATH);
        Scanner sc = new Scanner(file);
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
                String by = input[3];
                task = new Deadline(taskName, by);
                break;
            case "E":
                String from = input[3];
                String to = input[4];
                task = new Event(taskName, from, to);
                break;
            }
            if (isDone.equals("true") && task != null) {
                task.markAsDone();
            }
            TASKS.add(task);
        }
    }

    private static void saveData() throws IOException {
        File directory = new File("./data");
        if (!directory.exists()) {
            directory.mkdir();
        }
        FileWriter fw = new FileWriter(DATAFILE_PATH);
        for (Task task : TASKS) {
            fw.write(task.saveAsFileFormat());
            fw.append("\n");
        }
        fw.close();
    }

    //Stop the chatbot
    private static void exit() {
        System.out.printf("Bye. %s wants to see you again soon!\n", Pikachu.NAME);
    }

    //React based on command given
    private static void handleCommand(String command) {
        line();
        boolean needPrint = false;
        String[] action = command.split(" ");
        switch (action[0]) {
        case ("bye"):
            exit();
            Pikachu.isExit = true;
            break;

        case "list":
            System.out.println("Pika~pika! Here is the list:");
            for (int i = 0; i < TASKS.size(); i++) {
                System.out.printf("%d. %s\n", i + 1, TASKS.get(i));
            }
            break;

        case "mark":
            int id = Integer.parseInt(action[1]) - 1;
            TASKS.get(id).markAsDone();
            System.out.printf("Pika! This task has been marked as done:\n%s\n", TASKS.get(id));
            needPrint = true;
            break;

        case "unmark":
            int index = Integer.parseInt(action[1]) - 1;
            TASKS.get(index).markAsNotDone();
            System.out.printf("Pika! This task has been marked as not done yet:\n%s\n", TASKS.get(index));
            needPrint = true;
            break;

        case "delete":
            int deleteId = Integer.parseInt(action[1]) - 1;
            Task deletedTask = TASKS.get(deleteId);
            TASKS.remove(deleteId);
            System.out.printf("Pika! This task has been deleted:\n%s\n", deletedTask);
            needPrint = true;
            break;

        case "deadline":
            //Solution below inspired by ChatGPT
            int byIndex = command.indexOf("/by");
            String deadline = command.substring(8, byIndex).trim();
            String by = command.substring(byIndex + 3).trim();
            Task newDeadline = new Deadline(deadline, by);
            TASKS.add(newDeadline);
            System.out.printf("Added: %s\n", newDeadline);
            needPrint = true;
            break;

        case "event":
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
            TASKS.add(newEvent);
            System.out.printf("Added: %s\n", newEvent);
            needPrint = true;
            break;

        case "todo":
            try {
                String description = command.substring(4).trim();
                if (description.isEmpty()) {
                    throw new IllegalArgumentException("Pikachu needs description of TODO!!");
                }
                Task newTask = new ToDo(description);
                TASKS.add(newTask);
                System.out.printf("Added: %s\n", newTask);
                needPrint = true;
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                break;
            }

        default:
            System.out.println("Pikachu doesn't know what to do with this command!");
        }

        if (needPrint) {
            System.out.printf("⚡⚡⚡ %d tasks in the list ⚡⚡⚡\n", TASKS.size());
        }
        try {
            Pikachu.saveData();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        line();
    }

    public static void main(String[] args) {
        try {
            loadData();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        greet();
        Scanner sc = new Scanner(System.in);
        while (!Pikachu.isExit) {
            String command = sc.nextLine();
            handleCommand(command);
        }
    }
}

