package br.upe.lista_threads.corrida_revezamento;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Corredor implements Runnable {
    private final String nomeEquipe;
    private final String nome;
    private final CyclicBarrier[] barreiras;

    public Corredor(String nomeEquipe, String nome, CyclicBarrier[] barreiras) {
        this.nomeEquipe = nomeEquipe;
        this.nome = nome;
        this.barreiras = barreiras;
    }

    @Override
    public void run() {
        try {
            System.out.println("Corredor " + nome + " da equipe " + nomeEquipe + " partiu do ponto A!");

            for (int ponto = 0; ponto < barreiras.length; ponto++) {
                Thread.sleep((int) (Math.random() * 1000));
                System.out.println("Corredor " + nome + " da equipe " + nomeEquipe + " chegou ao ponto " + ponto);
                barreiras[ponto].await();
            }

            System.out.println("Corredor " + nome + " da equipe " + nomeEquipe + " terminou a corrida!");
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
