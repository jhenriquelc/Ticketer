package br.dev.casa24h.ticketer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.dev.casa24h.ticketer.model.Exibicao;
import br.dev.casa24h.ticketer.model.ItemBomboniere;
import br.dev.casa24h.ticketer.repository.ItemBomboniereRepository;
import br.dev.casa24h.ticketer.repository.FilmeRepository;

@Controller
public class HomeController {

    @Autowired
    ItemBomboniereRepository itemBomboniereRepository;

    @Autowired
    FilmeRepository filmeRepository;

    @GetMapping("/")
    public String redirecionarParaVendas() {
        return "redirect:/ingressos";
    }

    @GetMapping("/ingressos")
    public String ingressos(Model model) {
        model.addAttribute("conteudo", "ingressos");
        return "index";
    }

    @GetMapping("/bomboniere")
    public String bomboniere(Model model) {
        model.addAttribute("conteudo", "bomboniere");
        model.addAttribute("itens", itemBomboniereRepository.findAll());
        return "index";
    }

    @GetMapping("/filmes")
    public String filmes(Model model) {
        model.addAttribute("conteudo", "filmes");
        return "index";
    }

    @GetMapping("/exibicoes")
    public String exibicoes(Model model) {
        model.addAttribute("conteudo", "exibicoes");
        model.addAttribute("filmes", filmeRepository.findAll());
        model.addAttribute("exibicao", new Exibicao(null, null, null));
        return "index";
    }

    @GetMapping("/gerenciamento-bomboniere")
    public String gerenciamentoBomboniere(Model model) {
        model.addAttribute("conteudo", "gerenciamento-bomboniere");
        model.addAttribute("itemBomboniere", new ItemBomboniere("", 0f));
        return "index";
    }
}