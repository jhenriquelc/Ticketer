package br.dev.casa24h.ticketer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Ingresso extends Compravel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "exibicao_id")
    private Exibicao exibicao;

    private String poltrona;

    protected Ingresso() {
    }

    public Ingresso(Exibicao exibicao, String poltrona) {
        this.exibicao = exibicao;
        this.poltrona = poltrona;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getDescricao() {
        String salaStr = (this.exibicao != null && this.exibicao.getSala() != null) ? this.exibicao.getSala().toString() : "";
        String inicio = (this.exibicao != null && this.exibicao.getInicio() != null) ? this.exibicao.getInicio().toString() : "";
        String fim = (this.exibicao != null) ? String.valueOf(this.exibicao.getFim()) : "";
        String filmeNome = (this.exibicao != null && this.exibicao.getFilme() != null) ? this.exibicao.getFilme().getNome() : "";
        return salaStr + " - " + (this.poltrona != null ? this.poltrona : "") + "\n" + inicio + " - " + fim + "\n" + filmeNome;

    }

    @Override
    public float getPreco() {
        if (this.exibicao == null || this.exibicao.getFilme() == null) {
            return 0f;
        }
        return this.exibicao.getFilme().getPrecoIngresso();
    }

    public Exibicao getExibicao() {
        return exibicao;
    }

    public String getPoltrona() {
        return poltrona;
    }

}
