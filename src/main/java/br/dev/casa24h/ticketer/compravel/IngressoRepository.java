package br.dev.casa24h.ticketer.compravel;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "ingressos", path = "ingressos")
public interface IngressoRepository extends PagingAndSortingRepository<Ingresso, Long>, CrudRepository<Ingresso, Long> {

    List<Ingresso> findByExibicaoId(Long exibicaoId);

}
