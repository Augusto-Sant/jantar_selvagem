import java.util.concurrent.Semaphore;

public class Pote {
    private final int capacidade;
    private final Semaphore mutex = new Semaphore(1);
    private final Semaphore poteVazio = new Semaphore(0);
    private final Semaphore porcoes = new Semaphore(0);
    private boolean cozinheiroChamado = false;
    private int porcoesConsumidas = 0;

    public Pote(int capacidade) {
        this.capacidade = capacidade;
    }

    public void pegarPorcao(int id) throws InterruptedException {
        boolean comeu = false;

        while (!comeu) {
            mutex.acquire();

            if (porcoes.availablePermits() == 0 && !cozinheiroChamado) {
                cozinheiroChamado = true;
                System.out.printf("Selvagem %d viu o pote vazio e chamou o cozinheiro.%n", id);
                poteVazio.release();
            }

            if (porcoes.tryAcquire()) {
                porcoesConsumidas++;
                System.out.printf("Selvagem %d comeu. Porções comidas nesta rodada: %d/%d%n",
                        id, porcoesConsumidas, capacidade);
                comeu = true;
            }

            mutex.release();

            if (!comeu) Thread.sleep(100); // espera antes de tentar de novo
        }
    }

    public void encherPote() throws InterruptedException {
        while (true) {
            poteVazio.acquire();

            System.out.println("Cozinheiro está enchendo o pote...");
            Thread.sleep(1000); // simula tempo de cozimento

            porcoes.release(capacidade);

            mutex.acquire();
            cozinheiroChamado = false;
            porcoesConsumidas = 0;
            mutex.release();

            System.out.println("Cozinheiro encheu o pote.");
        }
    }
}