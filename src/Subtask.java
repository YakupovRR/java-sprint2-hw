public class Subtask extends Task {
    Epic parentEpic;

    public Subtask(String title, String description, Long id, String status, Epic parentEpic) {
        super(title, description, id, status);
        this.parentEpic = parentEpic;
    }

    public Epic getParentEpic() {
        return parentEpic;
    }

    public void setParentEpic(Epic parentEpic) {
        this.parentEpic = parentEpic;
    }

    @Override
    public void setStatus(String status) {
        super.setStatus(status);
        //
    }
}
