package tasktypes;

import manager.*;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static manager.Main.manager;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

class EpicTest {

    // Пустой список подзадач.
    @Test
    void subtaskListIsEmpty() {
        TaskManager manager = (InMemoryTaskManager) Managers.getDefault();
        Epic epic = new Epic("Test epic", "Test epic description");
        manager.putEpic(epic);
        List<Subtask> subtaskList = new ArrayList<>(epic.getIncludedSubtaks());
        boolean isEpty = subtaskList.isEmpty();
        assertTrue("Список подзадач пуст", isEpty);
        assertFalse("Список подзадач не пуст", isEpty);
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
        List<Subtask> subtaskList = new ArrayList<>(epic.getIncludedSubtaks());
        boolean AllIsNew = true;
        for (Subtask i : subtaskList) {
            if (!i.getStatus().equals(Status.NEW)) {
                AllIsNew = false;
            }
        }
        assertTrue("Все подзадачи со статусом NEW", AllIsNew);
        assertFalse("Не все подзадачи со статусом NEW", AllIsNew);
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
        List<Subtask> subtaskList = new ArrayList<>(epic.getIncludedSubtaks());
        boolean AllIsDone = true;
        for (Subtask i : subtaskList) {
            if (!i.getStatus().equals(Status.DONE)) {
                AllIsDone = false;
            }
        }
        if (!epic.getStatus().equals(Status.DONE)) {
            AllIsDone = false;
        }
        assertTrue("Все подзадачи и эпик со статусом DONE", AllIsDone);
        assertFalse("Не все подзадачи или эпик со статусом DONE", AllIsDone);
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
        List<Subtask> subtaskList = new ArrayList<>(epic.getIncludedSubtaks());
        boolean AllIsNewOrDone = true;
        for (Subtask i : subtaskList) {
            if (!(i.getStatus().equals(Status.DONE)) || (i.getStatus().equals(Status.NEW))) {
                AllIsNewOrDone = false;
            }
        }
        if (epic.getStatus().equals(Status.NEW)) {
            AllIsNewOrDone = false;
        }
        assertTrue("Все подзадачи статусом NEW или DONE, статус эпика корректен", AllIsNewOrDone);
        assertFalse("Некорректные статусы подзадач или эпика", AllIsNewOrDone);
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
        List<Subtask> subtaskList = new ArrayList<>(epic.getIncludedSubtaks());
        boolean AllIsInProgress = true;
        for (Subtask i : subtaskList) {
            if (!i.getStatus().equals(Status.IN_PROGRESS)) {
                AllIsInProgress = false;
            }
        }
        if (!epic.getStatus().equals(Status.IN_PROGRESS)) {
            AllIsInProgress = false;
        }
        assertTrue("Все подзадачи и эпик со статусом IN_PROGRESS", AllIsInProgress);
        assertFalse("Не все подзадачи или эпик со статусом IN_PROGRESS", AllIsInProgress);
    }
}