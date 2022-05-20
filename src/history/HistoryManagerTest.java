package history;

import manager.Managers;
import org.testng.annotations.Test;
import tasktypes.Epic;
import tasktypes.Status;
import tasktypes.Subtask;
import tasktypes.Task;

import java.time.LocalDateTime;
import java.util.List;

import static manager.Main.manager;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

public class HistoryManagerTest {

    //Пустая история задач
    @Test
    void historyListIsEpty() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        boolean historyIsEpty = false;
        if (historyManager.history().isEmpty()) {
            historyIsEpty = true;
        }
        assertTrue("Лист истории пуст", historyIsEpty);
        assertFalse("Лист истории не пуст", historyIsEpty);
    }

    //Дублирование (в ТЗ не указано, я так понял имелось в виду дублирование записи о задачи в истории)
    @Test
    void historyListDuble() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        Epic epic = new Epic("Test epic", "Test epic description");
        Subtask subtask1 = new Subtask("Test subtask1", "Test subtask1 description", Status.NEW, 30,
                LocalDateTime.now().plusMinutes(0), epic);
        Subtask subtask2 = new Subtask("Test subtask2", "Test subtask2 description", Status.NEW, 30,
                LocalDateTime.now().plusMinutes(31), epic);

        manager.putEpic(epic);
        manager.putSubtask(subtask1);
        manager.putSubtask(subtask2);
        Long idEpic = epic.getId();
        Long subtask1Id = subtask1.getId();
        Long subtask2Id = subtask2.getId();
        manager.getTaskById(idEpic);
        manager.getTaskById(subtask1Id);
        manager.getTaskById(subtask2Id);
        manager.getTaskById(idEpic);
        List<Task> history = historyManager.history();
        boolean isDuplicate = false;
        int copy = 0;
        Long verifiableId = epic.getId();
        for (Task i : history) {
            if (i.getId() == verifiableId) {
                copy++;
            }
        }
        if (copy>1) isDuplicate= true;


        assertTrue("Имеются повторные записи", isDuplicate);
        assertFalse("Повторные записи отсуствуют", isDuplicate);
    }

    //удаление задач из истории
    @Test
    void historyListRemove() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        Epic epic = new Epic("Test epic", "Test epic description");
        Subtask subtask1 = new Subtask("Test subtask1", "Test subtask1 description", Status.NEW, 30,
                LocalDateTime.now().plusMinutes(0), epic);
        Subtask subtask2 = new Subtask("Test subtask2", "Test subtask2 description", Status.NEW, 30,
                LocalDateTime.now().plusMinutes(31), epic);

        manager.putEpic(epic);
        manager.putSubtask(subtask1);
        manager.putSubtask(subtask2);
        Long idEpic = epic.getId();
        Long subtask1Id = subtask1.getId();
        Long subtask2Id = subtask2.getId();
        manager.getTaskById(idEpic);
        manager.getTaskById(subtask1Id);
        manager.getTaskById(subtask2Id);
        manager.removeById(idEpic);
        manager.removeById(subtask1Id);
        manager.removeById(subtask2Id);

        boolean isDelite = true;
        if (historyManager.history().contains(epic)) {
            isDelite = false;
        }
        if (historyManager.history().contains(subtask1)) {
            isDelite = false;
        }
        if (historyManager.history().contains(subtask2)) {
            isDelite = false;
        }

        assertTrue("Все удаленные задачи успешно удалены из истории", isDelite);
        assertFalse("Ошибка при удалении из истории", isDelite);
    }
}




