package pikachu;

import pikachu.task.TaskList;
import pikachu.storage.Storage;
import pikachu.parser.Parser;
import pikachu.ui.Ui;

public class Pikachu {

    private Storage storage;
    private Ui ui;
    private Parser parser;
    private TaskList tasks;


    //Reuse from https://nus-cs2103-ay2425s2.github.io/website/schedule/week3/project.html#4-add-increments-a-moreoop-a-packages-a-gradle-a-junit-a-jar
    public Pikachu(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);

        try {
            this.tasks = new TaskList(storage.loadData());
        } catch (Exception e) {
            this.tasks = new TaskList();
        }

        this.parser = new Parser(storage, tasks);
    }

    public void run() {
        boolean isExit = false;
        this.ui.showWelcome();

        while(!isExit) {
            try {
                String command = ui.readCommand();
                ui.showLine();
                isExit = parser.shouldExitAfterProcess(command);

                if(isExit) {
                    ui.showExit();
                }
            } catch (Exception e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    public String getResponse(String input) {
        return parser.getResponseForUi(input);
    }

    public static void main(String[] args) {
        new Pikachu("./data/pikachu.txt").run();
    }
}


