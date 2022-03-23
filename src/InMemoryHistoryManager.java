import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    private static List<Task> historyList  = new ArrayList<>();


    @Override
    public List<Task> getHistory() {     //вызов истории
        return historyList;
    }

    @Override
    public void setHistory(Task task) {
        int maxHistoryListLength = 10;
        while (historyList.size() >= maxHistoryListLength) {
            historyList.remove(0);
        }
        historyList.add(task);
    }

}
