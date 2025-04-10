package br.upe.ex1;

import java.util.ArrayList;

class DetectorNumeros extends Detector {

    public DetectorNumeros(String[] dados, boolean remover) {
        super(dados, remover);
    }

    @Override
    public void run() {
        ArrayList<String> sequencia = new ArrayList<>();

        for (String s : dados) {
            if (s.matches("\\d")) {
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
            removeSequencia(sequencia, "Números");
        } else {
            imprimirSequencia(sequencia, "Números");
        }
    }
}
