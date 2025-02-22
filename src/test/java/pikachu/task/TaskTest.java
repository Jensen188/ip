package pikachu.task;

import org.junit.jupiter.api.Test;

import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    public void saveAsFileFormat_success() {
        Task toDo = new ToDo("sleep");
        Task deadline = new Deadline("sleep", "2025-02-23");
        Task event = new Event("sleep", "today", "tomorrow");

        assertEquals("T|false|sleep", toDo.saveAsFileFormat());
        assertEquals("D|false|sleep|2025-02-23", deadline.saveAsFileFormat());
        assertEquals("E|false|sleep|today|tomorrow", event.saveAsFileFormat());
    }

    @Test
    public void testDeadlineDate_invalidFormat_DateTimeParseException() throws DateTimeParseException {
        assertThrows(DateTimeParseException.class,
                () -> new Deadline("sleep", "2025"));
    }
}
