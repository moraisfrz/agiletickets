package br.com.caelum.agiletickets.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Carrinho {

	private List<Produto> produtos = new ArrayList<Produto>();
	
	public Carrinho() {
	}
	
	public void adicionarProduto(Produto p) {
		produtos.add(p);
	}

	public Double calculaMenorValor() {
		return Collections.min(produtos).getValor();
	}

}
