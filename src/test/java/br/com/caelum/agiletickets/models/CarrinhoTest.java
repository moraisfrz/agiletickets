package br.com.caelum.agiletickets.models;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.caelum.agiletickets.models.Carrinho;
import br.com.caelum.agiletickets.models.Produto;

public class CarrinhoTest {

	@Test
	public void deveBuscarOMenorPrecoEmUmaListaDeProdutos() {
		Produto terere = new Produto("Tereré", 2.0);
		Produto soba = new Produto("Sobá", 11.0);
		
		Carrinho c = new Carrinho();
		c.adicionarProduto(terere);
		c.adicionarProduto(soba);
		
		Double menorValor = c.calculaMenorValor();
		
		assertEquals(2.0, menorValor, 0.00001);
	}

}
