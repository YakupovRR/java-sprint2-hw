import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Epic extends Task {


    List<Subtask> includedSabtaks = new ArrayList<>();


    public Epic(String title, String description, Long id, String status) {
        super(title, description, id, status);
        this.includedSabtaks = includedSabtaks;
    }

    //Проверка статуса эпика
    public void updateStatusEpic() {
        if (includedSabtaks.isEmpty()) {
          this.status = "NEW";
        } else {
            boolean isDone = true;
            boolean isNew = true;
            for (Subtask subtask : includedSabtaks) {
                if (subtask.status != "NEW"){
                    isNew = false;
                }
                if (subtask.status != "DONE"){
                    isDone = false;
                }
            }
            if (isDone) {
                this.status = "DONE";
            } else if (isNew) {
                this.status = "NEW";
            } else {
                this.status = "IN_PROGRESS";
            }
        }
    }


    public List<Subtask> getIncludedSabtaks() {
        return includedSabtaks;
    }

    public void setIncludedSabtaks(List<Subtask> includedSabtaks) {
        this.includedSabtaks = includedSabtaks;
    }

public List<Subtask> getSubtaskOfEpic (Epic epic) {
        if (epic != null){
        return epic.includedSabtaks;
    } else {
            System.out.println("Эпик пуст");
            return null;
        }

    }
}
