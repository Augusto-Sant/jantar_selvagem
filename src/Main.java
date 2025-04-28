public class Main {
    public static void main(String[] args) {
        int numSavages = 5;
        int potCapacity = 3;

        Pote pote = new Pote(potCapacity);

        Thread cozinheiro = new Thread(new Cozinheiro(pote), "Cook");
        cozinheiro.start();

        for (int i = 0; i < numSavages; i++) {
            Thread selvagem = new Thread(new Selvagem(pote), "Savage-" + i);
            selvagem.start();
        }
    }
}