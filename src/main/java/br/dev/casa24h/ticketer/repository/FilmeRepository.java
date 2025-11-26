package br.dev.casa24h.ticketer.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.dev.casa24h.ticketer.model.Filme;

@RepositoryRestResource(collectionResourceRel = "filmes", path = "filmes")
public interface FilmeRepository extends PagingAndSortingRepository<Filme, Long>, CrudRepository<Filme, Long> {
    
    List<Filme> findByNome(@Param("nome") String nome);

    List<Filme> findByNomeContainingIgnoreCase(@Param("nome") String nome);
}
