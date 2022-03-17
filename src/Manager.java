import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Manager {
    public Long id = 0L;


    HashMap<Long, Task> taskMap = new HashMap<>();
    HashMap<Long, Epic> epicMap = new HashMap<>();
    HashMap<Long, Subtask> subtaskMap = new HashMap<>();


    public HashMap<Long, Epic> getEpicMap() {
        return epicMap;
    }

    public HashMap<Long, Subtask> getSubtaskMap() {
        return subtaskMap;
    }

    public HashMap<Long, Task> getTaskMap() {
        return taskMap;
    }

//Получения списка всех задач для каждого типа

    public List<Task> getTasks() {
        return new ArrayList<>(taskMap.values());
    }

    public List<Epic> getEpics() {
        return new ArrayList<>(epicMap.values());
    }

    public List<Subtask> getSubtasks() {
        return new ArrayList<>(subtaskMap.values());
    }

    //Удаление всех задач
    public void clearTasks() {
        taskMap.clear();
    }

    public void clearEpics() {
        epicMap.clear();

    }

    public void clearSubtasks() {

        subtaskMap.clear();
    }

    //Получение по индефикатору
    public Task getById(long getId) {
        if (getId < 0 || getId > id) {
            System.out.println("Некорректный id");
            return null;
        } else if (epicMap.containsKey(getId)) {
            return epicMap.get(getId);
        } else if (subtaskMap.containsKey(getId)) {
            return subtaskMap.get(getId);
        } else {
            return taskMap.get(getId);
        }
    }

    // Созданние.
    public void putTask(Task task) {
        taskMap.put(id, task);
    }

    public void putEpic(Epic epic) {
        epicMap.put(id, epic);
    }

    public void putSubtask(Subtask subtask) {
        subtaskMap.put(id, subtask);
        (subtask.getParentEpic()).getIncludedSabtaks().add(subtask);
        subtask.getParentEpic().updateStatusEpic();
    }

    public Long nextId() {
 Long newId = id;
 id ++;
        return newId;

    }


    //Обновление
    public void updateTask(Long replaceId, Task replaceTask) {
        if (replaceId < 1 || replaceId > id) {
            System.out.println("Некорректный ввод id");
        } else if (epicMap.containsKey(replaceId)) {
            epicMap.put(replaceId, (Epic) replaceTask);
        } else if (subtaskMap.containsKey(replaceId)) {
            subtaskMap.put(replaceId, (Subtask) replaceTask);
            (((Subtask) replaceTask).getParentEpic()).getIncludedSabtaks().add((Subtask) replaceTask);
            ((Subtask) replaceTask).getParentEpic().updateStatusEpic();
        } else if (taskMap.containsKey(replaceId)) {
            taskMap.put(replaceId, replaceTask);
        } else {
            System.out.println("Задачи с таким id не найдено. Вероятно, она была удалена");
        }
    }


    //Удаление по индефикатору
    public void removeById(long getId) {
        if (getId < 1 || getId > id) {
            System.out.println("Некорректный ввод id");
        } else if (epicMap.containsKey(getId)) {
            epicMap.remove(getId);
        } else if (subtaskMap.containsKey(getId)) {
            subtaskMap.remove(getId);
        } else if (taskMap.containsKey(getId)) {
            taskMap.remove(getId);
        } else {
            System.out.println("Задачи с таким id не существует. Вероятно, она уже была удалена");
        }

    }

    public void printAllTasks() {
        printTasks();
        printEpic();
        printSubtask();
    }

    public void printTasks() {
        if (taskMap.isEmpty()) {
            System.out.println("Список Задач пуст");
        } else {
            for (Task i : taskMap.values()) {
                System.out.println(i.title);
            }
        }
    }

    public void printEpic() {
        if (epicMap.isEmpty()) {
            System.out.println("Список Эпиков пуст");
        } else {
            for (Task i : epicMap.values()) {
                System.out.println(i.title);
            }
        }
    }

    public void printSubtask() {
        if (subtaskMap.isEmpty()) {
            System.out.println("Список Подзадач пуст");
        } else {
            for (Task i : subtaskMap.values()) {
                System.out.println(i.title);
            }
        }
    }

    public Task getTaskById(Long desiredId) {
        if (desiredId < 1 || desiredId > id) {
            System.out.println("Некорректный ввод id");
            return null;
        } else if (epicMap.containsKey(desiredId)) {
            return (Task) epicMap.get(desiredId);
        } else if (subtaskMap.containsKey(desiredId)) {
            return (Task) subtaskMap.get(desiredId);
        } else if (taskMap.containsKey(desiredId)) {
            return taskMap.get(desiredId);
        } else {
            System.out.println("Задачи с таким id не найдено. Вероятно, она была удалена");
            return null;
        }
    }

/*
   + Возможность хранить задачи всех типов. Для этого вам нужно выбрать подходящую коллекцию.
        Методы для каждого из типа задач(Задача/Эпик/Подзадача):
    + Получение списка всех задач.
   + Удаление всех задач. тоже самое, только через клиа
    + Получение по идентификатору. contains, вначале переменную Таск, присвоив нулл. Или можно 3 метода.
    +Создание. Сам объект должен передаваться в качестве параметра.





 */


}
