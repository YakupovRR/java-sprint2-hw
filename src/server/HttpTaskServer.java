package server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;


public class HttpTaskServer {
//Если что у вас здесь должен быть класс Main с методом main.

    /*
     Далее создайте класс HttpTaskServer,
    который будет слушать порт 8080 и принимать запросы. Добавьте в него реализацию FileBackedTaskManager,
    которую можно получить из утилитного класса Managers. После этого можно реализовать маппинг запросов на
    методы интерфейса TaskManager.

     */







    public static void main(String[] args) throws IOException {
//        URI uri = URI.create(""); //какой-то адрес в скобка


        HttpServer.create(new InetSocketAddress(8080), 0);





        //здесь теперь маппинг запросов?






            /*
    возможно!
    К httpTaskServer делаются запросы, он в свою очередь согласно мапингу вызывает нужный метод у HttpTaskManager,
* в соответствии с переданным запросом. Далее HttpTaskManager выполняет нужный метод, и при помощи KVTaskClient
* выполняет сохранение или загрузку своего состояния на сервере KVServer
     */

/*
Преобразуем строку в Таску
        String jsonString = null;
        // или может сразу Gson-ом передать с сервера?
        Gson gson = new Gson();
        Task task = gson.fromJson(jsonString, Task.class);

        */


        /*
        таску в json
        ry {
          HttpClient client = HttpClient.newHttpClient();
          URI url = URI.create("http://localhost:8080/tasks/task");
          Gson gson = new Gson();
          String json = gson.toJson(task1);
          HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
          HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
          HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      }catch (JsonIOException ignored)

         */


/*
        List<Task> taskHistoryList = taskManager.history();
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .registerTypeAdapter(Task.class, new TaskSerializer())
                .registerTypeAdapter(Epic.class, new EpicSerializer())
                .registerTypeAdapter(Subtask.class, new SubTaskSerializer())
                .create();
        responseJson = gson.toJson(taskHistoryList);
*/

    }


}
