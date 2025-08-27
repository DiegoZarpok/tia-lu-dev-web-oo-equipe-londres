package report;

import model.Pedido;

import java.util.List;

public class RelatorioSimplificado implements RelatorioVendas {

    @Override
    public void gerar(List<Pedido> pedidos) {
        int totalPedidos = pedidos.size();
        double totalArrecadado = pedidos.stream()
                .mapToDouble(Pedido::getValorTotal)
                .sum();

        System.out.println("=== RELATÓRIO SIMPLIFICADO ===");
        System.out.println("Total de pedidos: " + totalPedidos);
        System.out.printf("Valor total arrecadado: R$ %.2f%n", totalArrecadado);
        System.out.println("==============================");
    }
}