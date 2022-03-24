import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    private static List<Task> historyList  = new ArrayList<>();
    private int maxHistoryListLength = 10;

    public static List<Task> getHistoryList() {
        return historyList;
    }

    public static void setHistoryList(List<Task> historyList) {
        InMemoryHistoryManager.historyList = historyList;
    }


    @Override
    public List<Task> getHistory() {     //вызов истории
        return historyList;
    }

    @Override
    public void setHistory(Task task) {
        while (historyList.size() >= maxHistoryListLength) {
            historyList.remove(0);
        }
        historyList.add(task);
    }



}
