package br.upe.lista_threads.corrida_revezamento;
import java.util.concurrent.CyclicBarrier;

public class Main {
    public static final int NUM_PONTOS = 3; // Pontos B, C, D

    public static void main(String[] args) {
        String[] nomesMeninas = {"Italan", "Julio", "Winicius"};
        String[] nomesBoys = {"Kyo", "Leticia", "Eduarda"};

        Equipe meninasSuperpoderosas = new Equipe("Meninas Superpoderosas", nomesMeninas, NUM_PONTOS);
        Equipe theBoys = new Equipe("The Boys",  nomesBoys, NUM_PONTOS);

        meninasSuperpoderosas.iniciarCorrida();
        theBoys.iniciarCorrida();
    }
}


