public class Main {
    public static void main(String[] args) {
        System.out.println("Пришло время практики!");

//чисто реализиуем консоль???
//а как id-то присваивать? Может изначально ставить -1?
//и статус для эпика вначале как? Просто NEW

Task washedDishes = new Task("Помыть посуду", "Помыть уже наконец посуду", id, "NEW");
Task takeUotTrash = new Task("Вынести мусор", "Вынести уже наконец мусор", id, "IN_PROGRESS");
Epic fixCar = new Epic("Починить машину", "Починить ходовку", id, "NEW");
Subtask buySpares = new Subtask("Купить запчасти", "Купить втулки стабилизатора и стойки", id, "IN_PROGRESS");
Subtask comeService = new Subtask("Заехать в сервис", "Позвонить и приехать в автосервис", id, "NEX");
Epic buyFood = new Epic("Купить продукты", "Купить продуктов на неделю", id, "NEW");
Subtask shoppingAshan = new Subtask("Закупки в Ашане", "Купить продукты на неделю в Ашане", id, "DONE");


//он хочет, что методы были static, но тогда там вылазиет ошибка
Manager.putTask(washedDishes);
Manager.putTask(takeUotTrash);
Manager.putEpic(fixCar);
Manager.putSubtask(buySpares);
Manager.putSubtask(comeService);
Manager.putEpic(buyFood);
Manager.putSubtask(shoppingAshan);


// а как распечатать список
        System.out.println(Manager.epicMap);


        /*
        Создайте 2 задачи, один эпик с 2 подзадачами, а другой эпик с 1 подзадачей.
Распечатайте списки эпиков, задач и подзадач, через System.out.println(..)
Измените статусы созданных объектов, распечатайте. Проверьте, что статус задачи и подзадачи сохранился, а статус эпика рассчитался по статусам подзадач.
И, наконец, попробуйте удалить одну из задач и один из эпиков.
Воспользуйтесь дебаггером, поставляемым вместе со средой разработки, что бы понять логику работы программы и отладить.

         */

    }

}
