package br.dev.casa24h.ticketer.filme;

import java.time.Duration;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Convert;

@Entity
public class Filme {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String nome;

    @Convert(converter = DurationAttributeConverter.class)
    private Duration duracao;

    private float precoIngresso;

    protected Filme() {
    }

    public Filme(String nome, Duration duracao, float precoIngresso) {
        this.nome = nome;
        this.duracao = duracao;
        this.precoIngresso = precoIngresso;
    }

    public String getNome() {
        return nome;
    }

    public Duration getDuracao() {
        return duracao;
    }

    public float getPrecoIngresso() {
        return precoIngresso;
    }

    public Long getId() {
        return id;
    }
    
}
