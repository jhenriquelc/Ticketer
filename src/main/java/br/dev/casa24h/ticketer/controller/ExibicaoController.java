package br.dev.casa24h.ticketer.controller;

import br.dev.casa24h.ticketer.model.Exibicao;
import br.dev.casa24h.ticketer.repository.ExibicaoRepository;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exibicoes")
public class ExibicaoController {

    private final ExibicaoRepository repository;

    public ExibicaoController(ExibicaoRepository repository) {
        this.repository = repository;
    }

    @PostMapping(path = "/{id}/ocupar/{assento}")
    public ResponseEntity<?> ocuparAssento(@PathVariable("id") @NonNull Long id, @PathVariable("assento") String assento) {
        Optional<Exibicao> opt = repository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Exibicao exibicao = opt.get();

        // assento existe na sala
        if (exibicao.getSala() == null || exibicao.getSala().getAssentos() == null || !exibicao.getSala().getAssentos().contains(assento)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Assento inválido para esta sala");
        }

        // assento já ocupado
        if (exibicao.verificarAssento(assento)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Assento já ocupado");
        }

        exibicao.ocuparAssento(assento);
        Exibicao saved = repository.save(exibicao);

        return ResponseEntity.ok(saved);
    }

}
