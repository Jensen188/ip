import java.util.Scanner;
import java.util.ArrayList;

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
    private static void echo(String[] command) {
        line();
        switch (command[0]) {
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
            int id = Integer.parseInt(command[1]) - 1;
            list.get(id).markAsDone();
            System.out.printf("Pika! This task has been marked as done:\n%s\n", list.get(id));
            break;

        case "unmark":
            int index = Integer.parseInt(command[1]) - 1;
            list.get(index).markAsNotDone();
            System.out.printf("Pika! This task has been marked as not done yet:\n%s\n", list.get(index));
            break;

        default:
            StringBuilder builder = new StringBuilder();
            for (String s : command) {
                builder.append(s);
                builder.append(" ");
            }
            String description = builder.toString();
            Task newTask = new Task(description);
            list.add(newTask);
            System.out.printf("added: %s\n", description);
            break;
        }
        line();
        System.out.println();
    }

    public static void main(String[] args) {
        greet();
        Scanner sc = new Scanner(System.in);
        while (!Pikachu.isExit) {
            String[] command = sc.nextLine().split(" ");
            echo(command);
        }
    }
}

