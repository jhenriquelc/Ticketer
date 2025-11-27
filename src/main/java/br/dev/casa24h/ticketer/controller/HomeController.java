package br.dev.casa24h.ticketer.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.dev.casa24h.ticketer.model.*;
import br.dev.casa24h.ticketer.repository.*;
@Controller
public class HomeController {

    @Autowired
    ItemBomboniereRepository itemBomboniereRepository;

    @Autowired
    ExibicaoRepository exibicaoRepository;

    @Autowired
    FilmeRepository filmeRepository;

    @GetMapping("/")
    public String redirecionarParaVendas() {
        return "redirect:/ingressos";
    }

    @GetMapping("/ingressos")
    public String ingressos(Model model) {
        
        Iterable<Filme> filmes = filmeRepository.findAll();
        Map<Filme, List<Exibicao>> novoFilmes = new HashMap<>();

        for (Filme filme : filmes) {
            List<Exibicao> exibicoesDoFilme = exibicaoRepository.findByFilmeId(filme.getId());
            novoFilmes.put(filme, exibicoesDoFilme);
        }

        model.addAttribute("conteudo", "ingressos");
        model.addAttribute("Filmes", novoFilmes.entrySet().iterator());
        return "index";
    }

    @GetMapping("/bomboniere")
    public String bomboniere(Model model) {
        
        model.addAttribute("conteudo", "bomboniere");
        model.addAttribute("itensBomboniere", itemBomboniereRepository.findAll());
        return "index";
    }

    @GetMapping("/filmes")
    public String filmes(Model model) {
        model.addAttribute("conteudo", "filmes");
        return "index";
    }

    @GetMapping("/sessoes")
    public String sessoes(Model model) {
        model.addAttribute("conteudo", "sessoes");
        return "index";
    }

    @GetMapping("/gerenciamento-bomboniere")
    public String gerenciamentoBomboniere(Model model) {
        model.addAttribute("conteudo", "gerenciamento-bomboniere");
        return "index";
    }
}