package manager;

import history.HistoryManager;
import tasktypes.Epic;
import tasktypes.Status;
import tasktypes.Subtask;
import tasktypes.Task;

import java.io.*;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

import static tasktypes.TasksTypes.SUBTASK;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

      public void save() {  //сохранение в файл
        TreeMap<Long, String> allTasks = new TreeMap<>(Comparator.comparingLong(o -> o));

        for (Task task : taskMap.values()) {
            allTasks.put(task.getId(), toCSV(task));
        }
        for (Epic epic : epicMap.values()) {
            allTasks.put(epic.getId(), toCSV(epic));
        }
        for (Subtask subtask : subtaskMap.values()) {
            allTasks.put(subtask.getId(), toCSV(subtask));
        }
        try (OutputStream outputStream = new FileOutputStream(file)) {
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"),
                    true);
            printWriter.println("id,type,name,status,description,epic");
            for (String task : allTasks.values()) {
                printWriter.println(task);
            }
            printWriter.println("\n" + toCSV(historyManager));
        } catch (IOException e) {
            System.out.println("Файл не найден.");
        }
    }

    public String toCSV(Task task) {    //создание строки из задачи
        String str = getId() + "," + task.getType() + "," + task.getTitle() + "," + task.getStatus() + ","
                + task.getDescription() + "," + task.getDuration() + "," + task.getStartTime() + ",";
        if (task.getType() == SUBTASK) {
            str = str + ((Subtask) task).getParentEpic().getId();
        }
        return str;
    }

    public Task fromString(String value) {             //создание задачи из строки
        String[] readenTask = value.split(",");
        String title = readenTask[2];
        String discription = readenTask[4];
        Status status = Status.valueOf(readenTask[3]);
        int duration = Integer.parseInt(readenTask[5]);
        LocalDateTime startTime = LocalDateTime.parse(readenTask[6]);
        switch (readenTask[1]) {
            case ("TASK"):
                Task task = new Task(title, discription, status, duration, startTime);
                taskMap.put(Long.valueOf(readenTask[0]), task);
                return task;
            case ("EPIC"):
                Task epic = new Epic(title, discription);
                epicMap.put(Long.valueOf(readenTask[0]), (Epic) epic);
                return epic;
            case ("SUBTASK"):
                Epic parentEpic = (Epic) getTaskById(Long.valueOf(readenTask[5]));
                Subtask subtask = new Subtask(title, discription, status, duration, startTime, parentEpic);
                parentEpic.updateStatusEpic();
                subtaskMap.put(Long.valueOf(readenTask[0]), subtask);
                return subtask;
            default:
                System.out.println("Ошибка при чтении файла");
                return null;
        }
    }

    public String toCSV(HistoryManager manager) { //сохранения менеджер истории в CSV
        String str;
        List<Long> taskId = new ArrayList<>();
        for (Task i : manager.history()) {
            taskId.add(i.getId());
        }
        str = String.join(",", String.valueOf(taskId));
        return str;
    }

    public void fromStringToHistrory(String value) { //восстановление истории из CSV
        List<String> tasksId = List.of(value.split(","));
        List<Integer> history = new ArrayList<>();

        for (String i : tasksId) {
            Long id = Long.valueOf(i);
            historyManager.add(getTaskById(id));
        }
    }

    public static FileBackedTasksManager loadFromFile(File file) throws ManagerSaveException {
        //восстанавливаем данные из файла
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);

        try (FileReader fileReader = new FileReader(file, Charset.forName("UTF-8"))) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            List<String> tasks = new ArrayList<>();
            String line = bufferedReader.readLine();
            while (line != null) {    //читаем до пустой строки-разделителя
                line = bufferedReader.readLine();
                if (!(line.trim().length() == 0)) {
                    tasks.add(line);
                }
            }
            for (String str : tasks) {
                Task task = fileBackedTasksManager.fromString(str);

                switch (task.getType()) {
                    case TASK:
                        fileBackedTasksManager.taskMap.put(task.getId(), task);
                        break;
                    case EPIC:
                        fileBackedTasksManager.epicMap.put(task.getId(), (Epic) task);
                        break;
                    case SUBTASK:
                        fileBackedTasksManager.subtaskMap.put(task.getId(), (Subtask) task);

                        break;
                    default:
                        throw new IllegalStateException("Ошибка при чтении типа задачи: " + task.getType());
                }

                line = bufferedReader.readLine(); //считываем строку с историей
                fileBackedTasksManager.fromStringToHistrory(line);

                fileBackedTasksManager.setId(Long.valueOf(tasks.size() - 2));
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при чтении файла");
        }
        return fileBackedTasksManager;

    }

    @Override
    public void clearAllTasks() {
        super.clearAllTasks();
        save();
    }

    @Override
    public void putTask(Task task) {
        super.putTask(task);
        save();
    }

    @Override
    public void putEpic(Epic epic) {
        super.putEpic(epic);
        save();
    }

    @Override
    public void putSubtask(Subtask subtask) {
        super.putSubtask(subtask);
        save();
    }

    @Override
    public void updateTask(Long replaceId, Task replaceTask) {
        super.updateTask(replaceId, replaceTask);
        save();
    }

    @Override
    public void removeById(long getId) {
        removeById(getId);
        save();
    }

    public static class Main {
        static TaskManager manager = (InMemoryTaskManager) Managers.getDefault();

    }
}