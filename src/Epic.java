import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    List<Subtask> includedSabtaks;

    public Epic(String title, String description, Long id, String status) {
        super(title, description, id, status);
        List<Subtask> includedSabtaks = new ArrayList<>();
    }

    public void updateStatusEpic() {               //Проверка статуса эпика
        if (includedSabtaks.isEmpty()) {
            this.setStatus("NEW");
        } else {
            boolean isDone = true;
            boolean isNew = true;
            for (Subtask subtask : includedSabtaks) {
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
        includedSabtaks.clear();
    }

    public void addNewSubtusk(Subtask subtask) {
        includedSabtaks.add(subtask);
    }

    public void deleteFromIncludedSabtaks (Subtask subtask) {
        includedSabtaks.remove(subtask);
    }

    public List<Subtask> getIncludedSabtaks() {
        return includedSabtaks;
    }

    public void setIncludedSabtaks(List<Subtask> includedSabtaks) {
        this.includedSabtaks = includedSabtaks;
    }

}
