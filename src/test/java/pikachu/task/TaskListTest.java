package pikachu.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class TaskListTest {

    @Test
    public void modifyList_success() {
        TaskList list = new TaskList();
        assertEquals(0, list.getSize());

        Task todo = new ToDo("eat");
        list.addTask(todo);
        assertEquals(1, list.getSize());
        assertEquals(todo, list.getTask(0));

        list.removeTask(todo);
        assertEquals(0, list.getSize());
    }

    @Test
    public void getTask_invalidIndex_IllegalArgumentExceptionThrown() {
        try {
            TaskList list = new TaskList();
            Task todo = new ToDo("sleep");
            list.addTask(todo);
            assertEquals(todo, list.getTask(1));
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Pikachu needs a valid index!", e.getMessage());
        }
    }
}
