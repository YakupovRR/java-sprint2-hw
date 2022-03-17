public class Main {
    static Manager manager = new Manager();

    public static void main(String[] args) {
        System.out.println("Пришло время практики!");


        Task washedDishes = new Task("Помыть посуду", "Помыть уже наконец посуду", manager.nextId(), "NEW");
        Task takeUotTrash = new Task("Вынести мусор", "Вынести уже наконец мусор", manager.nextId(), "IN_PROGRESS");
        Epic fixCar = new Epic("Починить машину", "Починить ходовку", manager.nextId(), "NEW");
        Subtask buySpares = new Subtask("Купить запчасти", "Купить втулки стабилизатора и стойки", manager.nextId(), "IN_PROGRESS", fixCar);
        Subtask comeService = new Subtask("Заехать в сервис", "Позвонить и приехать в автосервис", manager.nextId(), "NEX", fixCar);
        Epic buyFood = new Epic("Купить продукты", "Купить продуктов на неделю", manager.nextId(), "NEW");
        Subtask shoppingAshan = new Subtask("Закупки в Ашане", "Купить продукты на неделю в Ашане", manager.nextId(), "DONE", buyFood);


        manager.putTask(washedDishes);
        System.out.println("Пункт добавлен. Текущий id - "+ manager.id);
        manager.putTask(takeUotTrash);
        System.out.println("Пункт добавлен. Текущий id - "+ manager.id);
        manager.putEpic(fixCar);
        System.out.println("Пункт добавлен. Текущий id - "+ manager.id);
        manager.putSubtask(buySpares);
        System.out.println("Пункт добавлен. Текущий id - "+ manager.id);
        manager.putSubtask(comeService);
        System.out.println("Пункт добавлен. Текущий id - "+ manager.id);
        manager.putEpic(buyFood);
        System.out.println("Пункт добавлен. Текущий id - "+ manager.id);
        manager.putSubtask(shoppingAshan);
        System.out.println("Пункт добавлен. Текущий id - "+ manager.id);

        printTitleTasks();

    }



    // для удобства проверки печатаю список названий задач
         public static void printTitleTasks() {

            System.out.println("Список Задач:");
            if (manager.taskMap.isEmpty()) {
                System.out.println("Список Задач пуст");
            } else {
                for (Task i : manager.getTaskMap().values()) {
                    System.out.println(i.title);
                }
            }

            System.out.println("Список Эпиков:");
            if (manager.epicMap.isEmpty()) {
                System.out.println("Список Эпиков пуст");
            } else {
                for (Task i : manager.getEpicMap().values()) {
                    System.out.println(i.title);
                }
            }

            System.out.println("Список Подзадач:");
            if (manager.subtaskMap.isEmpty()) {
                System.out.println("Список Подзадач пуст");
            } else {
                for (Task i : manager.getSubtaskMap().values()) {
                    System.out.println(i.title);
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
