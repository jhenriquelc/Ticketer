package br.dev.casa24h.ticketer.compravel;

import br.dev.casa24h.ticketer.exibicao.Exibicao;

public class Ingresso extends Compravel {

    private Exibicao exibicao;

    private String poltrona;

    public String getDescricao() {
        return this.exibicao.getSala().toString() + " - " + this.poltrona.toString() + "\n"+
        this.exibicao.getInicio() + " - " + this.exibicao.getFim() + "\n" +
        this.exibicao.getFilme().getNome();

    }

    public float getPreco() {
        this.exibicao.getFilme().getPreco();
    }

    public Exibicao getExibicao() {
        return exibicao;
    }

    public void setExibicao(Exibicao exibicao) {
        this.exibicao = exibicao;
    }

    public String getPoltrona() {
        return poltrona;
    }

    public void setPoltrona(String poltrona) {
        this.poltrona = poltrona;
    }

}
