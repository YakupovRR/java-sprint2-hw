package tasktypes;

public class Subtask extends Task {
    private Epic parentEpic;
    private TasksTypes type = TasksTypes.SUBTASK;

    public Subtask(String title, String description, Status status, Epic parentEpic) {
        super(title, description, status);
        this.parentEpic = parentEpic;
    }

    public Epic getParentEpic() {
        return parentEpic;
    }

    public void setParentEpic(Epic parentEpic) {
        this.parentEpic = parentEpic;
    }

    }

