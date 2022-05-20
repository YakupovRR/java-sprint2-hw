package tasktypes;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {

    private List<Subtask> includedSubtaks;
    private LocalDateTime endTime;

//дописать статус
    public Epic(String title, String description) {
        super(title, description);
        this.status = getStatus();
        this.includedSubtaks = new ArrayList<>();
        this.startTime = calcStartTime();
        this.duration = calcDuration();
        this.endTime = calcEndTime();
        this.type = TasksTypes.EPIC;
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

    public LocalDateTime calcStartTime() {
        LocalDateTime startTime = null;
        if (includedSubtaks.isEmpty()) {
            return startTime;
        } else {
            startTime = includedSubtaks.get(0).getStartTime();
            for (Subtask i : includedSubtaks) {
                LocalDateTime thisStartTime = includedSubtaks.get(0).getStartTime();
                if (thisStartTime.isBefore(startTime)) {
                    startTime = thisStartTime;
                }
            }
            return startTime;
        }
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime calcEndTime() {
        LocalDateTime endTime = null;
        if (includedSubtaks.isEmpty()) {
            return endTime;
        } else {
            endTime = includedSubtaks.get(0).getEndTime();
            for (Subtask i : includedSubtaks) {
                LocalDateTime thisEndTime = includedSubtaks.get(0).getEndTime();
                if (thisEndTime.isBefore(endTime)) {
                    endTime = thisEndTime;
                }
            }
            return endTime;
        }
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    private int calcDuration() {
        int duration = (int) Duration.between(startTime, endTime).toMinutes();
        return duration;
    }

    public int getDuration() {
        return duration;
    }

    public List<Subtask> getIncludedSubtaks() {
        return includedSubtaks;
    }
}
