import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Epic extends Task {


    List<Subtask> includedSabtaks = new ArrayList<>();


    public Epic(String title, String description, Long id, String status) {
        super(title, description, id, status);
    }

    //Проверка статуса эпика
    public void updateStatusEpic(Epic epic) {
        if (includedSabtaks.isEmpty()) {
            epic.status = "NEW";
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
                epic.status = "DONE";
            } else if (isNew) {
                epic.status = "NEW";
            } else {
                epic.status = "IN_PROGRESS";
            }
        }
    }


    public List<Subtask> getIncludedSabtaks() {
        return includedSabtaks;
    }

    public void setIncludedSabtaks(List<Subtask> includedSabtaks) {
        this.includedSabtaks = includedSabtaks;
    }

    //что возвращаем? Лист? Что принимаем? Эпик?
public List<Subtask> getSubtaskOfEpic (Epic epic) {
        assert epic != null;
        return epic.includedSabtaks;
    }




}
