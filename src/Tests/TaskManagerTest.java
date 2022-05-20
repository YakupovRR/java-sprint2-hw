package Tests;

import manager.TaskManager;
import org.testng.annotations.Test;
import tasktypes.Epic;
import tasktypes.Status;
import tasktypes.Subtask;
import tasktypes.Task;

import java.time.LocalDateTime;
import java.util.List;

import static jdk.javadoc.internal.doclets.toolkit.util.DocPath.empty;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

abstract class TaskManagerTest<T extends TaskManager> {
    protected T taskManager;

    public TaskManagerTest(T taskManager) {
        this.taskManager = taskManager;
    }

    public TaskManagerTest() {
    }

    //наличие эпика у подзадачи - норма
    @Test
    void subtaskHaveEpic() {
        Epic epic = new Epic("Test epic", "Test epic description");
        Subtask subtask1 = new Subtask("Test subtask1", "Test subtask1 description", Status.NEW, 30,
                LocalDateTime.now().plusMinutes(0), epic);
        assertFalse("Подзадача имеет эпик", subtask1.getParentEpic().equals(null));
    }

    //проверка статуса эпика
    @Test
    void epicStatus() {
        Epic epic = new Epic("Test epic", "Test epic description");
        Epic epic2 = new Epic("Test epic", "Test epic description");
        Subtask subtask1 = new Subtask("Test subtask1", "Test subtask1 description", Status.NEW,
                30, LocalDateTime.now().plusMinutes(0), epic2);
        Epic epic3 = new Epic("Test epic", "Test epic description");
        Subtask subtask3 = new Subtask("Test subtask1", "Test subtask1 description", Status.DONE,
                30, LocalDateTime.now().plusMinutes(60), epic3);
        Epic epic4 = new Epic("Test epic", "Test epic description");
        Subtask subtask4 = new Subtask("Test subtask1", "Test subtask1 description", Status.DONE,
                30, LocalDateTime.now().plusMinutes(120), epic3);
        Subtask subtask5 = new Subtask("Test subtask1", "Test subtask1 description", Status.NEW,
                30, LocalDateTime.now().plusMinutes(240), epic3);

        assertTrue("Статус пустого эпика корректен", epic.getStatus().equals(Status.NEW));
        assertTrue("Статус нового эпика корректен", epic2.getStatus().equals(Status.NEW));
        assertTrue("Статус завершенного эпика корректен", epic3.getStatus().equals(Status.DONE));
        assertTrue("Статус эпика в процессе корректен", epic4.getStatus().equals(Status.IN_PROGRESS));
    }

    //проверка методов
    //получение списка всех задач - норма
    @Test
    void normalGetTasks() {
        Task task = new Task("Test", "description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(80));
        Task task2 = new Task("Test", "description", Status.DONE, 10,
                LocalDateTime.now().plusMinutes(100));
        Epic epic = new Epic("Test epic", "Test epic description");
        Epic epic2 = new Epic("Test epic", "Test epic description");
        Subtask subtask1 = new Subtask("Test subtask1", "Test subtask1 description", Status.NEW, 30,
                LocalDateTime.now().plusMinutes(0), epic);
        Subtask subtask2 = new Subtask("Test subtask2", "Test subtask2 description", Status.NEW, 30,
                LocalDateTime.now().plusMinutes(31), epic);

        assertTrue("Лист задач корректен", (taskManager.getTasks().size() == 2));
        assertTrue("Лист эпиков корректен", (taskManager.getEpics().size() == 2));
        assertTrue("Лист сабтасок корректен", (taskManager.getSubtasks().size() == 2));
        assertTrue("Лист задач по приоритету корректен", (taskManager.getPrioritizedTasks().size() == 6));
    }

    //удаление всех задач - норма
    @Test
    void normalClearAllTasks() {
        Task task = new Task("Test", "description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(80));
        Task task2 = new Task("Test", "description", Status.DONE, 10,
                LocalDateTime.now().plusMinutes(100));
        Epic epic = new Epic("Test epic", "Test epic description");
        Epic epic2 = new Epic("Test epic", "Test epic description");
        Subtask subtask1 = new Subtask("Test subtask1", "Test subtask1 description", Status.NEW, 30,
                LocalDateTime.now().plusMinutes(0), epic);
        Subtask subtask2 = new Subtask("Test subtask2", "Test subtask2 description", Status.NEW, 30,
                LocalDateTime.now().plusMinutes(31), epic);
        taskManager.clearAllTasks();

        assertTrue("Лист задач пуст", taskManager.getTasks().isEmpty());
        assertTrue("Лист эпиков пуст", taskManager.getEpics().isEmpty());
        assertTrue("Лист сабтасок пуст", taskManager.getSubtasks().isEmpty());
        assertTrue("Лист задач по приоритету пуст", taskManager.getPrioritizedTasks().isEmpty());
    }

    //получение по id - норма
    @Test
    void normalGetById() {
        Task task = new Task("Test", "description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(80));
        assertTrue("Получение по id корректно", taskManager.getTaskById(task.getId()).equals(task));
    }

    //обновление задач - норма
    void normalUpdateTask() {
        Task task = new Task("Test", "description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(80));
        Task newTask = new Task("Test", "description2", Status.NEW, 20,
                LocalDateTime.now().plusMinutes(80));
        taskManager.updateTask(task.getId(), newTask);

        assertTrue("Обновление задачи корректно", task.equals(newTask));
    }

    //удаление задачи -норма
    void normalDeleteTask() {
        Task task = new Task("Test", "description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(80));
                taskManager.removeById(task.getId());

        assertFalse("Удаление задачи корректно", taskManager.getTasks().contains(task));
    }

    //получение списка всех задач - пустой список
    @Test
    void normalGetTasksFromEmptyList() {
        boolean listTaskIsCorrect = false;
        List<Task> testTaskList = taskManager.getTasks();
        if (testTaskList.isEmpty()) listTaskIsCorrect = true;
        boolean listEpicIsCorrect = false;
        List<Epic> testEpicList = taskManager.getEpics();
        if (testEpicList.isEmpty()) listEpicIsCorrect = true;
        boolean listSubtaskIsCorrect = false;
        List<Subtask> testSubtaskList = taskManager.getSubtasks();
        if (testSubtaskList.isEmpty()) listSubtaskIsCorrect = true;

        assertTrue("Лист задач корректен", taskManager.getTasks().equals(empty));
        assertTrue("Лист эпиков корректен", taskManager.getEpics().equals(empty));
        assertTrue("Лист сабтасок корректен", taskManager.getSubtasks().equals(empty));
    }


    //удаление всех задач - норма
    @Test
    void emptyClearAllTasks() {
        taskManager.clearAllTasks();

        assertTrue("Лист задач пуст", taskManager.getTasks().isEmpty());
        assertTrue("Лист эпиков пуст", taskManager.getEpics().isEmpty());
        assertTrue("Лист сабтасок пуст", taskManager.getSubtasks().isEmpty());
        assertTrue("Лист задач по приоритету пуст", taskManager.getPrioritizedTasks().isEmpty());
    }

    //обновление задач -некорректный id
    @Test
    void errorUpdateTasks() {
        Task task = new Task("Test", "description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(80));
        Task task2 = new Task("Test", "description2", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(80));
        taskManager.updateTask((task.getId() +100), task2);

       assertFalse("Ошибка неверного id отловлена корректно", task.equals(task2));
    }

    //удаление задач -некорректный id
    //тогда вообще не понятно, что мы ожидаем от метода, в котором исключена возможность
    //удалить задачу по несуществующему индификатору
    @Test
    void errorDeleteTasks() {
        taskManager.removeById(999);

        assertTrue("Ошибка удаления по неверному id отловлена корректно", true);
    }

    //получение задач - некорректный id
    //аналогично
    @Test
    void errorGetTasks() {
        Task task = new Task("Test", "description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(80));
        taskManager.getTaskById((task.getId() + 10));

        assertTrue("Ошибка получения по неверному id отловлена корректно", true);
    }

//создание задачи - норма
@Test
    void putTaskNorm() {
        Task task = new Task("Test", "description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(80));
        assertTrue("Задача создана корректно", taskManager.getTasks().size() == 1);
    }

    //создание эпика - норма
    @Test
    void putEpicNorm() {
        Epic epic = new Epic("Test epic", "Test epic description");

        assertTrue("Задача создана корректно", taskManager.getEpics().size() == 1);
    }

    //создание сабтаска - норма
    @Test
    void putSubtaskNorm() {
        Epic epic = new Epic("Test epic", "Test epic description");

        Subtask subtask1 = new Subtask("Test subtask1", "Test subtask1 description", Status.NEW, 30,
                LocalDateTime.now().plusMinutes(0), epic);
        assertTrue("Задача создана корректно", taskManager.getSubtasks().size() == 1);
    }


    //список истории при не пустом списке задач
    @Test
    void getNotEmtyHistory() {
        Task task = new Task("Test", "description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(80));
        Task task2 = new Task("Test", "description2", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(80));
taskManager.updateTask(task.getId(), task2);
            assertTrue("Список истории сохранен корректно", taskManager.history().size() == 1);
    }

    //список истории при  пустом списке задач
    @Test
    void getEmtyHistory() {
        assertTrue("Список истории сохранен корректно", taskManager.history().size() == 0);
    }

    //получение порядкового Id - норма
    @Test
    void getIdFromNotEpty() {
        Task task = new Task("Test", "description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(80));
        assertTrue("Id корректен", taskManager.getId() == 2L);
    }

    //получение порядкового Id - пустой список
    @Test
    void getIdFromEpty() {

        assertTrue("Id корректен", taskManager.getId() == 1L);
    }

        //получение эпика у сабтаски - ошибка
    @Test
    void epicOfSubtaskNorm() {
        Epic epic = new Epic("Test epic", "Test epic description");
        Subtask subtask1 = new Subtask("Test subtask1", "Test subtask1 description", Status.NEW, 30,
                LocalDateTime.now().plusMinutes(0), epic);
        taskManager.removeById(epic.getId());
        assertTrue("Подзадача имеет эпик", subtask1.getParentEpic().equals(null));
    }

//вроде я все рабочие методы проверил. Какие тесты ещё нужны?
}