package br.dev.casa24h.ticketer.compravel;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class ItemBomboniere extends Compravel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    private float preco;

    protected ItemBomboniere() {
    }

    public ItemBomboniere(String nome, float preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getDescricao() {
        return this.nome;
    }

    @Override
    public float getPreco() {
        return this.preco;
    }

    public String getNome() {
        return nome;
    }

    public float getPrecoValor() {
        return preco;
    }

}
