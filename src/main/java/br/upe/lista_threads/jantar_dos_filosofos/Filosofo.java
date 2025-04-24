package br.upe.lista_threads.jantar_dos_filosofos;

import java.util.Random;

public class Filosofo extends Thread {
    private int asento;
    private boolean pensando = true;
    private boolean comendo = false;

    private Garfo esquerdo;
    private Garfo direito;

    private int refeicoes = 0;
    private long tempoTotalEspera = 0;
    private long tempoDePensamento = 0;
    private long tempoDeComida = 0;

    public Filosofo(String nome, int asento, Garfo esquerdo, Garfo direito) {
        this.setName(nome);
        this.asento = asento;
        this.esquerdo = esquerdo;
        this.direito = direito;
    }

    private void pensar() throws InterruptedException {
        long inicio = System.currentTimeMillis();
        this.comendo = false;
        this.pensando = true;
        Thread.sleep(new Random().nextInt(1000));
        tempoDePensamento += System.currentTimeMillis() - inicio;
    }

    private void comer() throws InterruptedException {
        long inicio = System.currentTimeMillis();
        this.pensando = false;
        this.comendo = true;
        Thread.sleep(new Random().nextInt(1000));
        this.refeicoes++;
        tempoDeComida += System.currentTimeMillis() - inicio;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                pensar();
                long tempoEspera = 0;

                // Tentando pegar os garfos
                long inicioEspera = System.currentTimeMillis();

                if(asento % 2 == 0){
                    direito.pegar();
                    tempoEspera += System.currentTimeMillis() - inicioEspera;

                    inicioEspera = System.currentTimeMillis();
                    esquerdo.pegar();

                }  else {
                    esquerdo.pegar();
                    tempoEspera += System.currentTimeMillis() - inicioEspera;

                    inicioEspera = System.currentTimeMillis();
                    direito.pegar();
                }
                tempoEspera += System.currentTimeMillis() - inicioEspera;


                tempoTotalEspera += tempoEspera;

                comer();

                esquerdo.soltar();
                direito.soltar();
            }
        } catch (InterruptedException e) {
            System.out.println(getName() + " ficou triste porquê o rodízio acabou");
        }
    }

    public boolean isPensando() {
        return pensando;
    }

    public boolean isComendo() {
        return comendo;
    }

    public int getRefeicoes() {
        return refeicoes;
    }

    public long getTempoEspera() {
        return tempoTotalEspera;
    }

    public long getTempoDePensamento() {
        return tempoDePensamento;
    }

    public long getTempoDeComida() {
        return tempoDeComida;
    }
}
