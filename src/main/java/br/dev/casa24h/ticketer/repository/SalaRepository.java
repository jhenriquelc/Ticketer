package br.dev.casa24h.ticketer.repository;

import br.dev.casa24h.ticketer.model.Sala;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "salas", path = "salas")
public interface SalaRepository extends PagingAndSortingRepository<Sala, Long>, CrudRepository<Sala, Long> {

}
