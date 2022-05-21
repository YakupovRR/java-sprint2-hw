package tests;

import manager.TaskManager;
import org.testng.annotations.Test;
import tasktypes.Epic;
import tasktypes.Status;
import tasktypes.Subtask;
import tasktypes.Task;

import java.lang.reflect.Executable;
import java.time.LocalDateTime;

import static jdk.javadoc.internal.doclets.toolkit.util.DocPath.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testng.AssertJUnit.*;


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
        taskManager.putTask(task);
        taskManager.putTask(task2);
        taskManager.putEpic(epic);
        taskManager.putEpic(epic2);
        taskManager.putSubtask(subtask1);
        taskManager.putSubtask(subtask2);

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
        taskManager.putTask(task);
        taskManager.putTask(task2);
        taskManager.putEpic(epic);
        taskManager.putEpic(epic2);
        taskManager.putSubtask(subtask1);
        taskManager.putSubtask(subtask2);
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
        taskManager.putTask(task);

        assertTrue("Получение по id корректно", taskManager.getTaskById(task.getId()).equals(task));
    }

    //обновление задач - норма
    void normalUpdateTask() {
        Task task = new Task("Test", "description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(80));
        Task newTask = new Task("Test", "description2", Status.NEW, 20,
                LocalDateTime.now().plusMinutes(80));
        taskManager.putTask(task);
        taskManager.updateTask(task.getId(), newTask);

        assertTrue("Обновление задачи корректно", task.equals(newTask));
    }

    //удаление задачи -норма
    void normalDeleteTask() {
        Task task = new Task("Test", "description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(80));
        taskManager.putTask(task);
        taskManager.removeById(task.getId());

        assertFalse("Удаление задачи корректно", taskManager.getTasks().contains(task));
    }

    //получение списка всех задач - пустой список
    @Test
    void normalGetTasksFromEmptyList() {

        assertTrue("Лист задач корректен", taskManager.getTasks().equals(empty));
        assertTrue("Лист эпиков корректен", taskManager.getEpics().equals(empty));
        assertTrue("Лист сабтасок корректен", taskManager.getSubtasks().equals(empty));
        assertTrue("Лист сабтасок корректен", taskManager.getPrioritizedTasks().equals(empty));
    }


    //удаление всех задач - норма
    @Test
    void emptyClearAllTasks() {
        taskManager.clearAllTasks();

        assertTrue("Лист задач пуст", taskManager.getTasks().size() == 0);
        assertTrue("Лист эпиков пуст", taskManager.getEpics().size() == 0);
        assertTrue("Лист сабтасок пуст", taskManager.getSubtasks().size() == 0);
        assertTrue("Лист задач по приоритету пуст", taskManager.getPrioritizedTasks().size() == 0);
    }

    //обновление задач -некорректный id
    @Test
    void errorUpdateTasks() {
        Task task = new Task("Test", "description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(80));
        Task task2 = new Task("Test", "description2", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(80));
        taskManager.putTask(task);
        taskManager.putTask(task2);
        taskManager.updateTask((task.getId() + 100), task2);

        assertFalse("Ошибка неверного id отловлена корректно", task.equals(task2));
    }

    //удаление задач -некорректный id
    @Test
    void errorDeleteTasks() throws Exception {
        final IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        taskManager.removeById(0L);
                    }
                });
        assertEquals("Некорректный ввод id", exception.getMessage());
    }

    //получение задач - некорректный id
    @Test
    void errorGetTasks() {
        assertNull("Задачи с таким id не существует", taskManager.getTaskById(0L));
    }

    //создание задачи - норма
    @Test
    void putTaskNorm() {
        Task task = new Task("Test", "description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(80));
        taskManager.putTask(task);
        assertTrue("Задача создана корректно", taskManager.getPrioritizedTasks().contains(task));
    }

    //создание эпика - норма
    @Test
    void putEpicNorm() {
        Epic epic = new Epic("Test epic", "Test epic description");
        taskManager.putEpic(epic);
        assertTrue("Задача создана корректно", taskManager.getPrioritizedTasks().contains(epic));
    }

    //создание сабтаска - норма
    @Test
    void putSubtaskNorm() {
        Epic epic = new Epic("Test epic", "Test epic description");
        taskManager.putEpic(epic);
        Subtask subtask1 = new Subtask("Test subtask1", "Test subtask1 description", Status.NEW, 30,
                LocalDateTime.now().plusMinutes(0), epic);
        taskManager.putSubtask(subtask1);
        assertTrue("Задача создана корректно", taskManager.getPrioritizedTasks().contains(subtask1));

    }

    //список истории при не пустом списке задач
    @Test
    void getNotEmtyHistory() {
        Task task = new Task("Test", "description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(80));
        Task task2 = new Task("Test", "description2", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(80));
        taskManager.putTask(task);
        taskManager.putTask(task2);
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
        taskManager.putTask(task);

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
        taskManager.putEpic(epic);
        Subtask subtask1 = new Subtask("Test subtask1", "Test subtask1 description", Status.NEW, 30,
                LocalDateTime.now().plusMinutes(0), epic);
        taskManager.putSubtask(subtask1);
        taskManager.removeById(epic.getId());
        assertTrue("Подзадача имеет эпик", subtask1.getParentEpic().equals(null));
    }

    //время эпика без сабтасок
    @Test
    void timesOfEpicWithoutSubtasks() {
        Epic epic = new Epic("Test epic", "Test epic description");
        taskManager.putEpic(epic);

        assertNull("Эпик не имеет starTime", epic.getStartTime());
        assertNull("Эпик не имеет duration", epic.getDuration());
        assertNull("Эпик не имеет endTime", epic.getEndTime());
    }

    //время эпика при добавлении сабтасок
    @Test
    void timesOfEpicWithSubtasks() {
        Epic epic = new Epic("Test epic", "Test epic description");
        taskManager.putEpic(epic);
        Subtask subtask1 = new Subtask("Test subtask1", "Test subtask1 description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(0), epic);
        Subtask subtask2 = new Subtask("Test subtask1", "Test subtask1 description", Status.NEW, 20,
                LocalDateTime.now().plusMinutes(30), epic);
        taskManager.putSubtask(subtask1);
        taskManager.putSubtask(subtask2);

        assertTrue("starTime корректен", epic.getStartTime().equals(LocalDateTime.now()));
        assertTrue("duration корректен", epic.getStartTime().equals(50));
        assertTrue("endTime корректен", epic.getEndTime().equals(LocalDateTime.now().plusMinutes(50)));
    }

    //время эпика при удалении одной из сабтасок
    @Test
    void timesOfEpicWithDeletedOneSubtask() {
        Epic epic = new Epic("Test epic", "Test epic description");
        taskManager.putEpic(epic);
        Subtask subtask1 = new Subtask("Test subtask1", "Test subtask1 description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(0), epic);
        Subtask subtask2 = new Subtask("Test subtask1", "Test subtask1 description", Status.NEW, 20,
                LocalDateTime.now().plusMinutes(30), epic);
        taskManager.putSubtask(subtask1);
        taskManager.putSubtask(subtask2);
        taskManager.removeById(subtask2.getId());

        assertTrue("starTime корректен", epic.getStartTime().equals(LocalDateTime.now()));
        assertTrue("duration корректен", epic.getStartTime().equals(10));
        assertTrue("endTime корректен", epic.getEndTime().equals(LocalDateTime.now().plusMinutes(10)));
    }

    //время эпика при удалении всех сабтасок
    @Test
    void timesOfEpicWithDeletedAllSubtasks() {
        Epic epic = new Epic("Test epic", "Test epic description");
        taskManager.putEpic(epic);
        Subtask subtask1 = new Subtask("Test subtask1", "Test subtask1 description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(0), epic);
        Subtask subtask2 = new Subtask("Test subtask1", "Test subtask1 description", Status.NEW, 20,
                LocalDateTime.now().plusMinutes(30), epic);
        taskManager.putSubtask(subtask1);
        taskManager.putSubtask(subtask2);
        taskManager.removeById(subtask2.getId());
        taskManager.removeById(subtask1.getId());

        assertNull("starTime корректен", epic.getStartTime());
        assertNull("duration корректен", epic.getDuration());
        assertNull("endTime корректен", epic.getEndTime());
    }

    //время эпика при обновлении сабтасок
    void timesOfEpicWithUpdateSubtasks() {
        Epic epic = new Epic("Test epic", "Test epic description");
        taskManager.putEpic(epic);
        Subtask subtask1 = new Subtask("Test subtask1", "Test subtask1 description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(0), epic);
        Subtask subtask2 = new Subtask("Test subtask1", "Test subtask1 description", Status.NEW, 20,
                LocalDateTime.now().plusMinutes(30), epic);
        taskManager.putSubtask(subtask1);
        taskManager.putSubtask(subtask2);
        taskManager.updateTask(subtask1.getId(), new Subtask("Test subtask1", "Test subtask1 description",
                Status.NEW, 10, LocalDateTime.now().plusMinutes(1), epic));
        taskManager.updateTask(subtask2.getId(), new Subtask("Test subtask1", "Test subtask1 description",
                Status.NEW, 30, LocalDateTime.now().plusMinutes(30), epic));

        assertTrue("starTime корректен", epic.getStartTime().equals(LocalDateTime.now().plusMinutes(1)));
        assertTrue("duration корректен", epic.getStartTime().equals(59));
        assertTrue("endTime корректен", epic.getEndTime().equals(LocalDateTime.now().plusMinutes(60)));
    }

    //правильность сортировки в трисет
    @Test
    void correctPrioritizedTasks() {
        Task task = new Task("Test", "description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(0));
        Task task2 = new Task("Test", "description", Status.DONE, 10,
                LocalDateTime.now().plusMinutes(20));
        Task task3 = new Task("Test", "description", Status.DONE, 10,
                LocalDateTime.now().plusMinutes(40));
        taskManager.putTask(task);
        taskManager.putTask(task2);
        taskManager.putTask(task3);

        assertTrue("1-ая позиция в списке корректная", taskManager.getPrioritizedTasks().first().equals(task));
        assertTrue("Последняя позиция в списке корректная",
                taskManager.getPrioritizedTasks().last().equals(task3));
    }

    //правильность сортировки в трисет при удалении
    @Test
    void correctPrioritizedTasksWithDeletedTask() {
        Task task = new Task("Test", "description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(0));
        Task task2 = new Task("Test", "description", Status.DONE, 10,
                LocalDateTime.now().plusMinutes(20));
        Task task3 = new Task("Test", "description", Status.DONE, 10,
                LocalDateTime.now().plusMinutes(40));
        Task task4 = new Task("Test", "description", Status.DONE, 10,
                LocalDateTime.now().plusMinutes(60));
        taskManager.putTask(task);
        taskManager.putTask(task2);
        taskManager.putTask(task3);
        taskManager.putTask(task4);
        taskManager.removeById(task.getId());
        taskManager.removeById(task4.getId());

        assertTrue("1-ая позиция в списке корректная", taskManager.getPrioritizedTasks().first().equals(task2));
        assertTrue("Последняя позиция в списке корректная",
                taskManager.getPrioritizedTasks().last().equals(task3));
    }

    //правильность сортировки в трисет при обновлении тасок
    @Test
    void correctPrioritizedTasksWithUpdateTask() {
        Task task = new Task("Test", "description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(0));
        Task task2 = new Task("Test", "description", Status.DONE, 10,
                LocalDateTime.now().plusMinutes(20));
        taskManager.putTask(task);
        taskManager.putTask(task2);
        taskManager.updateTask(task.getId(), new Task("Test", "description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(50)));

        assertTrue("1-ая позиция в списке корректная", taskManager.getPrioritizedTasks().first().equals(task2));
        assertTrue("Последняя позиция в списке корректная",
                taskManager.getPrioritizedTasks().last().equals(task));
    }

    //корректность работы isNotСrossing() при добавлении без пересечения
    @Test
    void correctIsNotСrossingWithoutCrossing() {
        Task task = new Task("Test", "description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(0));
        Task task2 = new Task("Test", "description", Status.DONE, 10,
                LocalDateTime.now().plusMinutes(60));

        Task task3 = new Task("Test", "description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(20));
        taskManager.putTask(task);
        taskManager.putTask(task2);
        taskManager.putTask(task3);

        assertTrue(taskManager.isNotСrossing(task3));
        assertTrue(taskManager.getPrioritizedTasks().contains(task3));
    }

    //корректность работы isNotСrossing() при добавлении с пересечениями
    @Test
    void correctIsNotСrossingWithCrossing() {
        Task task = new Task("Test", "description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(0));
        Task task2 = new Task("Test", "description", Status.DONE, 10,
                LocalDateTime.now().plusMinutes(60));
        Task task3 = new Task("Test", "description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(5));
        Task task4 = new Task("Test", "description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(65));
        taskManager.putTask(task);
        taskManager.putTask(task2);
        taskManager.putTask(task3);
        taskManager.putTask(task4);

        assertFalse(taskManager.isNotСrossing(task3));
        assertFalse(taskManager.isNotСrossing(task4));
        assertFalse(taskManager.getPrioritizedTasks().contains(task3));
        assertFalse(taskManager.getPrioritizedTasks().contains(task4));
    }

    //корректность работы isNotСrossing() при добавлении задачи, "пересекающейся" с уже удаленной задачей
    @Test
    void correctIsNotСrossingWithCrossingWithDeletedTask() {
        Task task = new Task("Test", "description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(0));
        Task task2 = new Task("Test", "description", Status.DONE, 10,
                LocalDateTime.now().plusMinutes(60));
        Task task4 = new Task("Test", "description", Status.NEW, 10,
                LocalDateTime.now().plusMinutes(65));
        taskManager.putTask(task);
        taskManager.putTask(task2);
        taskManager.removeById(task2.getId());
        taskManager.putTask(task4);

        assertTrue(taskManager.isNotСrossing(task4));
        assertTrue(taskManager.getPrioritizedTasks().contains(task4));
    }

}