package service;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class CentralDados {
    private static CentralDados instance;
    private final List<Cliente> clientes = new ArrayList<>();
    private final List<ItemCardapio> itensCardapio = new ArrayList<>();
    private final List<Pedido> pedidos = new ArrayList<>();
    private int nextClienteId = 1;
    private int nextItemId = 1;
    private int nextPedidoId = 1;

    private CentralDados() {}

    public static CentralDados getInstance() {
        if (instance == null) {
            instance = new CentralDados();
        }
        return instance;
    }

    public Cliente cadastrarCliente(String nome, String telefone) {
        Cliente cliente = new Cliente(nextClienteId++, nome, telefone);
        clientes.add(cliente);
        return cliente;
    }

    public ItemCardapio cadastrarItem(String nome, double preco) {
        ItemCardapio item = new ItemCardapio(nextItemId++, nome, preco);
        itensCardapio.add(item);
        return item;
    }

    public Pedido criarPedido(int clienteId) {
        Cliente cliente = buscarClientePorId(clienteId);
        if (cliente == null) return null;
        Pedido pedido = new Pedido(nextPedidoId++, cliente);
        pedidos.add(pedido);
        return pedido;
    }


    public Cliente buscarClientePorId(int id) {
        return clientes.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    public ItemCardapio buscarItemPorId(int id) {
        return itensCardapio.stream().filter(i -> i.getId() == id).findFirst().orElse(null);
    }

    public Pedido buscarPedidoPorId(int id) {
        return pedidos.stream().filter(p -> p.getNumero() == id).findFirst().orElse(null);
    }

    public List<Cliente> getClientes() { return clientes; }
    public List<ItemCardapio> getItensCardapio() { return itensCardapio; }
    public List<Pedido> getPedidos() { return pedidos; }
}