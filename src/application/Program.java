package application;

import entities.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Program {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        System.out.print("Entre com o caminho do arquivo: ");
        String path = sc.nextLine();
        System.out.println();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            List<Product> list = new ArrayList<>();
            String line = br.readLine();
            while (line != null) {
                String[] fields = line.split(",");
                list.add(new Product(fields[0], Double.parseDouble(fields[1])));
                line = br.readLine();
            }

            double average = list.stream()
                    .map(p -> p.getPrice())
                    .reduce(0.0, (x, y) -> x + y) / list.size();

            //map pegar apenas o preço
            //reduce soma os valores

            System.out.printf("Preço médio dos produtos: %.2f %n%n", average);

            Comparator<String> comp = (n1, n2) -> n1.toUpperCase().compareTo(n2.toUpperCase());

            List<String> priceMenor = list.stream()
                    .filter(p -> p.getPrice() < average)
                    .map(p -> p.getName())
                    .sorted(comp.reversed()).collect(Collectors.toList());

            //filter vai filtrar aqueles produtos com valor menos que a média
            //map vai pegar apenas o nome dos produtos
            //sorted organiza em ordem decrescente os nomes com base no meu comp (apenas porque coloquei o .reversed)

            System.out.println("Produtos com o valor abaixo da média: ");
            priceMenor.forEach(System.out::println);

        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }

        sc.close();
    }

}
