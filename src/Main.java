public class Main {
    public static void main(String[] args) {
        int numeroSelvagens = 5;
        int capacidadePote = 3;

        Pote pote = new Pote(capacidadePote);

        Thread cozinheiro = new Thread(new Cozinheiro(pote), "Cozinheiro");
        cozinheiro.start();

        for (int i = 0; i < numeroSelvagens; i++) {
            Thread selvagem = new Thread(new Selvagem(pote), "Selvagem-" + i);
            selvagem.start();
        }
    }
}