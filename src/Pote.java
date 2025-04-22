import java.util.concurrent.Semaphore;

public class Pote {
    private final int capacidade;
    private final Semaphore mutex = new Semaphore(1);
    private final Semaphore poteVazio = new Semaphore(0);
    private final Semaphore porcoes = new Semaphore(0);
    private boolean cozinheiroChamado = false;

    public Pote(int capacidade) {
        this.capacidade = capacidade;
    }

    public void pegarPorcao(int id) throws InterruptedException {
        mutex.acquire();

        if (porcoes.availablePermits() == 0 && !cozinheiroChamado) {
            cozinheiroChamado = true;
            System.out.printf("Selvagem %d viu o pote vazio e chamou o cozinheiro.%n", id);
            poteVazio.release();
        }

        mutex.release();

        porcoes.acquire();
        System.out.printf("Selvagem %d comeu. Porções restantes: %d%n", id, porcoes.availablePermits());
    }

    public void encherPote() throws InterruptedException {
        poteVazio.acquire();
        System.out.println("Cozinheiro está enchendo o pote...");
        Thread.sleep(1000);
        porcoes.release(capacidade);

        mutex.acquire();
        cozinheiroChamado = false;
        mutex.release();

        System.out.println("Cozinheiro encheu o pote.");
    }

}
