public class Main {
    static Manager manager = new Manager();

    public static void main(String[] args) {
        System.out.println("Пришло время практики!");

        Task washedDishes = new Task("Помыть посуду", "Помыть уже наконец посуду", 1L, "NEW");
        Task takeOutTrash = new Task("Вынести мусор", "Вынести уже наконец мусор", 2L, "IN_PROGRESS");
        Epic fixCar = new Epic("Починить машину", "Починить ходовку", 3L, "NEW");
        Subtask buySpares = new Subtask("Купить запчасти", "Купить втулки стабилизатора и стойки", 4L, "IN_PROGRESS", fixCar);
        Subtask comeService = new Subtask("Заехать в сервис", "Позвонить и приехать в автосервис", 5L, "NEW", fixCar);
        Epic buyFood = new Epic("Купить продукты", "Купить продуктов на неделю", 6L, "NEW");
        Subtask shoppingAshan = new Subtask("Закупки в Ашане", "Купить продукты на неделю в Ашане", 7L, "DONE", buyFood);

        manager.putTask(washedDishes);
        manager.putTask(takeOutTrash);
        manager.putEpic(fixCar);
        manager.putSubtask(buySpares);
        manager.putSubtask(comeService);
        manager.putEpic(buyFood);
        manager.putSubtask(shoppingAshan);
        printTitleTasks();
        System.out.println("Статус задачи Вынести мусор - " + takeOutTrash.getStatus());
        takeOutTrash.setStatus("DONE");

        System.out.println("Статус задачи Вынести мусор - " + takeOutTrash.getStatus());
        System.out.println("Статус эпика Починить машину - " + fixCar.getStatus());
        buySpares.setStatus("DONE");
        manager.updateTask(buySpares.getId(), buySpares);
        comeService.setStatus("DONE");
        manager.updateTask(buySpares.getId(), buySpares);
        System.out.println("Статус эпика Починить машину - " + fixCar.getStatus());

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

        /*
        Создайте 2 задачи, один эпик с 2 подзадачами, а другой эпик с 1 подзадачей.
Распечатайте списки эпиков, задач и подзадач, через System.out.println(..)
Измените статусы созданных объектов, распечатайте. Проверьте, что статус задачи и подзадачи сохранился, а статус эпика рассчитался по статусам подзадач.
И, наконец, попробуйте удалить одну из задач и один из эпиков.
Воспользуйтесь дебаггером, поставляемым вместе со средой разработки, что бы понять логику работы программы и отладить.

         */


}
