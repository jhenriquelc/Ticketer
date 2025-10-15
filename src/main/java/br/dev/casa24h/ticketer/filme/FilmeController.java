package br.dev.casa24h.ticketer.filme;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilmeController {
    
    @GetMapping("/filmes")
    public List<Filme> listarFilmes() {
        //TODO: Implementar a lógica para listar filmes
        return new ArrayList<>();
    }
}
