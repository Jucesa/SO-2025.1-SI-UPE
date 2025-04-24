package br.upe.lista_threads.jantar_dos_filosofos;

import java.util.concurrent.Semaphore;

public class Garfo {
    private final Semaphore semaphore = new Semaphore(1);

    public void pegar() throws InterruptedException {
        semaphore.acquire();
    }

    public void soltar() {
        semaphore.release();
    }
}
