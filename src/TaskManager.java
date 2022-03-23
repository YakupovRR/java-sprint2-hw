import java.util.HashMap;
import java.util.List;

public interface TaskManager {

   public HashMap<Long, Epic> getEpicMap();

    public HashMap<Long, Subtask> getSubtaskMap();

    public HashMap<Long, Task> getTaskMap();

    public List<Task> getTasks();       //Получения списка всех задач для каждого типа

    public List<Epic> getEpics();

    public List<Subtask> getSubtasks();

    public void clearAllTasks(); //Удаление всех задач

    public void clearTasks();

    public void clearEpics();

    public void clearSubtasks();

    public Task getTaskById(Long desiredId);  //получение задачи по id

    public void putTask(Task task);                     // Созданние.

    public void putEpic(Epic epic);

    public void putSubtask(Subtask subtask);

    public void updateTask(Long replaceId, Task replaceTask);   //Обновление задачи


    public void removeById(long getId); //Удаление по индефикатору

    public List<Subtask> getSubtaskOfEpic(Epic epic);

    public List<Task> getHistory();

    public void setHistory(Task task);
}
