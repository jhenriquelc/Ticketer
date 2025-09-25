package br.dev.casa24h.ticketer.model;

import java.util.HashMap;
import java.time.LocalDateTime;

public class Exibicao {

	private Sala sala;
	private Filme filme;
	private HashMap<String, Boolean> assentosOcupados;
	private LocalDateTime inicio;
        
	public boolean verificarAssento(String assento) {
            return assentosOcupados.getOrDefault(assento, Boolean.FALSE);
	}

	public void ocuparAssento(String assento) {
                
	}

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public HashMap<String, Boolean> getAssentosOcupados() {
        return assentosOcupados;
    }

    public void setAssentosOcupados(HashMap<String, Boolean> assentosOcupados) {
        this.assentosOcupados = assentosOcupados;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

}
