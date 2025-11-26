package br.dev.casa24h.ticketer.repository;

import java.util.List;
import java.time.LocalDateTime;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.dev.casa24h.ticketer.model.Exibicao;

@RepositoryRestResource(collectionResourceRel = "exibicoes", path = "exibicoes")
public interface ExibicaoRepository extends PagingAndSortingRepository<Exibicao, Long>, CrudRepository<Exibicao, Long> {

    List<Exibicao> findByFilmeId(@Param("filmeId") Long filmeId);

    List<Exibicao> findByInicioBefore(@Param("inicio") LocalDateTime inicio);

    List<Exibicao> findByInicioAfter(@Param("inicio") LocalDateTime inicio);

    List<Exibicao> findByFilmeNome(@Param("nome") String nome);

    List<Exibicao> findByFilmeNomeContainingIgnoreCase(@Param("nome") String nome);

    // pesquisas compostas
    
    List<Exibicao> findByFilmeNomeAndInicioBefore(@Param("nome") String nome, @Param("inicio") LocalDateTime inicio);

    List<Exibicao> findByFilmeNomeAndInicioAfter(@Param("nome") String nome, @Param("inicio") LocalDateTime inicio);

    List<Exibicao> findByFilmeNomeContainingIgnoreCaseAndInicioBefore(@Param("nome") String nome, @Param("inicio") LocalDateTime inicio);

    List<Exibicao> findByFilmeNomeContainingIgnoreCaseAndInicioAfter(@Param("nome") String nome, @Param("inicio") LocalDateTime inicio);

}
