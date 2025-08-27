package report;

import model.ItemPedido;
import model.Pedido;

import java.util.List;

public class RelatorioDetalhado implements RelatorioVendas {

    @Override
    public void gerar(List<Pedido> pedidos) {
        System.out.println("=== RELATÓRIO DETALHADO ===");
        for (Pedido pedido : pedidos) {
            System.out.println("Pedido #" + pedido.getNumero());
            System.out.println("Cliente: " + pedido.getCliente().getNome());
            System.out.println("Itens:");
            for (ItemPedido item : pedido.getItens()) {
                System.out.printf("- %s x%d = R$ %.2f%n",
                        item.getItem().getNome(),
                        item.getQuantidade(),
                        item.getSubtotal());
            }
            System.out.printf("Total: R$ %.2f%n", pedido.getValorTotal());
            System.out.println("Status: " + pedido.getStatus());
            System.out.println("----------------------------");
        }
        System.out.println("============================");
    }
}