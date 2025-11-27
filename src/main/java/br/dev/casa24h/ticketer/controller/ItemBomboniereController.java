package br.dev.casa24h.ticketer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import br.dev.casa24h.ticketer.model.ItemBomboniere;
import br.dev.casa24h.ticketer.repository.ItemBomboniereRepository;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/bomboniere")
public class ItemBomboniereController {

    @Autowired
    ItemBomboniereRepository itemBomboniereRepository;
    
    @PostMapping("")
    ResponseEntity<?> novoItem(@ModelAttribute ItemBomboniere itemBomboniere, Model model) {
        if (itemBomboniere == null) {
            return ResponseEntity.badRequest().body("ItemBomboniere cannot be null");
        }

        itemBomboniereRepository.save(itemBomboniere);
        System.out.println(itemBomboniere);
        return ResponseEntity.status(302).header("Location", "/bomboniere").build();
    }

}
