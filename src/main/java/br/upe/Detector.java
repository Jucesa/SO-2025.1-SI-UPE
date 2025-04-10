package br.upe;

import java.util.ArrayList;
import java.util.Arrays;

abstract class Detector extends Thread {
    protected String[] dados;
    protected boolean remover = false;

    protected Detector(String[] dados, boolean remover) {
        this.dados = dados;
        this.remover = remover;
    }

    protected void imprimirSequencia(ArrayList<String> sequencia, String tipo) {
        synchronized (System.out) {
            System.out.print("Thread " + tipo + ": ");
            for (String s : sequencia) {
                System.out.print(s);
            }
            System.out.println();
        }
    }

    protected void removeSequencia(ArrayList<String> sequencia, String tipo) {
        synchronized (System.out) {
            System.out.print("Thread " + tipo + ": ");
            for (String s : sequencia) {
                System.out.print(s);
            }
            System.out.println();

            for (String s : sequencia) {

                for (int i = 0; i < dados.length; i++) {
                    if (dados[i].equals(s)) {
                        dados[i] = "";
                        break;
                    }
                }
            }

            sequencia.clear();
            System.out.println("Array atual: " + Arrays.toString(dados).replace("," , " "));
        }


    }
}
