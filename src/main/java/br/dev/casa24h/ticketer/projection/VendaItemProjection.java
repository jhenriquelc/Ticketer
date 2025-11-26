package br.dev.casa24h.ticketer.projection;

import org.springframework.data.rest.core.config.Projection;

import br.dev.casa24h.ticketer.model.VendaItem;

@Projection(name = "itemSummary", types = { VendaItem.class })
public interface VendaItemProjection {

    String getDescricao();

    float getPreco();

}
