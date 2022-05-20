package tasktypes;

import java.time.LocalDateTime;

public class Subtask extends Task {
    private Epic parentEpic;
    private TasksTypes type = TasksTypes.SUBTASK;

    public Subtask(String title, String description, Status status, int duration,
                   LocalDateTime startTime, Epic parentEpic) {
        super(title, description, status, duration, startTime);
        this.parentEpic = parentEpic;
    }

    public Epic getParentEpic() {
        return parentEpic;
    }

    public void setParentEpic(Epic parentEpic) {
        this.parentEpic = parentEpic;
    }

}

