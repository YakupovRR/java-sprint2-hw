import java.util.List;

public interface HistoryManager {
    void linkLast(Task task);
    void removeNode(Long id);
    List<Task> history();
}
