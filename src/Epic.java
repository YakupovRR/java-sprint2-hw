import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Epic extends Task  {



    List<Subtask> includedSabtaks = new ArrayList<>();


    public Epic(String title, String description, Long id, String status, List<Subtask> includedSabtaks) {
        super(title, description, id, status);
        this.includedSabtaks = includedSabtaks;
    }

    public void updateStatusEpic (Epic epic) {
        if (includedSabtaks.isEmpty()) {
         epic.status = "NEW";
        } else {
            for (Subtask subtask : includedSabtaks) {

                //условия если все дан, не все дан, все нью. Сохраняю статус
            }
        }
    }



    public List<Subtask> getIncludedSabtaks() {
        return includedSabtaks;
    }

    public void setIncludedSabtaks(List<Subtask> includedSabtaks) {
        this.includedSabtaks = includedSabtaks;
    }
}
