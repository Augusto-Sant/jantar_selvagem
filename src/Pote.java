import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Pote {
    private int servings = 0;
    private final int capacity;
    private final Lock lock = new ReentrantLock();
    private final Condition empty = lock.newCondition();
    private final Condition full = lock.newCondition();

    public Pote(int capacity) {
        this.capacity = capacity;
    }

    public void pegarPorcao() throws InterruptedException {
        lock.lock();
        try {
            while (servings == 0) {
                System.out.println(Thread.currentThread().getName() + " found pot empty, waking cook.");
                empty.signal();               // Wake the cook
                full.await();                // Wait until the pot is refilled
            }
            servings--;
            System.out.println(Thread.currentThread().getName() + " got a serving. Servings left: " + servings);
        } finally {
            lock.unlock();
        }
    }

    public void encherPote() throws InterruptedException {
        lock.lock();
        try {
            // Wait until pot is empty
            while (servings > 0) {
                empty.await();
            }
            System.out.println("Cook is refilling the pot.");
            servings = capacity;
            full.signalAll();             // Wake all waiting savages
        } finally {
            lock.unlock();
        }
    }
}