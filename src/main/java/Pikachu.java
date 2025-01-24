public class Pikachu {
    private static final String line = "-------------------" +
            "-------------------";
    private static final String name = "Pikachu";

    //Printing lines
    private static void line() {
        System.out.println(line);
    }

    public static void main(String[] args) {
        line();
        System.out.printf("Hello, I'm %s!\n" +
                "What can I do for you?\n\n", name );
        line();
        System.out.println("Bye. Hope to see you again soon!\n");
        line();



    }
}

