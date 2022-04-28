package manager;

import java.io.IOException;

public class ManagerSaveException extends IOException {
    public ManagerSaveException() {
        super("Ошибка при чтении файла");
    }
}
