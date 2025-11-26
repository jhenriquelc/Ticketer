package br.dev.casa24h.ticketer.exibicao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import br.dev.casa24h.ticketer.sala.Sala;

@WebMvcTest(ExibicaoController.class)
class ExibicaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ExibicaoRepository repository;

    @Test
    @DisplayName("Deve retornar 404 quando a exibição não for encontrada")
    void ocuparAssentoRetorna404QuandoExibicaoNaoExiste() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/exibicoes/{id}/ocupar/{assento}", 1L, "A1"))
            .andExpect(status().isNotFound());

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve retornar 400 para assento inexistente na sala")
    void ocuparAssentoRetorna400QuandoAssentoNaoPertenceSala() throws Exception {
        Exibicao exibicao = buildExibicao(List.of("B1", "B2"));
        when(repository.findById(1L)).thenReturn(Optional.of(exibicao));

        mockMvc.perform(post("/api/exibicoes/{id}/ocupar/{assento}", 1L, "A1"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Assento inválido para esta sala"));

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve retornar 409 quando o assento já estiver ocupado")
    void ocuparAssentoRetorna409QuandoAssentoJaOcupado() throws Exception {
        Exibicao exibicao = buildExibicao(List.of("A1", "A2"));
        exibicao.ocuparAssento("A1");
        when(repository.findById(5L)).thenReturn(Optional.of(exibicao));

        mockMvc.perform(post("/api/exibicoes/{id}/ocupar/{assento}", 5L, "A1"))
            .andExpect(status().isConflict())
            .andExpect(content().string("Assento já ocupado"));

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve reservar o assento disponível e retornar 200")
    void ocuparAssentoRetorna200QuandoReservaRealizada() throws Exception {
        Exibicao exibicao = buildExibicao(List.of("A1", "A2"));
        when(repository.findById(9L)).thenReturn(Optional.of(exibicao));
        when(repository.save(any(Exibicao.class))).thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(post("/api/exibicoes/{id}/ocupar/{assento}", 9L, "A1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.assentosOcupados", Matchers.hasItem("A1")));

        ArgumentCaptor<Exibicao> captor = ArgumentCaptor.forClass(Exibicao.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getAssentosOcupados()).contains("A1");
    }

    private Exibicao buildExibicao(List<String> assentosSala) {
        Sala sala = new Sala(assentosSala);
        return new Exibicao(sala, null, LocalDateTime.now());
    }
}
