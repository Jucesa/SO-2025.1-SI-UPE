package br.upe.lista_threads.brute_force;


//Em computação, busca por força bruta ou busca exaustiva é uma
//técnica de solução de problemas bastante trivial, porém muito geral que consiste
//em enumerar todos os possíveis candidatos da solução e testar cada candidato
//para saber se ele satisfaz os critérios definidos. Implemente um programa que
//tente descobrir uma senha de exatamente 10 caracteres Todos os caracteres serão
//numéricos: '0' até '9’. Ele deverá verificar sistematicamente todas as possíveis
//senhas até que a correta seja encontrada. Para isso, utilize T threads, na qual cada
//deverá testar uma faixa de senhas diferentes, de forma que uma senha de teste só
//possa ser gerada/testada por uma única thread. O programa deverá ser encerrado
//quando uma thread descobrir a senha. Nesse momento, deverá ser impressa na
//tela, a senha e a identificação/número da thread que a encontrou. Todas as outras
//senhas testadas não deverão ser impressas na tela. A senha a ser quebrada será
//uma string inicializada estaticamente, assim como o número T de threads . Todas
//as senhas geradas deverão ser strings numéricas.
//OBS.: Todas threads deverão ser criadas no início do programa e, durante a
//execução, nenhuma outra thread poderá ser executada.


import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import static br.upe.lista_threads.brute_force.Encode.sha256;


public class Main {

    public static void main(String[] args) {
        int nucleos = Runtime.getRuntime().availableProcessors();
        Scanner scanner = new Scanner(System.in);
        final String CODIGO_PARADA = "SAIR";

        while (true) {
            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.println("Digite '" + CODIGO_PARADA + "' para encerrar ou pressione ENTER para continuar.");
            String comando = scanner.nextLine();
            if (comando.equalsIgnoreCase(CODIGO_PARADA)) {
                System.out.println("Encerrando o programa...");
                break;
            }
            Bruto.encontrada = false;
            long senha = ThreadLocalRandom.current().nextLong(0, Bruto.maxValor);
            String hashedPassword = sha256(Long.toString(senha));
            System.out.println("A senha é: " + senha);

            System.out.println("Núcleos disponíveis: " + nucleos);

            System.out.print("Quantas threads para quebrar a senha:");
            int threadsAmount = scanner.nextInt();
            scanner.nextLine();

            Thread[] threads = new Thread[threadsAmount];

            for(int i = 0; i < threadsAmount; i++) {
                threads[i] = new Bruto(hashedPassword, i, threadsAmount);
            }

            System.out.println();
            long t0 = System.currentTimeMillis();
            for(int i = 0; i < threadsAmount; i++) {
                threads[i].start();
            }

            for(int i = 0; i < threadsAmount; i++) {
                try {
                    threads[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            double tempo = (System.currentTimeMillis() - t0)/1000.0;
            System.out.println("Tempo para quebrar a senha: " + tempo +"s");
        }
        scanner.close();
    }
}


