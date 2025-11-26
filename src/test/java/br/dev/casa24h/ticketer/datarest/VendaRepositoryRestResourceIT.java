package br.dev.casa24h.ticketer.datarest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.MediaTypes;

import br.dev.casa24h.ticketer.model.ItemBomboniere;
import br.dev.casa24h.ticketer.model.Venda;
import br.dev.casa24h.ticketer.repository.VendaRepository;

class VendaRepositoryRestResourceIT extends RepositoryRestResourceITSupport {

    @Test
    @DisplayName("/vendas/search/findByPreco deve localizar vendas pelo total")
    void vendasSearchByPreco() throws Exception {
        Venda venda = instantiateVenda();
        venda.adicionar(new ItemBomboniere("Combo 1", 30f));
        venda = vendaRepository.save(venda);

        mockMvc.perform(get(BASE + "/vendas/search/findByPreco")
                .param("preco", "30")
                .accept(MediaTypes.HAL_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.vendas[0]._links.self.href")
                .value("http://localhost" + BASE + "/vendas/" + venda.getId()));

        mockMvc.perform(get(BASE + "/vendas/search/findByPreco")
                .param("preco", "99")
                .accept(MediaTypes.HAL_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.vendas.length()").value(0))
            .andExpect(jsonPath("$.page").doesNotExist());
    }
}
