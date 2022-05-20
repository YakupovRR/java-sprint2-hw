package Tests;

import manager.InMemoryTaskManager;
import manager.Managers;
import manager.TaskManager;
import org.testng.annotations.Test;
import tasktypes.Epic;
import tasktypes.Status;
import tasktypes.Subtask;

import java.time.LocalDateTime;

import static org.testng.AssertJUnit.assertTrue;

class EpicTest {

    // Пустой список подзадач.
    @Test
    void subtaskListIsEmpty() {
        TaskManager manager = (InMemoryTaskManager) Managers.getDefault();
        Epic epic = new Epic("Test epic", "Test epic description");
        manager.putEpic(epic);
        assertTrue("Список подзадач пуст", epic.getIncludedSubtaks().size() == 0);
    }

    //Все подзадачи со статусом NEW
    @Test
    void allSubtaskIsNew() {
        TaskManager manager = (InMemoryTaskManager) Managers.getDefault();
        Epic epic = new Epic("Test epic", "Test epic description");
        Subtask subtask1 = new Subtask("Test subtask1", "Test subtask1 description", Status.NEW, 30,
                LocalDateTime.now().plusMinutes(0), epic);
        Subtask subtask2 = new Subtask("Test subtask2", "Test subtask2 description", Status.NEW, 30,
                LocalDateTime.now().plusMinutes(31), epic);
        manager.putEpic(epic);
        manager.putSubtask(subtask1);
        manager.putSubtask(subtask2);

        assertTrue("Статус эпика корректен", epic.getStatus().equals(Status.NEW));
    }

    //Все подзадачи со статусом DONE.
    @Test
    void allSubtaskIsDone() {
        TaskManager manager = (InMemoryTaskManager) Managers.getDefault();
        Epic epic = new Epic("Test epic", "Test epic description");
        Subtask subtask1 = new Subtask("Test subtask1", "Test subtask1 description", Status.DONE, 30,
                LocalDateTime.now().plusMinutes(0), epic);
        Subtask subtask2 = new Subtask("Test subtask2", "Test subtask2 description", Status.DONE, 30,
                LocalDateTime.now().plusMinutes(31), epic);

        manager.putEpic(epic);
        manager.putSubtask(subtask1);
        manager.putSubtask(subtask2);

        assertTrue("Статус эпика корректен", epic.getStatus().equals(Status.DONE));

    }

    //Подзадачи со статусами NEW и DONE.
    @Test
    void allSubtaskIsNewOrDone() {
        TaskManager manager = (InMemoryTaskManager) Managers.getDefault();
        Epic epic = new Epic("Test epic", "Test epic description");
        Subtask subtask1 = new Subtask("Test subtask1", "Test subtask1 description", Status.DONE, 30,
                LocalDateTime.now().plusMinutes(0), epic);
        Subtask subtask2 = new Subtask("Test subtask2", "Test subtask2 description", Status.NEW, 30,
                LocalDateTime.now().plusMinutes(31), epic);
        manager.putEpic(epic);
        manager.putSubtask(subtask1);
        manager.putSubtask(subtask2);

        assertTrue("Статус эпика корректен", epic.getStatus().equals(Status.IN_PROGRESS));

    }

    //Подзадачи со статусом IN_PROGRESS.
    @Test
    void allSubtaskIsInProgress() {
        TaskManager manager = (InMemoryTaskManager) Managers.getDefault();
        Epic epic = new Epic("Test epic", "Test epic description");
        Subtask subtask1 = new Subtask("Test subtask1", "Test subtask1 description", Status.IN_PROGRESS, 30,
                LocalDateTime.now().plusMinutes(0), epic);
        Subtask subtask2 = new Subtask("Test subtask2", "Test subtask2 description", Status.IN_PROGRESS, 30,
                LocalDateTime.now().plusMinutes(31), epic);

        manager.putEpic(epic);
        manager.putSubtask(subtask1);
        manager.putSubtask(subtask2);

        assertTrue("Статус эпика корректен", epic.getStatus().equals(Status.IN_PROGRESS));

    }
}