import java.util.List;

public interface HistoryManager {
    void setHistory(Task task);

    List<Task> getHistory();


}
