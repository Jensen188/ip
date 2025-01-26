import java.util.Scanner;
import java.util.ArrayList;

public class Pikachu {
    private static final String line = "-------------------"
            + "-------------------";
    private static final String name = "Pikachu";
    private static boolean isExit = false;
    private static final ArrayList<String> list = new ArrayList<>();

    //Printing lines
    private static void line() {
        System.out.println(line);
    }

    private static void start() {
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
        line();
    }

    //React based on command given
    private static void echo(String command) {
        switch (command) {
        case ("bye"):
            exit();
            Pikachu.isExit = true;
            break;
        case "list":
            for (int i = 0; i < list.size(); i++) {
                System.out.printf("%d. %s\n", i+1, list.get(i));
            }
            line();
            break;
        default:
            list.add(command);
            System.out.printf("added: %s\n", command);
            line();
            break;
        }
    }

    public static void main(String[] args) {
        start();
        Scanner sc = new Scanner(System.in);
        while (!Pikachu.isExit) {
            String command = sc.nextLine();
            echo(command);
        }

    }
}

