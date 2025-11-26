package br.dev.casa24h.ticketer.exibicao;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import br.dev.casa24h.ticketer.filme.Filme;
import br.dev.casa24h.ticketer.sala.Sala;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Exibicao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sala_id")
    private Sala sala;

    @ManyToOne
    @JoinColumn(name = "filme_id")
    private Filme filme;

    @ElementCollection
    @CollectionTable(name = "exibicao_assentos", joinColumns = @JoinColumn(name = "exibicao_id"))
    @Column(name = "assento")
    private Set<String> assentosOcupados = new HashSet<>();

    private LocalDateTime inicio;

    protected Exibicao() {
    }

    public Exibicao(Sala sala, Filme filme, LocalDateTime inicio) {
        this.sala = sala;
        this.filme = filme;
        this.inicio = inicio;
        this.assentosOcupados = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public Set<String> getAssentosOcupados() {
        return assentosOcupados;
    }

    public void setAssentosOcupados(Set<String> assentosOcupados) {
        this.assentosOcupados = assentosOcupados;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public boolean verificarAssento(String assento) {
        if (this.assentosOcupados == null) {
            return false;
        }
        return this.assentosOcupados.contains(assento);
    }

    public void ocuparAssento(String assento) {
        if (this.assentosOcupados == null) {
            this.assentosOcupados = new HashSet<>();
        }
        this.assentosOcupados.add(assento);
    }

    public LocalDateTime getFim() {
        if (this.inicio == null || this.filme == null) {
            return null;
        }
        return this.inicio.plus(this.filme.getDuracao());
    }

}
