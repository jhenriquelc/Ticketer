package br.dev.casa24h.ticketer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.dev.casa24h.ticketer.model.ItemBomboniere;
import br.dev.casa24h.ticketer.model.Venda;
import br.dev.casa24h.ticketer.repository.ItemBomboniereRepository;
import br.dev.casa24h.ticketer.repository.VendaRepository;

@RestController
@RequestMapping("/api/checkout")
public class VendaController {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ItemBomboniereRepository itemRepo;

    public static class CartItem {
        public String id;        // optional: id of an existing ItemBomboniere (kept as String to accept non-numeric keys)
        public String name;
        public Double price;
        public Integer quantity;

        // Getters
        public String getId() { return id; }
        public String getName() { return name; }
        public Double getPrice() { return price; }
        public Integer getQuantity() { return quantity; }

        // Setters
        public void setId(String id) { this.id = id; }
        public void setName(String name) { this.name = name; }
        public void setPrice(Double price) { this.price = price; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
    }

    public static class CheckoutRequest {
        public List<CartItem> items;

        // Getters and Setters
        public List<CartItem> getItems() { return items; }
        public void setItems(List<CartItem> items) { this.items = items; }
    }

    public static class CheckoutResponse {
        public Long id;
        public Float preco;
        public String data;

        public CheckoutResponse(Long id, Float preco, String data) {
            this.id = id;
            this.preco = preco;
            this.data = data;
        }

        // Getters
        public Long getId() { return id; }
        public Float getPreco() { return preco; }
        public String getData() { return data; }
    }

    @PostMapping("")
    public ResponseEntity<?> checkout(@RequestBody CheckoutRequest req) {
        // Validação básica
        if (req == null || req.getItems() == null || req.getItems().isEmpty()) {
            return ResponseEntity.badRequest().body("Cart is empty");
        }

        // Validar itens
        for (CartItem ci : req.getItems()) {
            if (ci.getName() == null || ci.getName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Item name is required");
            }
            if (ci.getPrice() == null || ci.getPrice() < 0) {
                return ResponseEntity.badRequest().body("Item price is invalid");
            }
            if (ci.getQuantity() == null || ci.getQuantity() < 1) {
                return ResponseEntity.badRequest().body("Item quantity must be at least 1");
            }
        }

        Venda venda = Venda.criar();
        for (CartItem ci : req.getItems()) {
            int qty = ci.getQuantity();
            if (ci.getId() != null && !ci.getId().trim().isEmpty()) {
                // try to interpret id as a numeric DB id; if not numeric, fallback to creating a temporary item
                try {
                    Long parsedId = Long.valueOf(ci.getId());
                    // try to load an existing ItemBomboniere from DB, fallback to a lightweight instance
                    itemRepo.findById(parsedId).ifPresentOrElse(it -> {
                        for (int i = 0; i < qty; i++) venda.adicionar(it);
                    }, () -> {
                        ItemBomboniere temp = new ItemBomboniere(ci.getName(), ci.getPrice().floatValue());
                        for (int i = 0; i < qty; i++) venda.adicionar(temp);
                    });
                } catch (NumberFormatException nfe) {
                    // id is not a numeric DB id, create a lightweight instance using provided name/price
                    ItemBomboniere temp = new ItemBomboniere(ci.getName(), ci.getPrice().floatValue());
                    for (int i = 0; i < qty; i++) venda.adicionar(temp);
                }
            } else {
                ItemBomboniere temp = new ItemBomboniere(ci.getName(), ci.getPrice().floatValue());
                for (int i = 0; i < qty; i++) venda.adicionar(temp);
            }
        }

        Venda saved = vendaRepository.save(venda);
        // return a simple response DTO instead of the full entity
        CheckoutResponse response = new CheckoutResponse(
            saved.getId(),
            saved.getPreco(),
            saved.getData() != null ? saved.getData().toString() : null
        );
        return ResponseEntity.ok().body(response);
    }
}
