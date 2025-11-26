package br.dev.casa24h.ticketer.datarest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.MediaTypes;

import br.dev.casa24h.ticketer.model.Exibicao;

class ExibicaoRepositoryRestResourceIT extends RepositoryRestResourceITSupport {

    @Test
    @DisplayName("/exibicoes/search/findByFilmeNomeContainingIgnoreCase deve filtrar por nome do filme")
    void exibicoesSearchByFilmName() throws Exception {
        Exibicao exibicao = persistExibicao("Matrix Resurrections");

        mockMvc.perform(get(BASE + "/exibicoes/search/findByFilmeNomeContainingIgnoreCase")
                .param("nome", "matrix")
                .accept(MediaTypes.HAL_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.exibicoes[0]._links.self.href")
                .value("http://localhost" + BASE + "/exibicoes/" + exibicao.getId()));
    }
}
