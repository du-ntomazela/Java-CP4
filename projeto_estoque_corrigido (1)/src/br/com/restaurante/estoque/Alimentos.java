package br.com.restaurante.estoque;

public class Alimentos extends Item {
    private int validadeDias;

    public Alimentos() {}

    public Alimentos(String nome, int qtd, int capacidade, int validadeDias) {
        super(nome, qtd, capacidade);
        this.validadeDias = validadeDias;
    }

    public int getValidadeDias() { return validadeDias; }
    public void setValidadeDias(int validadeDias) { this.validadeDias = validadeDias; }

    @Override
    public String getTipo() { return "ALIMENTO"; }
}
