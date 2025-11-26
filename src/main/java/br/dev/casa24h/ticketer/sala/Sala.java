package br.dev.casa24h.ticketer.sala;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.JoinColumn;

@Entity
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "sala_assentos", joinColumns = @JoinColumn(name = "sala_id"))
    private List<String> assentos = new ArrayList<>();

    private String nome;

    protected Sala() {
    }

    public Sala(List<String> assentos) {
        this.assentos = assentos;
    }

    public Long getId() {
        return id;
    }

    public List<String> getAssentos() {
        return assentos;
    }

    public void setAssentos(List<String> assentos) {
        this.assentos = assentos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
}
