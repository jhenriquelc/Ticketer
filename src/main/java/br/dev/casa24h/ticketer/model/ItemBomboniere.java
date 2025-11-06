package br.dev.casa24h.ticketer.model;

public class ItemBomboniere extends Compravel {

    private String nome;

    private float preco;

    public String getDescricao() {
        return this.nome;
    }

    public float getPreco() {
        return this.preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
        
        

}
