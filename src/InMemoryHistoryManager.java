import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
   private static Node tail = null ;             //может final?
    private int maxHistoryListLength = 10;
    private Map<Long, Node> references;
    //Node history;


    public static void setHistoryList(List<Task> historyList) {
        InMemoryHistoryManager.historyList = historyList;
    }


    @Override
    public List<Task> history() {     //вызов истории
        ArrayList<Task> tasks = new ArrayList<>();
        if (head !=null) {
            Node first = head;
            while (first != null) {
                tasks.add(first.reference);     //через гетер?
                first = first.getNext();
            }
        }
        return tasks;
    }


    @Override
    public void add (Task task) {
        linkLast(task);
    }


    public void linkLast(Task task) {                //добавление таски setHistory
        if (references.containsKey(task.getId())) {
         removeNode(task.getId());           //у меня возвращается Long?
        }
        final Node oldTail = tail;      //тут баг?
final Node last = new Node (
        oldTail,
        null,
        task
        );
if (head == null) {
    head = last;
}
if (oldTail != null) {
    oldTail.setNext(last);
}
        tail = last;
        references.put(task.getId(), last);

    }

    @Override
    public void removeNode(Long id) {
final Node node = references.get(id);
       if (node != null) {
        final Node prev = node.getPrev();
        final Node next = node.getNext();
        if (prev!=null) {
            prev.setNext(next);
        } else  {
            head = next;
        }
        if (next!=null) {
            next.setPrev(prev);
        } else  {
            tail = prev;
        }
    references.remove(id);
       }
    }

    class Node {
        private Node prev;
        private Node next;
        private Task reference;

        public Node(
                final Node prev,
                final Node next,
                final Task reference

        ) {
            this.prev = prev;
            this.next = next;
            this.reference = reference;

        }

        public Node getPrev() {
            return prev;   //не this.prev?
        }

        public Node getNext() {
            return next;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }

        public void setNext(Node next) {
            this.next = next;
        }




   /* public void linkLast(Task task) {
        Node newHead = new Node(task);
        if (head != null) {
            Node oldHead = head;
            oldHead.setPrev(newHead);
            nodeMap.put(oldHead.getData().getIdNumber(), oldHead);
            newHead.setNext(oldHead);
            tail = oldHead;
        }
        if (tail == null) {
            tail = newHead;
        }
        head = newHead;
        nodeMap.put(newHead.getData().getIdNumber(), newHead);
    } */


    }


}
