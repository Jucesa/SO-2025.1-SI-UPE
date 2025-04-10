package br.upe;

import java.util.ArrayList;

class DetectorLetras extends Detector {

    public DetectorLetras(String[] dados, boolean remover) {
        super(dados, remover);
    }

    @Override
    public void run() {
        ArrayList<String> sequencia = new ArrayList<>();

        for (String s : dados) {
            if (s.matches("[a-zA-Z]")) {
                sequencia.add(s);
            } else {
                processarSequencia(sequencia);
                sequencia.clear();
            }
        }

        processarSequencia(sequencia);
    }

    private void processarSequencia(ArrayList<String> sequencia) {
        if (sequencia.isEmpty()) return;

        if (remover) {
            removeSequencia(sequencia, "Letras");
        } else {
            imprimirSequencia(sequencia, "Letras");
        }
    }
}
