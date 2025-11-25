package br.dev.casa24h.ticketer.model;

import java.util.ArrayList;

public record Sala(Long id, ArrayList<String> assentos) {

    public ArrayList<String> getAssentos() {
        return assentos;
    }

    public Sala setAssentos(ArrayList<String> assentos) {
        return new Sala(this.id, assentos);
    }
    
}
