package cli;

import model.*;
import report.*;
import service.SistemaRestaurante;

import java.util.List;
import java.util.Scanner;

public class MenuPrincipal {
    private final SistemaRestaurante sistema = new SistemaRestaurante();
    private final Scanner scanner = new Scanner(System.in);

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Cadastrar Item do Cardápio");
            System.out.println("4. Listar Itens do Cardápio");
            System.out.println("5. Criar Pedido");
            System.out.println("6. Avançar Status de Pedido");
            System.out.println("7. Consultar Pedidos por Status");
            System.out.println("8. Gerar Relatório Simplificado");
            System.out.println("9. Gerar Relatório Detalhado");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            switch (opcao) {
                case 1 -> cadastrarCliente();
                case 2 -> listarClientes();
                case 3 -> cadastrarItem();
                case 4 -> listarItens();
                case 5 -> criarPedido();
                case 6 -> avancarStatus();
                case 7 -> consultarPorStatus();
                case 8 -> gerarRelatorio(new RelatorioSimplificado());
                case 9 -> gerarRelatorio(new RelatorioDetalhado());
                case 0 -> System.out.println("Encerrando o sistema...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void cadastrarCliente() {
        System.out.print("Nome do cliente: ");
        String nome = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        Cliente cliente = sistema.registrarNovoCliente(nome, telefone);
        System.out.println("Cliente cadastrado com ID: " + cliente.getId());
    }

    private void listarClientes() {
        List<Cliente> clientes = sistema.listarClientes();
        System.out.println("=== CLIENTES CADASTRADOS ===");
        for (Cliente c : clientes) {
            System.out.printf("ID: %d | Nome: %s | Telefone: %s%n", c.getId(), c.getNome(), c.getTelefone());
        }
    }

    private void cadastrarItem() {
        System.out.print("Nome do item: ");
        String nome = scanner.nextLine();
        System.out.print("Preço: ");
        double preco = scanner.nextDouble();
        scanner.nextLine();
        ItemCardapio item = sistema.registrarNovoItem(nome, preco);
        System.out.println("Item cadastrado com ID: " + item.getId());
    }

    private void listarItens() {
        List<ItemCardapio> itens = sistema.listarItensCardapio();
        System.out.println("=== ITENS DO CARDÁPIO ===");
        for (ItemCardapio i : itens) {
            System.out.printf("ID: %d | Nome: %s | Preço: R$ %.2f%n", i.getId(), i.getNome(), i.getPreco());
        }
    }

    private void criarPedido() {
        listarClientes();
        System.out.print("Digite o ID do cliente: ");
        int clienteId = scanner.nextInt();
        scanner.nextLine();
        Pedido pedido = sistema.criarPedido(clienteId);
        if (pedido == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        boolean adicionando = true;
        while (adicionando) {
            listarItens();
            System.out.print("Digite o ID do item: ");
            int itemId = scanner.nextInt();
            System.out.print("Quantidade: ");
            int quantidade = scanner.nextInt();
            scanner.nextLine();
            boolean sucesso = sistema.adicionarItemAoPedido(pedido.getNumero(), itemId, quantidade);
            if (!sucesso) {
                System.out.println("Item inválido ou quantidade incorreta.");
            }

            System.out.print("Adicionar mais itens? (s/n): ");
            String resposta = scanner.nextLine();
            adicionando = resposta.equalsIgnoreCase("s");
        }

        System.out.println("Pedido criado com número: " + pedido.getNumero());
    }

    private void avancarStatus() {
        System.out.print("Digite o número do pedido: ");
        int pedidoId = scanner.nextInt();
        scanner.nextLine();
        boolean sucesso = sistema.avancarStatusPedido(pedidoId);
        if (sucesso) {
            System.out.println("Status do pedido avançado com sucesso.");
        } else {
            System.out.println("Não foi possível avançar o status.");
        }
    }

    private void consultarPorStatus() {
        System.out.println("Escolha o status:");
        for (StatusPedido status : StatusPedido.values()) {
            System.out.println("- " + status);
        }
        System.out.print("Digite o status: ");
        String statusStr = scanner.nextLine().toUpperCase();
        try {
            StatusPedido status = StatusPedido.valueOf(statusStr);
            List<Pedido> pedidos = sistema.consultarPedidosPorStatus(status);
            System.out.println("=== PEDIDOS COM STATUS " + status + " ===");
            for (Pedido p : pedidos) {
                System.out.printf("Pedido #%d | Cliente: %s | Total: R$ %.2f%n",
                        p.getNumero(), p.getCliente().getNome(), p.getValorTotal());
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Status inválido.");
        }
    }

    private void gerarRelatorio(RelatorioVendas relatorio) {
        List<Pedido> pedidos = sistema.listarTodosPedidos();
        relatorio.gerar(pedidos);
    }
}