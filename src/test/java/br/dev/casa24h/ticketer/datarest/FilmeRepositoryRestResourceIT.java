package br.dev.casa24h.ticketer.datarest;

import br.dev.casa24h.ticketer.model.Filme;
import br.dev.casa24h.ticketer.repository.FilmeRepository;

import java.time.Duration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.MediaTypes;


class FilmeRepositoryRestResourceIT extends RepositoryRestResourceITSupport {

    @Test
    @DisplayName("/filmes deve expor os filmes persistidos")
    void filmesEndpointListsEntities() throws Exception {
        filmeRepository.save(new Filme("Interestelar", Duration.ofMinutes(169), 45f));

        mockMvc.perform(get(BASE + "/filmes").accept(MediaTypes.HAL_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.filmes[0].nome").value("Interestelar"));
    }
}
