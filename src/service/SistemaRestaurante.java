package service;

import model.*;

import java.util.List;
import java.util.stream.Collectors;

public class SistemaRestaurante {
    private final CentralDados dados = CentralDados.getInstance();

    public Cliente registrarNovoCliente(String nome, String telefone) {
        return dados.cadastrarCliente(nome, telefone);
    }

    public ItemCardapio registrarNovoItem(String nome, double preco) {
        return dados.cadastrarItem(nome, preco);
    }

    public Pedido criarPedido(int clienteId) {
        return dados.criarPedido(clienteId);
    }

    public boolean adicionarItemAoPedido(int pedidoId, int itemId, int quantidade) {
        Pedido pedido = dados.buscarPedidoPorId(pedidoId);
        ItemCardapio item = dados.buscarItemPorId(itemId);
        if (pedido != null && item != null && quantidade > 0) {
            pedido.adicionarItem(item, quantidade);
            return true;
        }
        return false;
    }

    public boolean avancarStatusPedido(int pedidoId) {
        Pedido pedido = dados.buscarPedidoPorId(pedidoId);
        if (pedido != null) {
            return pedido.avancarStatus();
        }
        return false;
    }

    public List<Pedido> consultarPedidosPorStatus(StatusPedido status) {
        return dados.getPedidos().stream()
                .filter(p -> p.getStatus() == status)
                .collect(Collectors.toList());
    }

    public List<Cliente> listarClientes() {
        return dados.getClientes();
    }

    public List<ItemCardapio> listarItensCardapio() {
        return dados.getItensCardapio();
    }

    public List<Pedido> listarTodosPedidos() {
        return dados.getPedidos();
    }
}
