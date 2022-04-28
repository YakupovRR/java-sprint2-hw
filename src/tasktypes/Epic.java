package tasktypes;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Subtask> includedSubtaks;
    private TasksTypes type = TasksTypes.EPIC;

    public Epic(String title, String description, Status status) {
        super(title, description, status);
        this.includedSubtaks = new ArrayList<>();
    }

    public void updateStatusEpic() {               //Проверка статуса эпика
        if (includedSubtaks.isEmpty()) {
            this.setStatus(Status.NEW);
        } else {
            boolean isDone = true;
            boolean isNew = true;
            for (Subtask subtask : includedSubtaks) {
                if (subtask.getStatus() != Status.NEW) {
                    isNew = false;
                }
                if (subtask.getStatus() != Status.DONE) {
                    isDone = false;
                }
            }
            if (isDone) {
                this.setStatus(Status.DONE);
            } else if (isNew) {
                this.setStatus(Status.NEW);
            } else {
                this.setStatus(Status.IN_PROGRESS);
            }
        }
    }

    public void clearOfSubtusks() {
        includedSubtaks.clear();
    }

    public void addNewSubtusk(Subtask subtask) {
        includedSubtaks.add(subtask);
    }

    public void deleteFromincludedSubtaks(Subtask subtask) {
        includedSubtaks.remove(subtask);
    }

    public List<Subtask> getincludedSubtaks() {
        return includedSubtaks;
    }

    public void setincludedSubtaks(List<Subtask> includedSubtaks) {
        this.includedSubtaks = includedSubtaks;
    }

}
