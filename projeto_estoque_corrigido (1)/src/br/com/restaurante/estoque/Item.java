package br.com.restaurante.estoque;

public abstract class Item {
    private Integer id;
    private String nome;
    private int qtd;
    private int capacidade;

    public Item() {}

    public Item(String nome, int qtd, int capacidade) {
        this.nome = nome;
        this.qtd = qtd;
        this.capacidade = capacidade;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getQtd() { return qtd; }
    public void setQtd(int qtd) { this.qtd = qtd; }

    public int getCapacidade() { return capacidade; }
    public void setCapacidade(int capacidade) { this.capacidade = capacidade; }

    public abstract String getTipo();
}
