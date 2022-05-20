package manager;

import tasktypes.Epic;
import tasktypes.Subtask;
import tasktypes.Task;

import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

public interface TaskManager {

    HashMap<Long, Epic> getEpicMap();   //нет

    HashMap<Long, Subtask> getSubtaskMap();  //нет

    HashMap<Long, Task> getTaskMap(); //нет

    List<Task> getTasks();   //норма, пустой, ошибки быть не может    //Получения списка всех задач для каждого типа

    List<Epic> getEpics(); //норма, пустой, ошибки быть не может

    List<Subtask> getSubtasks(); //норма, пустой, ошибки быть не может

    void clearAllTasks(); //норма, пустой, ошибки быть не может    //Удаление всех задач

    void clearTasks(); //норма, пустой, ошибки быть не может

    void clearEpics(); //норма, пустой, ошибки быть не может

    void clearSubtasks(); //норма, пустой, ошибки быть не может

    Task getTaskById(Long desiredId); // норма, пустой = ошибка, ошибочный

    void putTask(Task task);   //норма, пустой список = норма,  ошибки быть не может     // Создание.

    void putEpic(Epic epic); //норма, пустой список = норма,  ошибки быть не может

    void putSubtask(Subtask subtask); //норма, пустой список = норма, ошибки быть не может

    void updateTask(Long replaceId, Task replaceTask);  //норма, пустой = норма, ошибка  //Обновление задачи

    void removeById(long getId); //норма, пустой = ошибка, ошибка  //Удаление по индефикатору

    List<Subtask> getSubtaskOfEpic(Epic epic); //норма, пустой = ошибка, ошибка

    public List<Task> history(); //норма, пустой, ошибочного id нет

    public Long getId(); //норма, пустой, ошибочного id нет

    public void setId(Long id);

    public TreeSet<Task> calcPrioritizedTasks();

    public TreeSet<Task> getPrioritizedTasks();

    public boolean isNotСrossing(Task newTask);
}
