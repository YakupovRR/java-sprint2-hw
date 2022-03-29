package manager;

import tasktypes.Epic;
import tasktypes.Status;
import tasktypes.Subtask;
import tasktypes.Task;

public class Main {
      static InMemoryTaskManager manager = new InMemoryTaskManager();

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