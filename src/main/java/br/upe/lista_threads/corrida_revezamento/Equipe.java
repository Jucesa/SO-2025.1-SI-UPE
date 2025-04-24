package br.upe.lista_threads.corrida_revezamento;

import java.util.concurrent.CyclicBarrier;

public class Equipe {
    private final String nomeEquipe;
    private final String[] nomesCorredores;
    private final CyclicBarrier[] barreiras;

    public Equipe(String nomeEquipe, String[] nomesCorredores, int numPontos) {
        this.nomeEquipe = nomeEquipe;
        this.nomesCorredores = nomesCorredores;
        this.barreiras = new CyclicBarrier[numPontos];

        for (int ponto = 0; ponto < numPontos; ponto++) {
            int pontoFinal = ponto;
            barreiras[ponto] = new CyclicBarrier(nomesCorredores.length, () ->
                    System.out.println("Equipe " + nomeEquipe + " pronta para sair do ponto " + pontoFinal)
            );
        }
    }

    public void iniciarCorrida() {
        for (String nome : nomesCorredores) {
            Thread t = new Thread(new Corredor(nomeEquipe, nome, barreiras));
            t.start();
        }
    }
}
