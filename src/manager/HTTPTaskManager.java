package manager;

/*
Теперь можно создать новую реализацию интерфейса TaskManager — класс HTTPTaskManager.
Он будет наследовать от FileBackedTasksManager.
Конструктор HTTPTaskManager должен будет вместо имени файла принимать URL к серверу KVServer. Также HTTPTaskManager создаёт KVTaskClient,
из которого можно получить исходное состояние менеджера. Вам нужно заменить вызовы сохранения состояния в файлах на вызов клиента.
В конце обновите статический метод getDefault() в утилитарном классе Managers, чтобы он возвращал HTTPTaskManager.



Метод  save  будет переопределен в новом классе HttpTaskManager, который использует http  -клиент, чтобы класть и забирать из kvServer

 */


// примерно как KVServer - server.createContext

import java.io.File;

public class HTTPTaskManager extends FileBackedTasksManager {
    public HTTPTaskManager(File file) {
        super(file);  // поменять на URL
    }


}
