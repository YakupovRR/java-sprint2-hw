package manager;

import history.HistoryManager;
import tasktypes.Epic;
import tasktypes.Subtask;
import tasktypes.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    protected Long id = 1L;
    protected HashMap<Long, Task> taskMap = new HashMap<>();
    protected HashMap<Long, Epic> epicMap = new HashMap<>();
    protected HashMap<Long, Subtask> subtaskMap = new HashMap<>();
    protected HistoryManager historyManager = Managers.getDefaultHistory();

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
        if (isNotСrossing(task)) {
            task.setId(id);
            taskMap.put(id, task);
            id++;
        }
    }

    @Override
    public void putEpic(Epic epic) {
        epic.setId(id);
        epicMap.put(id, epic);
        id++;
    }

    @Override
    public void putSubtask(Subtask subtask) {
        if (isNotСrossing(subtask)) {
            subtask.setId(id);
            subtaskMap.put(id, subtask);
            (subtask.getParentEpic()).addNewSubtusk(subtask);
            subtask.getParentEpic().updateStatusEpic();
            id++;
        }
    }

    @Override
    public void updateTask(Long replaceId, Task replaceTask) {       //Обновление задачи
        if (isNotСrossing(replaceTask)) {
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

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override

    public TreeSet<Task> getPrioritizedTasks() {

        TreeSet<Task> tasks = new TreeSet<Task>(new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
               if (o1.getStartTime().equals(null)) {
                   return 1;
               } else if (o2.getStartTime().equals(null)) {
                   return -1;
               }if (o1.getStartTime().isBefore(o2.getStartTime())) {
                    return -1;
                } else return 1;
            }
        });

        for (Long i : taskMap.keySet()) {
            Task task = taskMap.get(i);
            tasks.add(task);
        }
        for (Long i : subtaskMap.keySet()) {
            Task task = subtaskMap.get(i);
            tasks.add(task);
        }
        return tasks;
    }

    public boolean isNotСrossing(Task newTask) {
        TreeSet<Task> tasks = getPrioritizedTasks();
        tasks.add(newTask);
        List<Task> tasksList = new ArrayList<>();
        boolean notСrossing = true;
        boolean newTaskInList = false;
        int numberNewTaskInList = 0;
        //переносим сортированный TreeSet в List, "отлавливая" местоположение новой задачи
        for (Task i : tasks) {
            tasksList.add(i);
            if (newTaskInList) {       //если новая задача в листе, мы предварительно добавим следующею
                //и выйдем из цикла, дальше нам неинтересно
                break;
            }
            if (!i.equals(newTask)) {
                numberNewTaskInList++;
            } else {
                newTaskInList = true;
            }
        }
        if ((numberNewTaskInList > 0) && (newTask.getStartTime().isBefore(tasksList.get(
                numberNewTaskInList - 1).getEndTime()))) {
            notСrossing = false;
            System.out.println("Время начала новой задачи пересекается с временем окончания задачи № "
                    + tasksList.get(numberNewTaskInList - 1).getId());
        }
        if ((numberNewTaskInList < (tasksList.size() - 1)) && (newTask.getEndTime().isAfter(tasksList.get(
                numberNewTaskInList + 1).getStartTime()))) {
            notСrossing = false;
            System.out.println("Время окончания новой задачи пересекается с временем начала задачи № "
                    + tasksList.get(numberNewTaskInList + 1).getId());
        }
        return notСrossing;
    }
}







