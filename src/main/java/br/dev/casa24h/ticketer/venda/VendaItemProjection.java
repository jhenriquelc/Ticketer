package br.dev.casa24h.ticketer.venda;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "itemSummary", types = { VendaItem.class })
public interface VendaItemProjection {

    String getDescricao();

    float getPreco();

}
