package model;

public class ItemPedido {
    private final ItemCardapio item;
    private final int quantidade;

    public ItemPedido(ItemCardapio item, int quantidade) {
        this.item = item;
        this.quantidade = quantidade;
    }

    public double getSubtotal() {
        return item.getPreco() * quantidade;
    }

    public ItemCardapio getItem() { return item; }
    public int getQuantidade() { return quantidade; }
}