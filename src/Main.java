public class Main {
    public static void main(String[] args) {
        int capacidadePote = 3;
        int numeroSelvagens = 5;

        Pote pote = new Pote(capacidadePote);
        Cozinheiro cozinheiro = new Cozinheiro(pote);
        cozinheiro.start();

        for (int i = 1; i <= numeroSelvagens; i++) {
            new Selvagem(i, pote).start();
        }
    }
}