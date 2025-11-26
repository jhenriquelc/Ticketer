package br.dev.casa24h.ticketer.repository;

import br.dev.casa24h.ticketer.model.ItemBomboniere;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "bomboniere", path = "bomboniere")
public interface ItemBomboniereRepository extends PagingAndSortingRepository<ItemBomboniere, Long>, CrudRepository<ItemBomboniere, Long> {

    List<ItemBomboniere> findByNomeContainingIgnoreCase(String nome);

}
