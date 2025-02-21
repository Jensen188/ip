package pikachu.task;

public class ToDo extends Task{
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String saveAsFileFormat() {
        return "T|" + this.isDone + "|" + this.task;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
