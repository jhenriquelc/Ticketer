package br.dev.casa24h.ticketer.model;

public record ItemBomboniere(Long id, String nome, float preco) implements Compravel {

    public String getDescricao() {
        return this.nome;
    }

    public float getPreco() {
        return this.preco;
    }

    public String getNome() {
        return nome;
    }

    public ItemBomboniere setNome(String nome) {
        return new ItemBomboniere(id, nome, this.preco);
    }

}
