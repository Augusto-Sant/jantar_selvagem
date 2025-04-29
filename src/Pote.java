import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Pote {
    private int porcoes = 0;
    private final int capacidade;
    private final Lock lock = new ReentrantLock();
    private final Condition vazio = lock.newCondition();
    private final Condition cheio = lock.newCondition();

    public Pote(int capacidade) {
        this.capacidade = capacidade;
    }

    public void pegarPorcao() throws InterruptedException {
        lock.lock();
        try {
            while (porcoes == 0) {
                System.out.println(Thread.currentThread().getName() + " encontrou pote vazio, acordando cozinheiro.");
                vazio.signal(); // acorda cozinheiro
                cheio.await(); // espera pote ser enchido novamente
            }
            Thread.sleep((long) (Math.random() * 1000));
            porcoes--;
            System.out.println(Thread.currentThread().getName() + " pegou uma porção. porções restantes: " + porcoes);
        } finally {
            lock.unlock();
        }
    }

    public void encherPote() throws InterruptedException {
        lock.lock();
        try {
            while (porcoes > 0) {
                vazio.await();
            }
            System.out.println("cozinheiro está enchendo pote.");
            Thread.sleep((long) (Math.random() * 3000));
            porcoes = capacidade;
            cheio.signalAll(); // acorda todos os selvagens dormindo
        } finally {
            lock.unlock();
        }
    }
}