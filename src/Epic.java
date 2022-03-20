import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Subtask> includedSubtaks;

    public Epic(String title, String description, Long id, String status) {
        super(title, description, id, status);
        this.includedSubtaks = new ArrayList<>();
    }

    public void updateStatusEpic() {               //Проверка статуса эпика
        if (includedSubtaks.isEmpty()) {
            this.setStatus("NEW");
        } else {
            boolean isDone = true;
            boolean isNew = true;
            for (Subtask subtask : includedSubtaks) {
                if (subtask.getStatus() != "NEW") {
                    isNew = false;
                }
                if (subtask.getStatus() != "DONE") {
                    isDone = false;
                }
            }
            if (isDone) {
                this.setStatus("DONE");
            } else if (isNew) {
                this.setStatus("NEW");
            } else {
                this.setStatus("IN_PROGRESS");
            }
        }
    }

    public void clearOfSubtusks () {
        includedSubtaks.clear();
    }

    public void addNewSubtusk(Subtask subtask) {
        includedSubtaks.add(subtask);
    }

    public void deleteFromincludedSubtaks (Subtask subtask) {
        includedSubtaks.remove(subtask);
    }

    public List<Subtask> getincludedSubtaks() {
        return includedSubtaks;
    }

    public void setincludedSubtaks(List<Subtask> includedSubtaks) {
        this.includedSubtaks = includedSubtaks;
    }

}
