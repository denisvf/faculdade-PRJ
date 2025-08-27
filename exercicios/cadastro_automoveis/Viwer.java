package exercicios.cadastro_automoveis;

import java.util.ArrayList;
import java.util.Scanner;

public class Viwer {

    private boolean running = true;
    private Automobiles automobiles = new Automobiles();
    private Scanner scanner = new Scanner(System.in);

    private void add() {
        System.out.println("Número da placa: ");
        String plate = scanner.nextLine();

        System.out.println("Modelo: ");
        String model = scanner.nextLine();

        System.out.println("Preço: ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.println("KM: ");
        double km = Double.parseDouble(scanner.nextLine());

        Automobile automobile = new Automobile(plate, model, km, price);

        boolean added = this.automobiles.add(automobile);
        if (added) {
            System.out.println("Automóvel adicionado com sucesso!");
        } else {
            System.out.println("Já existe um automóvel com essa placa.");
        }

    }

    private void remove() {
        System.out.println("Digite a placa do automóvel: ");
        String plate = scanner.nextLine();

        boolean removed = this.automobiles.removeByPlate(plate);
        if (removed) {
            System.out.println("Automóvel removido com sucesso!");
        } else {
            System.out.println("Nenhum automóvel encontrado com essa placa.");
        }
    }

    private void showAutomobile() {
        System.out.println("Digite a placa do automóvel: ");
        String plate = scanner.nextLine();

        Automobile auto = this.automobiles.getByPlate(plate);
        if (auto != null) {
            System.out.println(auto);
        } else {
            System.out.println("Automóvel não encontrado!");
        }
    }

    private void list() {
        ArrayList<Automobile> all = this.automobiles.getAutomobiles();
        if (all.isEmpty()) {
            System.out.println("Nenhum automóvel cadastrado.");
        } else {
            System.out.println("Lista de automóveis cadastrados:");
            for (Automobile auto : all) {
                System.out.println(auto);
            }
        }
    }

    private void handleOption(int option) {
        switch (option) {
            case 1 -> this.add();
            case 2 -> this.remove();
            case 3 -> this.showAutomobile();
            case 4 -> this.list();
            case 5 -> this.running = false;
            default -> System.out.println("Opção inválida! Digite novamente.");
        }
    }

    public void initialize() {
        System.out.println("Bem-vindo ao cadastro de automóveis!");

        do {
            System.out.println("\n-------- MENU --------");
            System.out.println("1 - Adicionar automóvel");
            System.out.println("2 - Excluir automóvel");
            System.out.println("3 - Consultar automóvel específico");
            System.out.println("4 - Listar todos");
            System.out.println("5 - Sair");
            System.out.print("Digite a opção desejada: ");

            try {
                int option = Integer.parseInt(scanner.nextLine());
                this.handleOption(option);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida, digite um número.");
            }

        } while (this.running);

        System.out.println("Programa finalizado.");
        scanner.close();
    }
}
