public class Selvagem extends Thread {
    private final int id;
    private final Pote pote;

    public Selvagem(int id, Pote pote) {
        this.id = id;
        this.pote = pote;
    }

    @Override
    public void run() {
        try {
            while (true) {
                pote.pegarPorcao(id);
                Thread.sleep((long) (Math.random() * 1000));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
