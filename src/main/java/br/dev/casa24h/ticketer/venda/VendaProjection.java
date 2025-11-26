package br.dev.casa24h.ticketer.venda;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "withItens", types = { Venda.class })
public interface VendaProjection {

    Long getId();

    LocalDateTime getData();

    List<VendaItemProjection> getItens();

}
