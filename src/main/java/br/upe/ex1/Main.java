package br.upe.ex1;

import java.util.Scanner;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final String CODIGO_PARADA = "SAIR";

        while (true) {
            System.out.println("Digite '" + CODIGO_PARADA + "' para encerrar ou pressione ENTER para continuar.");
            String comando = scanner.nextLine();
            if (comando.equalsIgnoreCase(CODIGO_PARADA)) {
                System.out.println("Encerrando o programa...");
                break;
            }

            System.out.println("Deseja REMOVER as sequências detectadas? (s/n):");
            String opcaoRemover = scanner.nextLine().trim().toLowerCase();
            boolean remover = opcaoRemover.equals("s");

            System.out.println("Digite uma sequência de letras e números:");
            String input = scanner.nextLine();

            String[] dados = input.split("");

            Thread letras = new DetectorLetras(dados, remover);
            Thread numeros = new DetectorNumeros(dados, remover);

            letras.start();
            numeros.start();

            try {
                letras.join();
                numeros.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Estado final do array: " + Arrays.toString(dados));
            System.out.println("------------------------------------------------------");
        }

        scanner.close();
    }
}
