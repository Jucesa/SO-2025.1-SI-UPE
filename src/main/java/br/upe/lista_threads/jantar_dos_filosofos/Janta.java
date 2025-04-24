package br.upe.lista_threads.jantar_dos_filosofos;

import java.util.*;

public class Janta {

    private static final int NUM_FILOSOFOS = 5;
    private static final Garfo[] garfos = new Garfo[NUM_FILOSOFOS];
    private static final Filosofo[] filosofos = new Filosofo[NUM_FILOSOFOS];

    private static int segundosMaxDoRodizio = 30*1000;
    static String[] nomesFilosofos = {
            "Sócrates", "Platão", "Aristóteles", "Descartes", "Kant",
            "Nietzsche", "Confúcio", "Lao Tsé", "Epicuro", "Heráclito",
            "Parmênides", "Pitágoras", "Tales de Mileto", "Zenão de Eleia", "Demócrito",
            "Agostinho", "Tomás de Aquino", "Maquiavel", "Hobbes", "Locke",
            "Rousseau", "Voltaire", "Hume", "Marx", "Engels",
            "Heidegger", "Sartre", "Camus", "Wittgenstein", "Berkeley",
            "Spinoza", "Leibniz", "John Stuart Mill", "Simone de Beauvoir", "Foucault",
            "Deleuze", "Adorno", "Hannah Arendt", "Habermas", "Russell",
            "Peirce", "Derrida", "Rawls", "Nozick", "David Lewis",
            "Frege", "Quine", "Popper", "Kripke", "Judith Butler",
            "Al-Farabi", "Avicena", "Averróis", "Maimônides", "Ibn Khaldun",
            "Boécio", "Anselmo de Cantuária", "Erasmo de Roterdã", "Francis Bacon", "Giordano Bruno",
            "Augusto Comte", "Durkheim", "Bourdieu", "Zizek", "Byung-Chul Han"
    };

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Quantos segundos o rodízio vai durar: ");
        segundosMaxDoRodizio = scanner.nextInt()*1000;

        // Criando os garfos
        for (int i = 0; i < NUM_FILOSOFOS; i++) {
            garfos[i] = new Garfo();
        }

        // Criando os filósofos
        List<String> nomes = nomear();
        for (int i = 0; i < NUM_FILOSOFOS; i++) {
            filosofos[i] = new Filosofo(nomes.get(i), i, garfos[i], garfos[(i + 1) % NUM_FILOSOFOS]);
        }

        // Iniciando as threads dos filósofos
        for (Filosofo filosofo : filosofos) {
            filosofo.start();
        }

        // Loop para imprimir o status
        long t0 = System.currentTimeMillis();

        while ( ((System.currentTimeMillis() - t0)) < segundosMaxDoRodizio) {
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            imprimirStatus();
            Thread.sleep(3000);
        }

        for (Filosofo filosofo : filosofos) {
            filosofo.interrupt();
        }

    }




    private static ArrayList<String> nomear(){
        HashSet<String> nomesSet = new HashSet<>();
        while(nomesSet.size() < NUM_FILOSOFOS) {
            nomesSet.add(nomesFilosofos[new Random().nextInt(0, nomesFilosofos.length)]);
        }
        return new ArrayList<>(nomesSet);
    }

    private static void imprimirStatus() {
        System.out.println("=== Estado dos Filósofos ===");
        for (Filosofo filosofo : filosofos) {
            long tempoMedioEspera = (filosofo.getRefeicoes() == 0) ? 0 : filosofo.getTempoEspera() / filosofo.getRefeicoes();
            System.out.printf("%-20s está %-3s | Fatias de pizza: %-2d | Tempo médio de espera: %-5d ms\n",
                    filosofo.getName(),
                    filosofo.isComendo() ? "Comendo " : "Pensando",
                    filosofo.getRefeicoes(),
                    tempoMedioEspera);
        }
    }
}