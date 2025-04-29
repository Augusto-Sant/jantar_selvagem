public class Selvagem extends Thread {
    private final Pote pote;

    public Selvagem(Pote pote) {
        this.pote = pote;
    }

    @Override
    public void run() {
        try {
            while (true) {
                pote.pegarPorcao();
                comer();
                Thread.sleep((long) (Math.random() * 1000));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void comer() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " está comendo.");
        Thread.sleep((long) (Math.random() * 1000));   // Simulate eating time
    }
}
