package br.dev.casa24h.ticketer.compravel;

import br.dev.casa24h.ticketer.exibicao.Exibicao;

public record Ingresso(Long id, Exibicao exibicao, String poltrona) implements Compravel {

    public String getDescricao() {
        return this.exibicao.sala().toString() + " - " + this.poltrona.toString() + "\n"+
        this.exibicao.inicio() + " - " + this.exibicao.getFim() + "\n" +
        this.exibicao.filme().nome();

    }

    public float getPreco() {
        return this.exibicao.filme().precoIngresso();
    }

    public Ingresso setExibicao(Exibicao exibicao) {
        return new Ingresso(this.id, exibicao, this.poltrona);
    }

    public Ingresso setPoltrona(String poltrona) {
        return new Ingresso(this.id, this.exibicao, poltrona);
    }

}
