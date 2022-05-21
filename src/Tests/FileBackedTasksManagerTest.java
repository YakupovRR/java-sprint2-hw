package tests;

import manager.FileBackedTasksManager;
import org.testng.annotations.Test;
import tasktypes.Epic;
import tasktypes.Status;
import tasktypes.Task;

import java.io.File;
import java.time.LocalDateTime;

import static org.testng.AssertJUnit.*;

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
    @Test
    void epicEmpty() {
        File file = new File("Task manager.csv");
        FileBackedTasksManager taskManager = new FileBackedTasksManager(file);
        Epic epic = new Epic("Test epic", "Test epic description");
        FileBackedTasksManager taskManagerFromFile = FileBackedTasksManager.loadFromFile(file);
        taskManager.putEpic(epic);
        assertFalse("Сохранения эпика без подзадач корректно", file.length() == 0);
        assertTrue("Восстановление эпика без подзадач корректно", !(taskManagerFromFile.getEpics().isEmpty()));
    }
}



