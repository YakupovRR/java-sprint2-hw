package history;

import tasktypes.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InMemoryHistoryManager implements HistoryManager {
    private static Node head = null;
    private static Node tail = null;
   private Map<Long, Node> references;

    public InMemoryHistoryManager() {
        this.references = new HashMap<>();
            }

    @Override
    public List<Task> history() {     //вызов истории
        ArrayList<Task> tasks = new ArrayList<>();
        if (head != null) {
            Node first = head;
            while (first != null) {
                tasks.add(first.reference);
                first = first.getNext();
            }
        }
        return tasks;
    }

    @Override
    public void add(Task task) {
        linkLast(task);
    }

    @Override
    public void remove(Long id) {
        final Node node = references.get(id);
        removeNode(node);
    }

       private void linkLast(Task task) {
        if (references.containsKey(task.getId())) {
            removeNode(references.get(task.getId()));
        }
        final Node oldTail = tail;
        final Node last = new Node(
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

    private void removeNode(Node node) {
        if (node != null) {
            final Node prev = node.getPrev();
            final Node next = node.getNext();
            if (prev != null) {
                prev.setNext(next);
            } else {
                head = next;
            }
            if (next != null) {
                next.setPrev(prev);
            } else {
                tail = prev;
            }
            references.remove(node);
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
            return prev;
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
    }
}
