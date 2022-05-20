package manager;

import org.testng.annotations.Test;
import tasktypes.Epic;
import tasktypes.Status;
import tasktypes.Subtask;
import tasktypes.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import static org.testng.AssertJUnit.assertTrue;

abstract class TaskManagerTest<T extends TaskManager> {
    T taskManager;

    public TaskManagerTest(T taskManager) {
        this.taskManager = taskManager;
    }

    //наличие эпика у подзадачи
    @Test
    void subtaskaHaveEpic() {
        Epic epic = new Epic("Test epic", "Test epic description");
        Subtask subtask1 = new Subtask("Test subtask1", "Test subtask1 description", Status.NEW, 30,
                LocalDateTime.now().plusMinutes(0), epic);

        boolean subtaslHaveEpic = true;
        if (subtask1.getParentEpic().equals(null)) subtaslHaveEpic = false;
        assertTrue("Подзадача имеет эпик", subtaslHaveEpic);
    }

    //проверка статуса эпика
    @Test
    void epicStatus() {
        Epic epic = new Epic("Test epic", "Test epic description");
        boolean stasusEmptyEpic = false;
        if (epic.getStatus().equals(Status.NEW)) stasusEmptyEpic = true;
        Subtask subtask1 = new Subtask("Test subtask1", "Test subtask1 description", Status.NEW, 30,
                LocalDateTime.now().plusMinutes(0), epic);
        boolean stasusNewEpic = false;
        if (epic.getStatus().equals(Status.NEW)) stasusNewEpic = true;
        subtask1.setStatus(Status.DONE);
        boolean stasusDoneEpic = false;
        if (epic.getStatus().equals(Status.DONE)) stasusDoneEpic = true;
        Subtask subtask2 = new Subtask("Test subtask2", "Test subtask2 description", Status.NEW, 30,
                LocalDateTime.now().plusMinutes(31), epic);
        boolean stasusInProgressEpic = false;
        if (epic.getStatus().equals(Status.IN_PROGRESS)) stasusInProgressEpic = true;
        assertTrue("Статус пустого эпика корректен", stasusEmptyEpic);
        assertTrue("Статус нового эпика корректен", stasusNewEpic);
        assertTrue("Статус завершенного эпика корректен", stasusDoneEpic);
        assertTrue("Статус эпика в процессе корректен", stasusInProgressEpic);
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

        boolean listTaskIsCorrect = false;
        if (taskManager.getTasks().contains(task) && taskManager.getTasks().contains(task2)) listTaskIsCorrect = true;
        boolean listEpicIsCorrect = false;
        if (taskManager.getEpics().contains(epic) && taskManager.getTasks().contains(epic2)) listEpicIsCorrect = true;
        boolean listSubtaskIsCorrect = false;
        if (taskManager.getEpics().contains(subtask1) && taskManager.getTasks().contains(subtask2)) listSubtaskIsCorrect
                = true;
        assertTrue("Лист задач корректен", listTaskIsCorrect);
        assertTrue("Лист эпиков корректен", listEpicIsCorrect);
        assertTrue("Лист сабтасок корректен", listSubtaskIsCorrect);
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
        boolean listTaskIsCorrect = false;
        if (taskManager.getTasks().isEmpty()) listTaskIsCorrect = true;
        boolean listEpicIsCorrect = false;
        if (taskManager.getEpics().isEmpty()) listEpicIsCorrect = true;
        boolean listSubtaskIsCorrect = false;
        if (taskManager.getSubtasks().isEmpty()) listSubtaskIsCorrect = true;

        assertTrue("Лист задач пуст", listTaskIsCorrect);
        assertTrue("Лист эпиков пуст", listEpicIsCorrect);
        assertTrue("Лист сабтасок пуст", listSubtaskIsCorrect);
    }

    //получение по id -норма
    @Test
    void normalGetById() {
        Task task = new Task("Test", "description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(80));
        Epic epic = new Epic("Test epic", "Test epic description");
        Subtask subtask1 = new Subtask("Test subtask1", "Test subtask1 description", Status.NEW, 30,
                LocalDateTime.now().plusMinutes(0), epic);
        boolean isCorrect = true;
        if (!taskManager.getTaskById(task.getId()).equals(task)) isCorrect = false;
        if (!taskManager.getTaskById(epic.getId()).equals(epic)) isCorrect = false;
        if (!taskManager.getTaskById(subtask1.getId()).equals(subtask1)) isCorrect = false;
    }

    //обновление задач -норма
    void normalUpdateTask() {
        Task task = new Task("Test", "description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(80));
        Task newTask = new Task("Test", "description2", Status.NEW, 20,
                LocalDateTime.now().plusMinutes(80));

        taskManager.updateTask(task.getId(), newTask);
        boolean isCorrect = false;
        if (task.equals(newTask)) isCorrect = true;
        assertTrue("Обновление задачи корректно", isCorrect);
    }

    //удаление задач -норма
    void normalDeleteTask() {
        Task task = new Task("Test", "description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(80));
        taskManager.removeById(task.getId());
        boolean isCorrect = true;
        if (taskManager.getTasks().contains(task)) isCorrect = false;
        assertTrue("Удаление задачи корректно", isCorrect);
    }


    //получение списка всех задач - пустой список
    @Test
    void normalGetTasks() {
        boolean listTaskIsCorrect = false;
        List<Task> testTaskList = taskManager.getTasks();
        if (testTaskList.isEmpty()) listTaskIsCorrect = true;
        boolean listEpicIsCorrect = false;
        List<Epic> testEpicList = taskManager.getEpics();
        if (testEpicList.isEmpty()) listEpicIsCorrect = true;
        boolean listSubtaskIsCorrect = false;
        List<Subtask> testSubtaskList = taskManager.getSubtasks();

        if (testSubtaskList.isEmpty()) listSubtaskIsCorrect = true;
        assertTrue("Лист задач корректен", listTaskIsCorrect);
        assertTrue("Лист эпиков корректен", listEpicIsCorrect);
        assertTrue("Лист сабтасок корректен", listSubtaskIsCorrect);
    }

    //обновление задач -некорректный id
    //не совсем понятно, что мы собрались проверять, если некорректные id у нас отловлены уже в методе
    void errorUpdateTasks() {
        Task task = new Task("Test", "description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(80));
        Task task2 = new Task("Test", "description2", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(80));
        Long errorId = task.getId() + 10;
        Scanner scanner = new Scanner(System.in);
        String errorMessage;
        taskManager.updateTask(task.getId(), task2);
        errorMessage = scanner.nextLine();
        boolean isCorrect = false;
        if (errorMessage.equals("Некорректный ввод id обновляемой задачи") ||
                errorMessage.equals("Задачи с таким id не найдено. Возможно, она была удалена")) {
            isCorrect = true;
        }
        assertTrue("Ошибка неверного id отловлена корректно", isCorrect);
    }

    //удаление задач -некорректный id
    void errorDeleteTasks() {
        Task task = new Task("Test", "description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(80));
        Long removedId = task.getId();
        taskManager.removeById(removedId);
        taskManager.removeById(removedId);
        Scanner scanner = new Scanner(System.in);
        String errorMessage;
        errorMessage = scanner.nextLine();
        boolean isCorrect = false;
        if (errorMessage.equals("Некорректный ввод id") ||
                errorMessage.equals("Задачи с таким id не существует. Вероятно, она уже была удалена")) {
            isCorrect = true;
        }
        assertTrue("Ошибка удаления по неверному id отловлена корректно", isCorrect);
    }

    //получение задач -некорректный id

    void errorGetTasks() {
        Task task = new Task("Test", "description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(80));
        Long errorId = task.getId() + 10;
        taskManager.getTaskById(errorId);
        Scanner scanner = new Scanner(System.in);
        String errorMessage;
        errorMessage = scanner.nextLine();
        boolean isCorrect = false;
        if (errorMessage.equals("Некорректный ввод id") ||
                errorMessage.equals("Задачи с таким id не найдено. Вероятно, она была удалена")) {
            isCorrect = true;
        }
        assertTrue("Ошибка получения по неверному id отловлена корректно", isCorrect);
    }

}