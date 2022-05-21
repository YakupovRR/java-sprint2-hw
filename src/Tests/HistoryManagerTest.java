package tests;

import history.HistoryManager;
import manager.InMemoryTaskManager;
import manager.Managers;
import manager.TaskManager;
import org.testng.annotations.Test;
import tasktypes.Epic;

import static org.testng.AssertJUnit.assertTrue;

public class HistoryManagerTest {

    //Пустая история задач
    @Test
    void historyListIsEpty() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertTrue("Лист истории пуст", historyManager.history().isEmpty());
    }

    //Дублирование
    @Test
    void historyListDuble() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        TaskManager manager = (InMemoryTaskManager) Managers.getDefault();
        Epic epic = new Epic("Test epic", "Test epic description");
        manager.putEpic(epic);
        manager.getTaskById(epic.getId());
        manager.getTaskById(epic.getId());
        assertTrue("Повторные записи отсуствуют", historyManager.history().size() == 1);
    }

    //удаление задач из истории
    @Test
    void historyListRemove() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        TaskManager manager = (InMemoryTaskManager) Managers.getDefault();
        Epic epic = new Epic("Test epic", "Test epic description");
        manager.removeById(epic.getId());
        assertTrue("Удаленные задачи успешно удалены из истории", historyManager.history().size() == 0);
    }
}




