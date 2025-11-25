package br.dev.casa24h.ticketer.model;

import java.util.ArrayList;

public record Venda(Long id, ArrayList<Compravel> itens) {
	
	public Venda adicionar(Compravel item) {
		ArrayList<Compravel> nova = new ArrayList<>(this.itens);
		nova.add(item);
		return new Venda(this.id, nova);
	}

	public float calcularPreco() {
		float total = 0;
		for (Compravel c : itens) total += c.getPreco();
		return total;
	}

}
