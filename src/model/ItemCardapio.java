package model;

public class ItemCardapio {
    private final int id;
    private final String nome;
    private final double preco;

    public ItemCardapio(int id, String nome, double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public double getPreco() { return preco; }
}