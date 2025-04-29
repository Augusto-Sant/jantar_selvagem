import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Pote {
    private int porcoes = 0;
    private final int capacidade;
    private final Lock lock = new ReentrantLock();
    private final Condition vazio = lock.newCondition();
    private final Condition cheio = lock.newCondition();

    private boolean cozinheiroNotificado = false; // NEW FLAG

    public Pote(int capacidade) {
        this.capacidade = capacidade;
    }

    public void pegarPorcao() throws InterruptedException {
        lock.lock();
        try {
            while (porcoes == 0) {

                // Only one thread notifies the cook
                if (!cozinheiroNotificado) {
                    System.out.println(Thread.currentThread().getName() + " encontrou pote vazio, acordando cozinheiro..");
                    cozinheiroNotificado = true;
                    vazio.signal(); // Acorda o cozinheiro apenas uma vez
                }

                cheio.await(); // Espera até que o pote seja reenchido
            }

            Thread.sleep((long) (Math.random() * 2000));
            porcoes--;
            System.out.println(Thread.currentThread().getName() + " pegou uma porção. Porções restantes: " + porcoes);
        } finally {
            lock.unlock();
        }
    }

    public void encherPote() throws InterruptedException {
        lock.lock();
        try {
            while (porcoes > 0) {
                System.out.println("Cozinheiro esperando... pote ainda tem comida.");
                vazio.await(); // Aguarda até que o pote esteja vazio
            }

            System.out.println("Cozinheiro está enchendo o pote.");
            Thread.sleep((long) (Math.random() * 5000)); // Simula tempo de cozimento
            porcoes = capacidade;
            cozinheiroNotificado = false; // Reseta o sinalizador
            System.out.println("Cozinheiro terminou de encher. Porções adicionadas: " + porcoes);

            cheio.signalAll(); // Acorde todos os selvagens
        } finally {
            lock.unlock();
        }
    }
}