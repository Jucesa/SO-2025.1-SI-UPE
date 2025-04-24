package br.upe.lista_threads.brute_force;

import java.util.Objects;

import static br.upe.lista_threads.brute_force.Encode.sha256;

public class Bruto extends Thread {

    protected long limiteProcuraInf;
    protected long limiteProcuraSup;
    private String hashedPassword;

    public static final long maxValor = 9999999999L;
    public static volatile boolean encontrada = false;

    protected Bruto(String hashedPassword, int i, int totalThreads) {
        this.hashedPassword = hashedPassword;

        long intervalo = maxValor / totalThreads;

        this.limiteProcuraInf = i * intervalo;

        if (i == totalThreads - 1) {
            this.limiteProcuraSup = maxValor;
        } else {
            this.limiteProcuraSup = (i + 1) * intervalo - 1;
        }
    }

    @Override
    public void run() {
        String threadOutput = getName() + " -> ";

        for (long i = limiteProcuraInf; i <= limiteProcuraSup && !encontrada; i++) {

            synchronized (System.out) {
                System.out.printf("\r%s%d", threadOutput, i);
            }

            if (Objects.equals(hashedPassword, sha256(Long.toString(i)))) {
                encontrada = true;
                synchronized (System.out) {
                    System.out.println();
                    System.out.println("Senha encontrada pela thread " + getName() + ": " + i);
                }
                break;
            }
        }

        synchronized (System.out) {
            System.out.println("\nThread " + getName() + " encerrando.");
        }
    }

}
