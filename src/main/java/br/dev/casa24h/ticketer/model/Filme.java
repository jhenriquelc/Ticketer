package br.dev.casa24h.ticketer.model;

import java.time.Duration;

public record Filme(String nome, Duration duracao, float precoIngresso) {

    public Filme setNome(String nome) {
        return new Filme(nome, this.duracao(), this.precoIngresso());
    }

    public Filme setDuracao(Duration duracao) {
        return new Filme(this.nome(), duracao, this.precoIngresso());
    }

    public Filme setPrecoIngresso(float precoIngresso) {
        return new Filme(this.nome(), this.duracao(), precoIngresso);
    }
    
}
