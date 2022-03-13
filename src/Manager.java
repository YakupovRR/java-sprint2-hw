import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Manager {
    public Long id = 0;

    HashMap <Long, Epic > epicMap = new HashMap<>();
    HashMap <Long, Subtask > subtaskMap = new HashMap<>();
    HashMap <Long, Task > taskMap = new HashMap<>();


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

    public List<Task> clearTasks() {
        return new ArrayList<>(taskMap.clear());
    }
    public List<Epic> clearEpics() {
        return new ArrayList<>(epicMap.clear());
    }
    public List<Subtask> clearSubtasks() {
        return new ArrayList<>(subtaskMap.clear());
    }

//Получение по индефикатору
    public Task getById(long getId) {
    if (getId<0||getId>id) {
        System.out.println("Некорректный id");
        return null;
    } else if (epicMap.containsKey(getId)) {
        return epicMap.get(getId);
    } else if (subtaskMap.containsKey(getId)) {
        return subtaskMap.get(getId);
    } else if (taskMap.containsKey(getId)) {
        return taskMap.get(getId);
    }
}

    // Созданние.
    public void putTask (Task task) {
        id++;
        taskMap.put(id, task);
    }
    public void putEpic (Epic epic) {
        id++;
        taskMap.put(id, epic);
    }
    public void putSubtask (Subtask subtask) {
        id++;
        taskMap.put(id, subtask);


        //запустить метод проверки статуса эпика

    }

//Обновление
    public void getById(Long replaceId, Task replaceTask) {
        if (replaceId<0||replaceId>id) {
            System.out.println("Некорректный id");
        } else if (epicMap.containsKey(replaceId)) {
            epicMap.replace(replaceId, (Epic) replaceTask);
        } else if (subtaskMap.containsKey(replaceId)) {
            subtaskMap.get(replaceId, (Subtask) replaceTask);

            //запустить метод проверки статуса эпика

        } else if (taskMap.containsKey(replaceId)) {
            taskMap.get(replaceId, replaceTask);
        }
    }


    //Удаление по индефикатору
    public Task removeById(long getId) {
        if (getId<0||getId>id) {
            System.out.println("Некорректный id");
        } else if (epicMap.containsKey(getId)) {
            epicMap.remove(getId);
        } else if (subtaskMap.containsKey(getId)) {
            subtaskMap.remove(getId);
        } else if (taskMap.containsKey(getId)) {
            taskMap.remove(getId);
        } else {
            System.out.println("Задачи с таким id не существует");
        }

    }


    /*
    public void printAllTask (HashMap taskHashMap) {
//я принимаю в taskHashMap хэшмапу со списком задач того типа, которые хочет менеджер
        // я должен сохранить в i объект-задачу, а из этого объекта уже вытянуть название
        for (Task i: taskHashMap.values())
            System.out.println(i.getTitle());
    }

    // может подать и эпики и сабтаски сюда?

*/




    public void updateTask (Task task) {

        taskMap.put(task.getId(), task);
    }


/*
   + Возможность хранить задачи всех типов. Для этого вам нужно выбрать подходящую коллекцию.
        Методы для каждого из типа задач(Задача/Эпик/Подзадача):
    +Получение списка всех задач.
   + Удаление всех задач. тоже самое, только через клиа
    + Получение по идентификатору. contains, вначале переменную Таск, присвоив нулл. Или можно 3 метода.
    +Создание. Сам объект должен передаваться в качестве параметра.
   +/- Обновление. Новая версия объекта с верным идентификатором передаются в виде параметра.
   + Удаление по идентификатору.
    Дополнительные методы:
    Получение списка всех подзадач определённого эпика.
    Управление статусами осуществляется по следующему правилу:
    Менеджер сам не выбирает статус для задачи. Информация о нём приходит менеджеру вместе с информацией о
    самой задаче. По этим данным в одних случаях он будет сохранять статус, в других будет рассчитывать.

    Для эпиков:
    если у эпика нет подзадач или все они имеют статус NEW, то статус должен быть NEW.
    если все подзадачи имеют статус DONE, то и эпик считается завершённым — со статусом DONE.
    во всех остальных случаях статус должен быть IN_PROGRESS.

 */


}
