package manager;

import tasktypes.Epic;
import tasktypes.Status;
import tasktypes.Subtask;
import tasktypes.Task;

//Код просмотерел, вроде всё выровнял. Если где-то осталось, просьба не бить ногами)

public class Main {
    static TaskManager manager = (InMemoryTaskManager) Managers.getDefault();  //честно, так и не понял,
    //как иначе это реализовать. Такой вариант подсказали в слаке; но что-о я сомневаюсь))
    // static InMemoryTaskManager manager = new InMemoryTaskManager(); - а чем плоха такая реализация?

    public static void main(String[] args) {
        System.out.println("Пришло время практики!");

        Task washedDishes = new Task("Помыть посуду", "Помыть уже наконец посуду", Status.NEW);
        Task takeOutTrash = new Task("Вынести мусор", "Вынести уже наконец мусор", Status.IN_PROGRESS);
        Epic fixCar = new Epic("Починить машину", "Починить ходовку", Status.NEW);
        Subtask buySpares = new Subtask("Купить запчасти", "Купить втулки стабилизатора и стойки", Status.IN_PROGRESS, fixCar);
        Subtask comeService = new Subtask("Заехать в сервис", "Позвонить и приехать в автосервис", Status.NEW, fixCar);
        Epic buyFood = new Epic("Купить продукты", "Купить продуктов на неделю", Status.NEW);

        manager.putTask(washedDishes);
        manager.putTask(takeOutTrash);
        manager.putEpic(fixCar);
        manager.putSubtask(buySpares);
        manager.putSubtask(comeService);
        manager.putEpic(buyFood);
        System.out.println(washedDishes.getId());
        System.out.println(takeOutTrash.getId());
        System.out.println(manager.getId());


        manager.getTaskById(1L);
        manager.getTaskById(2L);
        System.out.println(manager.history());
    }
}