package br.dev.casa24h.ticketer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

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

    @GetMapping("/carrinho")
    public String carrinho(Model model) {
        model.addAttribute("conteudo", "carrinho");
        return "index";
    }
}