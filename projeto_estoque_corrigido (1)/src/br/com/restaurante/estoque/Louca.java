package br.com.restaurante.estoque;

public class Louca extends Item {
    private boolean limpo;
    private String material;

    public Louca() {}

    public Louca(String nome, int qtd, int capacidade, boolean limpo, String material) {
        super(nome, qtd, capacidade);
        this.limpo = limpo;
        this.material = material;
    }

    public boolean isLimpo() { return limpo; }
    public void setLimpo(boolean limpo) { this.limpo = limpo; }

    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }

    @Override
    public String getTipo() { return "LOUCA"; }
}
