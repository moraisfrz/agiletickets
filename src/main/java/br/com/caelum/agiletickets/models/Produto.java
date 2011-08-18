package br.com.caelum.agiletickets.models;

public class Produto implements Comparable<Produto> {

	private String nome;
	private Double valor;

	public Produto(String nome, Double valor) {
		super();
		this.nome = nome;
		this.valor = valor;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}

	@Override
	public int compareTo(Produto other) {
		if (this.getValor() < other.getValor()) {
			return -1;
		} else if (this.getValor() > other.getValor()) {
			return 1;
		} else {
			return 0;
		}
	}
	
}
