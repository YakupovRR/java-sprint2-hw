import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Manager {
    public Long id = 1L;
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

    public List<Task> getTasks() {      //Получения списка всех задач для каждого типа
        if (taskMap.isEmpty()) {
            System.out.println("Список Задач пуст");
            return null;
        } else {
            return new ArrayList<>(taskMap.values());
        }
    }

    public List<Epic> getEpics() {
        if (epicMap.isEmpty()) {
            System.out.println("Список Эпиков пуст");
            return null;
        } else {
            return new ArrayList<>(epicMap.values());
        }
    }

    public List<Subtask> getSubtasks() {
        if (taskMap.isEmpty()) {
            System.out.println("Список Задач пуст");
            return null;
        } else {
            return new ArrayList<>(subtaskMap.values());
        }
    }

    public void clearAllTasks() {            //Удаление всех задач
        clearTasks();
        clearEpics();
        clearSubtasks();
    }

    public void clearTasks() {
        taskMap.clear();
    }

    public void clearEpics() {
        subtaskMap.clear();   //на случай, если вдруг решим удалить только эпики, а такси оставить
        epicMap.clear();
    }

    public void clearSubtasks() {
        subtaskMap.clear();
        for (Epic i : epicMap.values()) { //на случай, если вдруг решим удалить только сабтаски, а эпики оставить
            i.getincludedSubtaks().clear();
            i.updateStatusEpic();
        }
    }

    public Task getTaskById(Long desiredId) {              //получение задачи по id
        if (desiredId < 1 || desiredId>id) {
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

    public void putTask(Task task) {                    // Созданние.
        taskMap.put(id, task);
        id++;
    }

    public void putEpic(Epic epic) {
        epicMap.put(id, epic);
        id++;
    }

    public void putSubtask(Subtask subtask) {
        subtaskMap.put(id, subtask);
        (subtask.getParentEpic()).addNewSubtusk(subtask);
        subtask.getParentEpic().updateStatusEpic();
        id++;
    }

    public void updateTask(Long replaceId, Task replaceTask) {       //Обновление задачи
        if (replaceId < 1 || replaceId>id) {
            System.out.println("Некорректный ввод id обновляемой задачи");
        } else if (epicMap.containsKey(replaceId)) {
            epicMap.put(replaceId, (Epic) replaceTask);
        } else if (subtaskMap.containsKey(replaceId)) {
            subtaskMap.put(replaceId, (Subtask) replaceTask);
            Epic parentEpic = ((Subtask) replaceTask).getParentEpic();
            parentEpic.addNewSubtusk((Subtask) replaceTask);
            parentEpic.updateStatusEpic();
        } else if (taskMap.containsKey(replaceId)) {
            taskMap.put(replaceId, replaceTask);
        } else {
            System.out.println("Задачи с таким id не найдено. Возможно, она была удалена");
        }
    }

    public void removeById(long getId) {                  //Удаление по индефикатору
        if (getId < 1 || getId> id) {
            System.out.println("Некорректный ввод id");
        } else if (epicMap.containsKey(getId)) {
            epicMap.remove(getId);
        } else if (subtaskMap.containsKey(getId)) {
            Subtask deleteSubtask = subtaskMap.remove(getId);
            deleteSubtask.getParentEpic().deleteFromincludedSubtaks(deleteSubtask);
            deleteSubtask.getParentEpic().updateStatusEpic();
        } else if (taskMap.containsKey(getId)) {
            taskMap.remove(getId);
        } else {
            System.out.println("Задачи с таким id не существует. Вероятно, она уже была удалена");
        }
    }

    public List<Subtask> getSubtaskOfEpic(Epic epic) {
        if (epic != null) {
            return epic.getincludedSubtaks();
        } else {
            System.out.println("Эпик пуст");
            return null;
        }
    }

}

