package br.dev.casa24h.ticketer.projection;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.rest.core.config.Projection;

import br.dev.casa24h.ticketer.model.Venda;

@Projection(name = "withItens", types = { Venda.class })
public interface VendaProjection {

    Long getId();

    LocalDateTime getData();

    List<VendaItemProjection> getItens();

}
