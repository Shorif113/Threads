import java.util.LinkedList;

public class SimpleQueue<T> {
    private LinkedList<T> queue = new LinkedList<>();

    public synchronized void enqueue(T item) {
        queue.add(item);
        notify();
    }

    public synchronized T dequeue() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        return queue.remove();
    }

    public static void main(String[] args) {
        SimpleQueue<Integer> queue = new SimpleQueue<>();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                queue.enqueue(i);
                System.out.println("Enqueued: " + i);
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    System.out.println("Dequeued: " + queue.dequeue());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }
}

