import java.util.List;

public interface HistoryManager {
    void linkLast(Task task);
    void removeNode(int id);
    List<Task> history();  //у Сергея history
}
