package br.dev.casa24h.ticketer.venda;

import br.dev.casa24h.ticketer.compravel.Compravel;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class VendaItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String descricao;

    private float preco;

    @ManyToOne
    @JoinColumn(name = "venda_id")
    private Venda venda;

    protected VendaItem() {
    }

    public VendaItem(Compravel c) {
        if (c == null) {
            return;
        }
        this.descricao = c.getDescricao();
        this.preco = c.getPreco();
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public float getPreco() {
        return preco;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

}
