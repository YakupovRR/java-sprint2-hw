package tasktypes;

import java.time.LocalDateTime;

public class Subtask extends Task {
    private Epic parentEpic;

    public Subtask(String title, String description, Status status, int duration,
                   LocalDateTime startTime, Epic parentEpic) {
        super(title, description, status, duration, startTime);
        this.parentEpic = parentEpic;
        this.type = TasksTypes.SUBTASK;
    }

    public Epic getParentEpic() {
        return parentEpic;
    }

    public void setParentEpic(Epic parentEpic) {
        this.parentEpic = parentEpic;
    }

}

