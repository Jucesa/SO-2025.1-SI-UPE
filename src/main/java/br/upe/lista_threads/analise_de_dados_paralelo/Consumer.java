package br.upe.lista_threads.analise_de_dados_paralelo;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Consumer extends Thread {
    public HashMap<String, Integer> wordCount = new HashMap<>();
    private final String filesPath;

    public static volatile long offset = 0;

    public Consumer(String filesPath){
        this.filesPath = filesPath;
    }

    private void setWordCount(List<String> palavras){
        for (String palavra : palavras) {
            if (!palavra.isEmpty()) {
                this.wordCount.put(palavra, this.wordCount.getOrDefault(palavra, 0) + 1);
            }
        }
    }

    @Override
    public void run() {
        long offset = Consumer.offset;

        int tamanhoBloco = 1024;
        List<String> palavras = new ArrayList<>();

        try (RandomAccessFile raf = new RandomAccessFile(this.filesPath, "r")) {
            if (offset >= raf.length()) Thread.currentThread().interrupt();

            raf.seek(offset);

            byte[] buffer = new byte[tamanhoBloco];
            int bytesLidos = raf.read(buffer);

            if (bytesLidos > 0) {
                String dados = new String(buffer, 0, bytesLidos);
                dados = dados.replaceAll("[^\\p{L}\\p{N}\\s]", "");
                String[] x = dados.toLowerCase().replace('\n', ' ').trim().split("\\s+"); // \\s+ para espaços múltiplos
                palavras.addAll(Arrays.asList(x));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        setWordCount(palavras);

    }
}
