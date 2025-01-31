import java.util.Scanner;
import java.util.ArrayList;

import task.Task;
import task.Deadline;
import task.ToDo;
import task.Event;

public class Pikachu {
    private static final String line = "--------------------------------------";
    private static final String name = "Pikachu";
    private static boolean isExit = false;
    private static final ArrayList<Task> list = new ArrayList<>();

    //Printing lines
    private static void line() {
        System.out.println(line);
    }

    private static void greet() {
        line();
        System.out.printf("""
                It's me %s!!
                What can I do for you?
                
                """, name );
        line();
    }

    //Stop the chatbot
    private static void exit() {
        System.out.printf("Bye. %s wants to see you again soon!\n", Pikachu.name);
    }

    //React based on command given
    private static void handleCommand(String command) {
        line();
        StringBuilder builder = new StringBuilder();
        String[] action = command.split(" ");
        switch (action[0]) {
        case ("bye"):
            exit();
            Pikachu.isExit = true;
            break;

        case "list":
            System.out.println("Pika~pika! Here is the list:");
            for (int i = 0; i < list.size(); i++) {
                System.out.printf("%d. %s\n", i+1, list.get(i));
            }
            break;

        case "mark":
            int id = Integer.parseInt(action[1]) - 1;
            list.get(id).markAsDone();
            System.out.printf("Pika! This task has been marked as done:\n%s\n", list.get(id));
            break;

        case "unmark":
            int index = Integer.parseInt(action[1]) - 1;
            list.get(index).markAsNotDone();
            System.out.printf("Pika! This task has been marked as not done yet:\n%s\n", list.get(index));
            break;

        case "deadline":
            //Solution below inspired by ChatGPT
            int byIndex = command.indexOf("/by");
            String deadline = command.substring(8, byIndex).trim();
            String by = command.substring(byIndex + 3).trim();
            Task newDeadline = new Deadline(deadline, by);
            list.add(newDeadline);
            System.out.printf("Added: %s\n", newDeadline);
            break;

        case "event":
            int toIndex = command.indexOf("/to");
            int fromIndex  = command.indexOf("/from");

            String event = command.substring(5, Math.min(fromIndex, toIndex)).trim();
            String from = "";
            String to = "";
            if (fromIndex > toIndex) {
                to = command.substring(toIndex + 3, fromIndex).trim();
                from = command.substring(fromIndex + 5).trim();
            } else {
                System.out.println(fromIndex+ " " + toIndex);
                from = command.substring(fromIndex + 5, toIndex).trim();
                to = command.substring(toIndex + 3).trim();
            }
            System.out.println(event+ " " + from + " " + to);
            Task newEvent = new Event(event, from, to);
            list.add(newEvent);
            System.out.printf("Added: %s\n", newEvent);
            break;

        case "todo":
            String description = command.substring(4).trim();
            Task newTask = new ToDo(description);
            list.add(newTask);
            System.out.printf("Added: %s\n", newTask);
            break;
        }
        System.out.printf("⚡⚡⚡ %d tasks in the list ⚡⚡⚡\n", list.size());
        line();

    }

    public static void main(String[] args) {
        greet();
        Scanner sc = new Scanner(System.in);
        while (!Pikachu.isExit) {
            String command = sc.nextLine();
            handleCommand(command);
        }
    }
}

