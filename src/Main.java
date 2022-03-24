public class Main {
    static InMemoryTaskManager manager = new InMemoryTaskManager();
    static HistoryManager historyManager = Managers.getDefaultHistory();

    public static void main(String[] args) {
        System.out.println("Пришло время практики!");

        Task washedDishes = new Task("Помыть посуду", "Помыть уже наконец посуду", Status.NEW);
        Task takeOutTrash = new Task("Вынести мусор", "Вынести уже наконец мусор", Status.IN_PROGRESS);
        Epic fixCar = new Epic("Починить машину", "Починить ходовку", Status.NEW);
        Subtask buySpares = new Subtask("Купить запчасти", "Купить втулки стабилизатора и стойки", Status.IN_PROGRESS, fixCar);
        Subtask comeService = new Subtask("Заехать в сервис", "Позвонить и приехать в автосервис", Status.NEW, fixCar);
        Epic buyFood = new Epic("Купить продукты", "Купить продуктов на неделю", Status.NEW);
        Subtask shoppingAshan = new Subtask("Закупки в Ашане", "Купить продукты на неделю в Ашане", Status.DONE, buyFood);

        manager.putTask(washedDishes);
        manager.putTask(takeOutTrash);
        manager.putEpic(fixCar);
        manager.putSubtask(buySpares);
        manager.putSubtask(comeService);
        manager.putEpic(buyFood);
        manager.putSubtask(shoppingAshan);
        printTitleTasks();
        System.out.println("Статус задачи Вынести мусор - " + takeOutTrash.getStatus());
        takeOutTrash.setStatus(Status.DONE);
        System.out.println("Статус задачи Вынести мусор - " + takeOutTrash.getStatus());
        System.out.println("Статус эпика Починить машину - " + fixCar.getStatus());
        buySpares.setStatus(Status.DONE);
        manager.updateTask(buySpares.getId(), buySpares);
        comeService.setStatus(Status.DONE);
        manager.updateTask(buySpares.getId(), buySpares);
        System.out.println("Статус эпика Починить машину - " + fixCar.getStatus());
        System.out.println(historyManager.getHistory());
        System.out.println(washedDishes.getId());
        System.out.println(takeOutTrash.getId());
        manager.getTaskById(1L);
        historyManager.getHistory();
        manager.getTaskById(2L);
        historyManager.getHistory();
if(historyManager.getHistory().isEmpty()) {
    System.out.println("Список пуст");
} else {
    System.out.println("Список не пуст");
}
    }

    // для удобства проверки печатаю список названий задач
    public static void printTitleTasks() {

        System.out.println("Список Задач:");
        if (manager.taskMap.isEmpty()) {
            System.out.println("Список Задач пуст");
        } else {
            for (Task i : manager.getTaskMap().values()) {
                System.out.println(i.getTitle());
            }
        }

        System.out.println("Список Эпиков:");
        if (manager.epicMap.isEmpty()) {
            System.out.println("Список Эпиков пуст");
        } else {
            for (Task i : manager.getEpicMap().values()) {
                System.out.println(i.getTitle());
            }
        }

        System.out.println("Список Подзадач:");
        if (manager.subtaskMap.isEmpty()) {
            System.out.println("Список Подзадач пуст");
        } else {
            for (Task i : manager.getSubtaskMap().values()) {
                System.out.println(i.getTitle());
            }
        }

    }


}
