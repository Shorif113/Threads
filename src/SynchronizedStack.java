import java.util.Stack;
import java.util.Random;

public class SynchronizedStack {
    private Stack<Integer> stack = new Stack<>();
    private static final int MAX_SIZE = 10;

    public synchronized void push(int value) throws InterruptedException {
        while (stack.size() == MAX_SIZE) {
            wait();
        }
        stack.push(value);
        System.out.println("Pushed: " + value);
        notify();
    }

    public synchronized int pop() throws InterruptedException {
        while (stack.isEmpty()) {
            wait();
        }
        int value = stack.pop();
        System.out.println("Popped: " + value);
        notify();
        return value;
    }

    public static void main(String[] args) {
        SynchronizedStack stack = new SynchronizedStack();

        Thread producer = new Thread(() -> {
            Random random = new Random();
            for (int i = 0; i < 100; i++) {
                try {
                    stack.push(random.nextInt(100) + 1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    stack.pop();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        producer.start();
        consumer.start();
    }
}
