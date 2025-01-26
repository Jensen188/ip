import java.util.Scanner;

public class Pikachu {
    private static final String line = "-------------------" +
            "-------------------";
    private static final String name = "Pikachu";
    private static boolean isExit = false;


    //Printing lines
    private static void line() {
        System.out.println(line);
    }

    private static void start(){
        line();
        System.out.printf("It's me %s!!\n" +
                "What can I do for you?\n\n", name );
        line();
    }

    //Stop the chatbot
    private static void exit() {
        System.out.printf("Bye. %s wants to see you again soon!\n", Pikachu.name);
        line();
    }

    //Repeating the command
    private static void echo(String command) {
        if (!command.equals("bye")) {
            System.out.println(command);
            line();
        } else {
            exit();
            Pikachu.isExit = true;
        }
    }

    public static void main(String[] args) {
        start();
        Scanner sc = new Scanner(System.in);
        while(!Pikachu.isExit) {
            echo(sc.nextLine());
        }

    }
}

