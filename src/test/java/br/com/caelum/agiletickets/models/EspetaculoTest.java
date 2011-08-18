package br.com.caelum.agiletickets.models;

import static org.junit.Assert.*;

import java.util.List;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Test;

public class EspetaculoTest {

	@Test
	public void deveCriarApenasUmaSessaoDiaria() {
		
		Espetaculo e = new Espetaculo();
		LocalDate inicio = new LocalDate(2010, 8, 18);
		LocalDate fim = new LocalDate(2010, 8, 18);
		LocalTime horario = new LocalTime(19, 0); 

		e.criaSessoes(inicio, fim, horario, Periodicidade.DIARIA);
		
		assertEquals(1, e.getSessoes().size());
		assertEquals(inicio.toDateTime(horario), e.getSessoes().get(0).getInicio());
	}

	@Test
	public void deveCriarDuasSessoesDiarias() {
		
		Espetaculo e = new Espetaculo();
		LocalDate inicio = new LocalDate(2010, 8, 18);
		LocalDate fim = new LocalDate(2010, 8, 19);
	
		LocalTime horario = new LocalTime(19, 0); 

		e.criaSessoes(inicio, fim, horario, Periodicidade.DIARIA);
		
		assertEquals(2, e.getSessoes().size());
		assertEquals(inicio.toDateTime(horario), e.getSessoes().get(0).getInicio());
		assertEquals(fim.toDateTime(horario), e.getSessoes().get(e.getSessoes().size() - 1).getInicio());
	}

	@Test
	public void deveCriarApenasUmaSessaoSemanal() {
		
		Espetaculo e = new Espetaculo();
		LocalDate inicio = new LocalDate(2010, 8, 18);
		LocalDate fim = new LocalDate(2010, 8, 24);
		LocalTime horario = new LocalTime(19, 0); 

		e.criaSessoes(inicio, fim, horario, Periodicidade.SEMANAL);
		
		assertEquals(1, e.getSessoes().size());
		assertEquals(inicio.toDateTime(horario), e.getSessoes().get(0).getInicio());
	}

	@Test
	public void deveCriarDuasSessoesSemanais() {
		
		Espetaculo e = new Espetaculo();
		LocalDate inicio = new LocalDate(2010, 8, 18);
		LocalDate fim = new LocalDate(2010, 8, 28);
	
		LocalTime horario = new LocalTime(19, 0); 

		e.criaSessoes(inicio, fim, horario, Periodicidade.SEMANAL);
		
		assertEquals(2, e.getSessoes().size());
		assertEquals(inicio.toDateTime(horario), e.getSessoes().get(0).getInicio());
	}
	
	
}
