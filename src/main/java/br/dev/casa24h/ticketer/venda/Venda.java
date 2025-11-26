package br.dev.casa24h.ticketer.venda;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.dev.casa24h.ticketer.compravel.Compravel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;

@Entity
public class Venda {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<VendaItem> itens = new ArrayList<>();

	@Column(name = "created_at", updatable = false)
	private LocalDateTime data;

	protected Venda() {
	}

	@PrePersist
	protected void onCreate() {
		if (this.data == null) {
			this.data = LocalDateTime.now();
		}
	}

	public void adicionar(Compravel item) {
		if (item == null) {
			return;
		}
		VendaItem vi = new VendaItem(item);
		vi.setVenda(this);
		this.itens.add(vi);
	}

	public float getPreco() {
		float total = 0f;
		for (VendaItem vi : this.itens) {
			if (vi != null) {
				total += vi.getPreco();
			}
		}
		return total;
	}

	public Long getId() {
		return id;
	}

	public List<VendaItem> getItens() {
		return itens;
	}

	public LocalDateTime getData() {
		return data;
	}

}
