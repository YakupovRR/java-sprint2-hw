package Tests;

import manager.FileBackedTasksManager;
import org.testng.annotations.Test;
import tasktypes.Epic;
import tasktypes.Status;
import tasktypes.Task;

import java.io.File;
import java.time.LocalDateTime;

import static org.testng.AssertJUnit.assertTrue;

public class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    public FileBackedTasksManagerTest() {
        File file = new File("Task manager.csv");
        taskManager = new FileBackedTasksManager(file);
    }

    //проверка создания и восстановления при пустом списке задач
    @Test
    void listIsEpty() {
        File file = new File("Task manager.csv");
        FileBackedTasksManager taskManager = new FileBackedTasksManager(file);
        taskManager.save();
        FileBackedTasksManager taskManagerFromFile = FileBackedTasksManager.loadFromFile(file);
        assertTrue("Сохранения пустого списка в файл корректно", !(file.length() == 0));
        assertTrue("Восстановление из пустого файла корректно", (
                taskManagerFromFile.getPrioritizedTasks().size() > 0));
    }

    //проверка пустого списка истории
    @Test
    void historyListIsEpty() {
        File file = new File("Task manager.csv");
        FileBackedTasksManager taskManager = new FileBackedTasksManager(file);
        Task task = new Task("Test", "description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(0));
        taskManager.removeById(task.getId());
        taskManager.save();
        FileBackedTasksManager taskManagerFromFile = FileBackedTasksManager.loadFromFile(file);
        assertTrue("Сохранение пустой истории корректно", taskManagerFromFile.history().isEmpty());
    }

    //эпик без подзадач
    // Понятней не столо. Эпик без подзадач...что? Что он сам факт сохранился? Или что он именно без подзадач?
    @Test
    void epicEmpty() {
        File file = new File("Task manager.csv");
        FileBackedTasksManager taskManager = new FileBackedTasksManager(file);
        Epic epic = new Epic("Test epic", "Test epic description");
        taskManager.save(); //  а как тогда без save?
        FileBackedTasksManager taskManagerFromFile = FileBackedTasksManager.loadFromFile(file);
        assertTrue("Сохранения эпика без подзадач корректно", !(file.length() == 0));
        assertTrue("Восстановление эпика без подзадач корректно", !(taskManagerFromFile.getEpics().isEmpty()));
    }

    @Test
    void subtaskHaveEpic() {
    }

    @Test
    void epicStatus() {
    }

    @Test
    void normalGetTasks() {
    }

    @Test
    void normalClearAllTasks() {
    }

    @Test
    void normalGetById() {
    }

    void normalUpdateTask() {
    }

    void normalDeleteTask() {
    }

    @Test
    void normalGetTasksFromEmptyList() {
    }

    @Test
    void emptyClearAllTasks() {
    }

    @Test
    void errorUpdateTasks() {
    }

    @Test
    void errorDeleteTasks() {
    }

    @Test
    void errorGetTasks() {
    }

    @Test
    void putTaskNorm() {
    }

    @Test
    void putEpicNorm() {
    }

    @Test
    void putSubtaskNorm() {
    }

    @Test
    void getNotEmtyHistory() {
    }

    @Test
    void getEmtyHistory() {
    }

    @Test
    void getIdFromNotEpty() {
    }

    @Test
    void getIdFromEpty() {
    }

    @Test
    void epicOfSubtaskNorm() {
    }
}



