package br.dev.casa24h.ticketer.repository;

import br.dev.casa24h.ticketer.model.Venda;
import br.dev.casa24h.ticketer.projection.VendaProjection;

import java.util.List;
import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "vendas", path = "vendas", excerptProjection = VendaProjection.class)
public interface VendaRepository extends PagingAndSortingRepository<Venda, Long>, CrudRepository<Venda, Long> {

    @Query("select v from Venda v join v.itens i group by v having abs(sum(i.preco) - :preco) < 0.001")
    List<Venda> findByPreco(@Param("preco") Double preco);

    List<Venda> findByDataBetween(LocalDateTime startInclusive, LocalDateTime endInclusive);

    default List<Venda> findByData(LocalDateTime data) {
        if (data == null) {
            return List.of();
        }
        LocalDateTime start = data.withSecond(0).withNano(0);
        LocalDateTime end = data.withSecond(59).withNano(999_999_999);
        return findByDataBetween(start, end);
    }
}
