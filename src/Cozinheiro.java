public class Cozinheiro extends Thread {
    private final Pote pote;

    public Cozinheiro(Pote pote) {
        this.pote = pote;
    }

    @Override
    public void run() {
        try {
            while (true) {
                pote.encherPote();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
