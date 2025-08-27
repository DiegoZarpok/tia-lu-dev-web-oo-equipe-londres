package model;

import service.CentralDados;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.println;

public class Pedido {
    private  int numero;
    private final Cliente cliente;
    private final List<ItemPedido> itens;
    private double valorTotal;
    private StatusPedido status;
    private LocalDateTime dataHora;
    private final List<PedidoListener> listeners = new ArrayList<>();

    public Pedido(int numero, Cliente cliente) {
        this.numero = numero;
        this.cliente = cliente;
        this.itens = new ArrayList<>();
        this.status = null;
        this.dataHora = null;
    }

    public void adicionarItem(ItemCardapio item, int quantidade) {
        ItemPedido novoItem = new ItemPedido(item, quantidade);
        itens.add(novoItem);
        valorTotal += novoItem.getSubtotal();
    }

    public void aceitarPedido() {
        this.status = StatusPedido.ACEITO;
        this.dataHora = LocalDateTime.now();

        notificarListeners();
    }


    public boolean avancarStatus() {
        StatusPedido antigo = status;
        switch (status) {
            case null -> { aceitarPedido();}
            case ACEITO -> status = StatusPedido.PREPARANDO;
            case PREPARANDO -> status = StatusPedido.FEITO;
            case FEITO -> status = StatusPedido.AGUARDANDO_ENTREGADOR;
            case AGUARDANDO_ENTREGADOR -> status = StatusPedido.SAIU_PARA_ENTREGA;
            case SAIU_PARA_ENTREGA -> status = StatusPedido.ENTREGUE;
            case ENTREGUE -> { return false; }
        }
        System.out.println("Status do pedido foi alterado de:" + antigo + " para " + status);

        if (status != antigo) {
            notificarListeners();
        }
        return true;
    }

    public void adicionarListener(PedidoListener listener) {
        listeners.add(listener);
    }

    private void notificarListeners() {
        for (PedidoListener listener : listeners) {
            listener.onStatusAlterado(this);
        }
    }

    public int getNumero() { return numero; }
    public Cliente getCliente() { return cliente; }
    public List<ItemPedido> getItens() { return itens; }
    public double getValorTotal() { return valorTotal; }
    public StatusPedido getStatus() { return status; }
    public LocalDateTime getDataHora() { return dataHora; }
}