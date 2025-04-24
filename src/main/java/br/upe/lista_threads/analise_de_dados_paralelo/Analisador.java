package br.upe.lista_threads.analise_de_dados_paralelo;

import java.util.*;

public class Analisador {
    public static final int N = 20; // Top N palavras
    public static final int T = 2; // Quantidade de threads
    private static final String filePath = "src/main/java/br/upe/lista_threads/analise_de_dados_paralelo/mangaio.txt";
    public static HashMap<String, Integer> wordsCount = new HashMap<>();

    private static void printWordCount(HashMap<String, Integer> wordsCount){

        // Ordenar e imprimir as N palavras mais frequentes
        List<Map.Entry<String, Integer>> sortedWords = new ArrayList<>(wordsCount.entrySet());
        sortedWords.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        System.out.println("As " + Analisador.N + " palavras mais frequentes:");
        for (int i = 0; i < Analisador.N && i < sortedWords.size(); i++) {
            System.out.println(sortedWords.get(i).getKey() + " -> " + sortedWords.get(i).getValue() + " vezes");
        }
    }



    public static void main(String[] args) throws InterruptedException {

        long startTime = System.currentTimeMillis();

        List<Consumer> threads = new ArrayList<>();
        for(int i = 0; i < T; i++){
            threads.add(new Consumer(filePath));
        }

        for(Thread t : threads){
            t.start();
        }

        for(Thread t : threads){
            t.join();
        }

        for(Consumer t : threads){
            for (Map.Entry<String, Integer> entry : t.wordCount.entrySet()) {
                String palavra = entry.getKey();
                int contagem = entry.getValue();

                wordsCount.put(palavra, wordsCount.getOrDefault(palavra, 0) + contagem);
            }
        }

        printWordCount(wordsCount);

        long endTime = System.currentTimeMillis();
        System.out.println("Tempo total: " + (endTime - startTime) / 1000.0 + " segundos");
    }



}
