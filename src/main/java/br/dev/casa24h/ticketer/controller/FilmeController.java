package br.dev.casa24h.ticketer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.dev.casa24h.ticketer.model.Filme;
import br.dev.casa24h.ticketer.repository.FilmeRepository;

@RestController
@RequestMapping("/api/filmes")
public class FilmeController {

    @Autowired
    private FilmeRepository filmeRepository;

    @PostMapping("")
    ResponseEntity<?> novoItem(@RequestBody Filme filme) {
        if (filme == null) {
            return ResponseEntity.badRequest().body("Filme cannot be null");
        }

        filmeRepository.save(filme);
        return ResponseEntity.created(java.net.URI.create("/api/repo/filmes/" + filme.getId())).body(filme);
    }

}
