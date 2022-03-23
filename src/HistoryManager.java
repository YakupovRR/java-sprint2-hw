import java.util.List;

public interface HistoryManager {
    public void setHistory(Task task);
    public List<Task> getHistory();
}
