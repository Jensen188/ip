package ui;

import java.util.Scanner;

public class Ui {
    private final String NAME = "Pikachu";
    private final Scanner SC;

    public Ui() {
        this.SC = new Scanner(System.in);
    }

    public void showLine() {
        String LINE = "--------------------------------------";
        System.out.println(LINE);
    }

    public void showWelcome() {
        this.showLine();
        System.out.printf("""
                It's me %s!!
                What can I do for you?
                """, this.NAME);
        this.showLine();
    }

    public void showExit() {
        System.out.printf(
                "Bye. %s wants to see you again soon!\n", this.NAME);
    }

    public void showError(String errorMessage) {
        System.out.println(errorMessage);
    }

    public String readCommand() {
        return SC.nextLine();
    }
}
