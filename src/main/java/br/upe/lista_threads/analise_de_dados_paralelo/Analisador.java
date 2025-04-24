package br.upe.lista_threads.analise_de_dados_paralelo;
import java.io.*;
import java.util.*;

public class Analisador {
    public static final int N = 2; // Top N palavras
    public static final int T = 3; // Quantidade de threads
    private static final String filePath = "src/main/java/br/upe/lista_threads/analise_de_dados_paralelo/mangaio.txt";
    private static final HashMap<String, Integer> wordsCount = new HashMap<>();

    public static void main(String[] args) {
        try {
            long startTime = System.currentTimeMillis();

            List<String> linhas = lerLinhas(filePath);
            iniciarThreads(linhas);

            // Ordenar e imprimir as N palavras mais frequentes
            List<Map.Entry<String, Integer>> sortedWords = new ArrayList<>(wordsCount.entrySet());
            sortedWords.sort((a, b) -> b.getValue().compareTo(a.getValue()));

            System.out.println("As " + N + " palavras mais frequentes:");
            for (int i = 0; i < N && i < sortedWords.size(); i++) {
                System.out.println(sortedWords.get(i).getKey() + " -> " + sortedWords.get(i).getValue() + " vezes");
            }

            long endTime = System.currentTimeMillis();
            System.out.println("Tempo total: " + (endTime - startTime) / 1000.0 + " segundos");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static List<String> lerLinhas(String filePath) {
        List<String> linhas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                linhas.add(line.toLowerCase());
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return linhas;
    }

    public static void iniciarThreads(List<String> linhas) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < T; i++) {
            final int threadIndex = i;
            Thread thread = new Thread(() -> {
                for (int j = threadIndex; j < linhas.size(); j += T) {
                    String linha = linhas.get(j);
                    String[] palavras = linha.split("\\W+");
                    for (String palavra : palavras) {
                        if (!palavra.isEmpty()) {
                            synchronized (wordsCount) {
                                wordsCount.put(palavra, wordsCount.getOrDefault(palavra, 0) + 1);
                            }
                        }
                    }
                }
            });
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }
}
