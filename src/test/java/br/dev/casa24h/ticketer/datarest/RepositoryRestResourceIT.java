package br.dev.casa24h.ticketer.datarest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import br.dev.casa24h.ticketer.compravel.IngressoRepository;
import br.dev.casa24h.ticketer.compravel.ItemBomboniereRepository;
import br.dev.casa24h.ticketer.exibicao.Exibicao;
import br.dev.casa24h.ticketer.exibicao.ExibicaoRepository;
import br.dev.casa24h.ticketer.filme.Filme;
import br.dev.casa24h.ticketer.filme.FilmeRepository;
import br.dev.casa24h.ticketer.sala.Sala;
import br.dev.casa24h.ticketer.sala.SalaRepository;
import br.dev.casa24h.ticketer.venda.Venda;
import br.dev.casa24h.ticketer.venda.VendaRepository;

@SpringBootTest
@AutoConfigureMockMvc
abstract class RepositoryRestResourceITSupport {

    protected static final String BASE = "/api/repo";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected FilmeRepository filmeRepository;

    @Autowired
    protected SalaRepository salaRepository;

    @Autowired
    protected ExibicaoRepository exibicaoRepository;

    @Autowired
    protected IngressoRepository ingressoRepository;

    @Autowired
    protected ItemBomboniereRepository itemBomboniereRepository;

    @Autowired
    protected VendaRepository vendaRepository;

    @AfterEach
    void cleanup() {
        ingressoRepository.deleteAll();
        vendaRepository.deleteAll();
        itemBomboniereRepository.deleteAll();
        exibicaoRepository.deleteAll();
        filmeRepository.deleteAll();
        salaRepository.deleteAll();
    }
    protected Exibicao persistExibicao(String filmeNome) {
        Filme filme = filmeRepository.save(new Filme(filmeNome, Duration.ofMinutes(120), 35f));
        Sala sala = new Sala(new ArrayList<>(List.of("A1", "A2", "A3")));
        sala.setNome("Sala Principal");
        sala = salaRepository.save(sala);
        Exibicao exibicao = new Exibicao(sala, filme, LocalDateTime.now().plusDays(1));
        return exibicaoRepository.save(exibicao);
    }

    protected Venda instantiateVenda() {
        try {
            var constructor = Venda.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Não foi possível instanciar Venda para os testes", e);
        }
    }
}
