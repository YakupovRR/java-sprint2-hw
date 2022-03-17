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
        return new HashMap<>(taskMap.clear());
    }
    public List<Epic> clearEpics() {
        return new HashMap<>(epicMap.clear());
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
    } else  {
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
        epicMap.put(id, epic);
    }
    public void putSubtask (Subtask subtask) {
        id++;
        subtaskMap.put(id, subtask);
        //запустить метод проверки статуса эпика
        subtask.getParentEpic().epic.updateStatusEpic;
    }

//Обновление
    public void getById(Long replaceId, Task replaceTask) {
        if (replaceId<0||replaceId>id) {
            System.out.println("Некорректный id");
        } else if (epicMap.containsKey(replaceId)) {             // в Эпик
            epicMap.replace(replaceId, (Epic) replaceTask);

        } else if (subtaskMap.containsKey(replaceId)) {           // В сабтаску

            // (Subtask) replaceTask.parentEpic;  хз что для чего я это писал

            subtaskMap.get(replaceId, (Subtask) replaceTask);
            //нужно взять пэрэнт эпик этого сабтаска и запустить ему updateStatusEpic
            //но как он ему сохранит? это же по факту другой объект? Искать в хэшмапе с эпиками такой же и выставлять?
            Epic intermediateEpic = new Epic();          // создал эпик-прокладку
            intermediateEpic = ((Subtask) replaceTask).parentEpic;   // сохранил в него пэрентЭпик этого сабтаска
            intermediateEpic.includedSabtaks.add((Subtask) replaceTask); // сохранил в список этого эпика новую сабтаску
            // теперь как-то надо сопоставить по id интерЭпика с хэшмакой
            Long intermedId =  intermediateEpic.id; // создал ИД-прокладку
            epicMap.put(intermedId, intermediateEpic); // заменил в Мапе менеджера по этому ИД новый эпик

            //запустить метод проверки статуса эпика
            Epic.updateStatusEpic(epicMap.get(intermedId));


        } else if (taskMap.containsKey(replaceId)) {
           taskMap.get(replaceId, replaceTask);
        } else {
            System.out.println("Задачи с таким id не найдено. Вероятно, она была удалена");
        }
    }


    //Удаление по индефикатору
    public void removeById(long getId) {
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
   + Получение списка всех подзадач определённого эпика.
    Управление статусами осуществляется по следующему правилу:
    Менеджер сам не выбирает статус для задачи. Информация о нём приходит менеджеру вместе с информацией о
    самой задаче. По этим данным в одних случаях он будет сохранять статус, в других будет рассчитывать.

    Для эпиков:
    если у эпика нет подзадач или все они имеют статус NEW, то статус должен быть NEW.
    если все подзадачи имеют статус DONE, то и эпик считается завершённым — со статусом DONE.
    во всех остальных случаях статус должен быть IN_PROGRESS.

 */


}
