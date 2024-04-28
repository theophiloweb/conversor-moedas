package com.teophilosilva.model.principal;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.teophilosilva.model.app.Coin;
import com.teophilosilva.model.app.CurrencyConverter;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        Coin coin = new Coin();
        coin.currenciesCoin();
        ArrayList<String> coinList = new ArrayList<>(coin.getCoin());

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Selecione uma opção:");
            System.out.println("1 - Converter");
            System.out.println("2 - Sair");

            int option = scanner.nextInt();

            if (option == 1) {

                var entradaValida = false;

                while (!entradaValida) {

                    System.out.println("Selecione uma moeda:");
                    for (int i = 0; i < coinList.size(); i++) {
                        System.out.printf("%d. %s%n", (i + 1), coinList.get(i).trim());
                    }

                    System.out.println("Digite os dois valores para cotação");
                    String entrada = scanner.nextLine();


                    String[] valores = entrada.trim().split(" ");

                    int[] valoresInteiros = new int[valores.length];

                    if (valoresInteiros.length == 2) {
                        try {

                            valoresInteiros[0] = Integer.parseInt(valores[0]);
                            valoresInteiros[1] = Integer.parseInt(valores[1]);
                            entradaValida = true;

                            System.out.println("Qual valor a ser cotado?:");
                            double value = scanner.nextDouble();

                            if (valoresInteiros[0] >= 1 && valoresInteiros[0] <= coinList.size()) {
                                String selectedFirst = coinList.get(valoresInteiros[0] - 1);
                                String selectedSecond = coinList.get(valoresInteiros[1] - 1);

                                String[] partsOne = selectedFirst.split(":");
                                String[] partsTwo = selectedSecond.split(":");

                                String selectCoinOne = partsOne[0].replaceAll("\"", "");
                                String descriptionCoinOne = partsOne[1].replaceAll("\"", "");

                                String selectCoinTwo = partsTwo[0].replaceAll("\"", "");
                                String descriptionCoinTwo = partsTwo[1].replaceAll("\"", "");

                                System.out.printf("A moeda base é  " + selectCoinOne + " => " + descriptionCoinOne);
                                System.out.printf("A moeda cotada é " + selectCoinTwo + " => " + descriptionCoinTwo);

                                // Realizar a cotação
                                CurrencyConverter currencyConverter = new CurrencyConverter(value, selectCoinOne, selectCoinTwo);
                                currencyConverter.currencyConverter();
                                String json = currencyConverter.getResult();
                                // Analisar a string JSON
                                JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
                                // Extrair o valor da chave "rate"
                                double currency = jsonObject.get(selectCoinTwo).getAsDouble();
                                // Imprimir o valor da taxa
                                System.out.println("O montante de " + value + " em  " + selectCoinOne + " equivale a " + currency + " em " + selectCoinTwo);

                            } else {
                                System.out.println("Opção inválida");
                            }


                        } catch (Exception e) {

                        }

                    } // While Interno

                }

            } else if (option == 2) {
                System.out.println("Saindo do sistema...");
                break;
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }

        }

    }

}