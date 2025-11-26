package br.dev.casa24h.ticketer.datarest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;

class BomboniereRepositoryRestResourceIT extends RepositoryRestResourceITSupport {

    @Test
    @DisplayName("/bomboniere deve permitir criar e pesquisar itens")
    void bomboniereSupportsCreateAndSearch() throws Exception {
        var creationResult = mockMvc.perform(post(BASE + "/bomboniere")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"nome\":\"Pipoca Doce\",\"preco\":12.5}"))
            .andExpect(status().isCreated())
            .andReturn();

        Long createdId = itemBomboniereRepository.findAll().iterator().next().getId();
        assertThat(creationResult.getResponse().getHeader("Location"))
            .isEqualTo("http://localhost" + BASE + "/bomboniere/" + createdId);

        mockMvc.perform(get(BASE + "/bomboniere/search/findByNomeContainingIgnoreCase")
                .param("nome", "pip")
                .accept(MediaTypes.HAL_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.bomboniere[0].nome").value("Pipoca Doce"));
    }
}
