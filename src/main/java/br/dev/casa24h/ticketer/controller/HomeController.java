package br.dev.casa24h.ticketer.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.dev.casa24h.ticketer.model.*;
import br.dev.casa24h.ticketer.repository.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

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

        logger.info(novoFilmes.toString());
        logger.info("Total de filmes encontrados: " + novoFilmes.size());
        // logger.info(novoFilmes);
        logger.info(filmes.toString());
        model.addAttribute("conteudo", "ingressos");
        // model.addAttribute("Filmes", filmeRepository.findAll());
        model.addAttribute("Filmes", novoFilmes);
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