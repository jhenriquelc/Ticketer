package br.dev.casa24h.ticketer.datarest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.MediaTypes;

import br.dev.casa24h.ticketer.model.Exibicao;
import br.dev.casa24h.ticketer.model.Ingresso;

class IngressoRepositoryRestResourceIT extends RepositoryRestResourceITSupport {

    @Test
    @DisplayName("/ingressos/search/findByExibicaoId deve trazer apenas ingressos da exibição")
    void ingressosSearchByExibicaoId() throws Exception {
        Exibicao exibicao = persistExibicao("Duna");
        ingressoRepository.save(new Ingresso(exibicao, "B7"));
        ingressoRepository.save(new Ingresso(exibicao, "B8"));

        mockMvc.perform(get(BASE + "/ingressos/search/findByExibicaoId")
                .param("exibicaoId", exibicao.getId().toString())
                .accept(MediaTypes.HAL_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.ingressos.length()").value(2))
            .andExpect(jsonPath("$._embedded.ingressos[0].poltrona").value("B7"));
    }
}
