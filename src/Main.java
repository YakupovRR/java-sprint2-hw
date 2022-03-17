public class Main {
    public static void main(String[] args) {
        System.out.println("Пришло время практики!");
        Manager manager = new Manager();

//а как id-то присваивать? Может изначально ставить -1?


        Task washedDishes = new Task("Помыть посуду", "Помыть уже наконец посуду", manager.nextId(), "NEW");
        Task takeUotTrash = new Task("Вынести мусор", "Вынести уже наконец мусор", manager.nextId(), "IN_PROGRESS");
        Epic fixCar = new Epic("Починить машину", "Починить ходовку", manager.nextId(), "NEW");
        Subtask buySpares = new Subtask("Купить запчасти", "Купить втулки стабилизатора и стойки", manager.nextId(), "IN_PROGRESS", fixCar);
        Subtask comeService = new Subtask("Заехать в сервис", "Позвонить и приехать в автосервис", manager.nextId(), "NEX", fixCar);
        Epic buyFood = new Epic("Купить продукты", "Купить продуктов на неделю", manager.nextId(), "NEW");
        Subtask shoppingAshan = new Subtask("Закупки в Ашане", "Купить продукты на неделю в Ашане", manager.nextId(), "DONE", buyFood);


//он хочет, что методы были static, но тогда там вылазиет ошибка
        manager.putTask(washedDishes);
        manager.putTask(takeUotTrash);
        manager.putEpic(fixCar);
        manager.putSubtask(buySpares);
        manager.putSubtask(comeService);
        manager.putEpic(buyFood);
        manager.putSubtask(shoppingAshan);


// а как распечатать список
        for (Task i : manager.getEpicMap().values()) {
            System.out.println(i.title);
        }




        /*
        Создайте 2 задачи, один эпик с 2 подзадачами, а другой эпик с 1 подзадачей.
Распечатайте списки эпиков, задач и подзадач, через System.out.println(..)
Измените статусы созданных объектов, распечатайте. Проверьте, что статус задачи и подзадачи сохранился, а статус эпика рассчитался по статусам подзадач.
И, наконец, попробуйте удалить одну из задач и один из эпиков.
Воспользуйтесь дебаггером, поставляемым вместе со средой разработки, что бы понять логику работы программы и отладить.

         */

    }

}
