package br.dev.casa24h.ticketer.model;

public class Ingresso extends Compravel {

    private Exibicao exibicao;

    private String poltrona;

    public String getDescricao() {
        // TODO
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
