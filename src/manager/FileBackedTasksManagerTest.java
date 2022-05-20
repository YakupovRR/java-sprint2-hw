package manager;

import org.testng.annotations.Test;
import tasktypes.Epic;
import tasktypes.Status;
import tasktypes.Task;

import java.io.File;
import java.time.LocalDateTime;

import static org.testng.AssertJUnit.assertTrue;

public class FileBackedTasksManagerTest extends TaskManagerTest {
    public FileBackedTasksManagerTest(TaskManager taskManager) {
        super(taskManager);
    }


    //проверка создания и восстановления при пустом списке задач
    @Test
    void listIsEpty() {
        File file = new File("Task manager.csv");
        FileBackedTasksManager taskManager = new FileBackedTasksManager(file);
        taskManager.save();
        boolean saveIsCorrect = false;
        boolean loadedIsCorrect = false;
        if (file.length() == 0) saveIsCorrect = true;
        FileBackedTasksManager taskManagerFromFile = FileBackedTasksManager.loadFromFile(file);
        if (taskManagerFromFile.getTasks().isEmpty() && taskManagerFromFile.getEpics().isEmpty() &&
                taskManagerFromFile.getSubtasks().isEmpty()) {
            loadedIsCorrect = true;
        }
        assertTrue("Сохранения пустого списка в файл корректно", saveIsCorrect);
        assertTrue("Восстановление из пустого файла корректно", loadedIsCorrect);
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
        boolean saveIsCorrect = false;
        boolean loadedIsCorrect = false;


        if (taskManager.history().isEmpty()) saveIsCorrect = true;
        FileBackedTasksManager taskManagerFromFile = FileBackedTasksManager.loadFromFile(file);
        if (taskManagerFromFile.history().isEmpty()) {
            loadedIsCorrect = true;
        }
        assertTrue("Сохранения пустой истории в файл корректно", saveIsCorrect);
        assertTrue("Восстановление из файла пустой истории корректно", loadedIsCorrect);
    }


    //эпик без подзадач
    //понятия не имею, что там по ТЗ от нас этим хотели узнать
    @Test
    void historyListIsEpty() {
        File file = new File("Task manager.csv");
        FileBackedTasksManager taskManager = new FileBackedTasksManager(file);
        Epic epic = new Epic("Test epic", "Test epic description");
        taskManager.save();
        boolean isCorrect = false;
        if (taskManager.history().isEmpty()) isCorrect = true;
        FileBackedTasksManager taskManagerFromFile = FileBackedTasksManager.loadFromFile(file);
       Epic epic2 = taskManagerFromFile.getEpics().get(0);
        if (epic.equals(epic2)) {
            isCorrect = true;
        }
        assertTrue("Сохранения эпика без подзадач корректно", isCorrect);
    }

}



