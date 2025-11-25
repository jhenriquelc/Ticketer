package br.dev.casa24h.ticketer.model;

import java.util.HashMap;
import java.time.LocalDateTime;

public record Exibicao(Sala sala, Filme filme, HashMap<String, Boolean> assentosOcupados, LocalDateTime inicio) {
        
	public boolean verificarAssento(String assento) {
        return assentosOcupados.getOrDefault(assento, Boolean.FALSE);
	}

	public void ocuparAssento(String assento) {
        this.assentosOcupados.put(assento, Boolean.TRUE);
	}


    public Exibicao setSala(Sala sala) {
        return new Exibicao(sala, this.filme(), this.assentosOcupados(), this.inicio());
    }

    public Exibicao setFilme(Filme filme) {
        return new Exibicao(this.sala(), filme, this.assentosOcupados(), this.inicio());
    }

    public Exibicao setAssentosOcupados(HashMap<String, Boolean> assentosOcupados) {
        return new Exibicao(this.sala(), this.filme(), assentosOcupados, this.inicio());
    }

    public Exibicao setInicio(LocalDateTime inicio) {
        return new Exibicao(this.sala(), this.filme(), this.assentosOcupados(), inicio);
    }

    public LocalDateTime getFim() {
        return this.inicio.plus(filme.duracao());
    }

}
