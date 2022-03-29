package manager;

import history.HistoryManager;
import tasktypes.Epic;
import tasktypes.Subtask;
import tasktypes.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


class InMemoryTaskManager implements TaskManager {
    private Long id = 1L;
    HashMap<Long, Task> taskMap = new HashMap<>();
    HashMap<Long, Epic> epicMap = new HashMap<>();
    HashMap<Long, Subtask> subtaskMap = new HashMap<>();
    HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public HashMap<Long, Epic> getEpicMap() {
        return epicMap;
    }

    @Override
    public HashMap<Long, Subtask> getSubtaskMap() {
        return subtaskMap;
    }

    @Override
    public HashMap<Long, Task> getTaskMap() {
        return taskMap;
    }

    @Override
    public List<Task> getTasks() {      //Получения списка всех задач для каждого типа
        if (taskMap.isEmpty()) {
            System.out.println("Список Задач пуст");
            return null;
        } else {
            return new ArrayList<>(taskMap.values());
        }
    }

    @Override
    public List<Epic> getEpics() {
        if (epicMap.isEmpty()) {
            System.out.println("Список Эпиков пуст");
            return null;
        } else {
            return new ArrayList<>(epicMap.values());
        }
    }

    @Override
    public List<Subtask> getSubtasks() {
        if (taskMap.isEmpty()) {
            System.out.println("Список Задач пуст");
            return null;
        } else {
            return new ArrayList<>(subtaskMap.values());
        }
    }

    @Override
    public void clearAllTasks() {            //Удаление всех задач
        clearTasks();
        clearEpics();
        clearSubtasks();
    }

    @Override
    public void clearTasks() {
        taskMap.clear();
    }

    @Override
    public void clearEpics() {
        subtaskMap.clear();   //на случай, если вдруг решим удалить только эпики, а такси оставить
        epicMap.clear();
    }

    @Override
    public void clearSubtasks() {
        subtaskMap.clear();
        for (Epic i : epicMap.values()) { //на случай, если вдруг решим удалить только сабтаски, а эпики оставить
            i.getincludedSubtaks().clear();
            i.updateStatusEpic();
        }
    }

    @Override
    public Task getTaskById(Long desiredId) {              //получение задачи по id
        if (desiredId < 1 || desiredId > id) {
            System.out.println("Некорректный ввод id");
            return null;
        } else if (epicMap.containsKey(desiredId)) {
            historyManager.add((Task) epicMap.get(desiredId));
            return (Task) epicMap.get(desiredId);
        } else if (subtaskMap.containsKey(desiredId)) {
            historyManager.add((Task) subtaskMap.get(desiredId));
            return (Task) subtaskMap.get(desiredId);
        } else if (taskMap.containsKey(desiredId)) {
            historyManager.add(taskMap.get(desiredId));
            return taskMap.get(desiredId);
        } else {
            System.out.println("Задачи с таким id не найдено. Вероятно, она была удалена");
            return null;
        }
    }

    @Override
    public void putTask(Task task) {                    // Созданние.
        task.setId(id);
        taskMap.put(id, task);
        id++;
    }

    @Override
    public void putEpic(Epic epic) {
        epic.setId(id);
        epicMap.put(id, epic);
        id++;
    }

    @Override
    public void putSubtask(Subtask subtask) {
        subtask.setId(id);
        subtaskMap.put(id, subtask);
        (subtask.getParentEpic()).addNewSubtusk(subtask);
        subtask.getParentEpic().updateStatusEpic();
        id++;
    }

    @Override
    public void updateTask(Long replaceId, Task replaceTask) {       //Обновление задачи
        replaceTask.setId(replaceId);
        if (replaceId < 1 || replaceId > id) {
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

    @Override
    public void removeById(long getId) {                  //Удаление по индефикатору
        if (getId < 1 || getId > id) {
            System.out.println("Некорректный ввод id");
        } else if (epicMap.containsKey(getId)) {
            epicMap.remove(getId);
            historyManager.remove(getId);
        } else if (subtaskMap.containsKey(getId)) {
            Subtask deleteSubtask = subtaskMap.remove(getId);
            deleteSubtask.getParentEpic().deleteFromincludedSubtaks(deleteSubtask);
            deleteSubtask.getParentEpic().updateStatusEpic();
            historyManager.remove(getId);
        } else if (taskMap.containsKey(getId)) {
            taskMap.remove(getId);
            historyManager.remove(getId);
        } else {
            System.out.println("Задачи с таким id не существует. Вероятно, она уже была удалена");
        }
    }

    @Override
    public List<Subtask> getSubtaskOfEpic(Epic epic) {
        if (epic != null) {
            return epic.getincludedSubtaks();
        } else {
            System.out.println("Эпик пуст");
            return null;
        }
    }

    @Override
    public List<Task> history() {
        return historyManager.history();
    }
@Override
    public Long getId() {
        return id;
    }
}

