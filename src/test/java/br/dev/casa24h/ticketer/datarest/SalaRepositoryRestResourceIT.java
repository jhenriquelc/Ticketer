package br.dev.casa24h.ticketer.datarest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.MediaTypes;

import br.dev.casa24h.ticketer.sala.Sala;

class SalaRepositoryRestResourceIT extends RepositoryRestResourceITSupport {

    @Test
    @DisplayName("/salas deve retornar assentos e nome da sala")
    void salasEndpointListsSeats() throws Exception {
        Sala sala = new Sala(new ArrayList<>(List.of("A1", "A2")));
        sala.setNome("Sala Azul");
        salaRepository.save(sala);

        mockMvc.perform(get(BASE + "/salas").accept(MediaTypes.HAL_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.salas[0].nome").value("Sala Azul"))
            .andExpect(jsonPath("$._embedded.salas[0].assentos[0]").value("A1"));
    }
}
