import java.util.HashMap;
import java.util.List;

public interface TaskManager {

    HashMap<Long, Epic> getEpicMap();

    HashMap<Long, Subtask> getSubtaskMap();

    HashMap<Long, Task> getTaskMap();

    List<Task> getTasks();       //Получения списка всех задач для каждого типа

    List<Epic> getEpics();

    List<Subtask> getSubtasks();

    void clearAllTasks(); //Удаление всех задач

    void clearTasks();

    void clearEpics();

    void clearSubtasks();

    Task getTaskById(Long desiredId);  //получение задачи по id

    void putTask(Task task);                     // Созданние.

    void putEpic(Epic epic);

    void putSubtask(Subtask subtask);

    void updateTask(Long replaceId, Task replaceTask);   //Обновление задачи


    void removeById(long getId); //Удаление по индефикатору

    List<Subtask> getSubtaskOfEpic(Epic epic);



}
