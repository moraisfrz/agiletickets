package br.com.caelum.agiletickets.controllers;

import static br.com.caelum.vraptor.view.Results.status;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import br.com.caelum.agiletickets.domain.Agenda;
import br.com.caelum.agiletickets.domain.DiretorioDeEstabelecimentos;
import br.com.caelum.agiletickets.models.Espetaculo;
import br.com.caelum.agiletickets.models.Periodicidade;
import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;

import com.google.common.base.Strings;

@Resource
public class EspetaculosController {

	private NumberFormat CURRENCY = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

	private final Agenda agenda;
	private Validator validator;
	private Result result;
	private Sessao sessao;
	private Espetaculo espetaculo;
	private final DiretorioDeEstabelecimentos estabelecimentos;
	
	public EspetaculosController(Agenda agenda, DiretorioDeEstabelecimentos estabelecimentos, Validator validator, Result result) {
		this.agenda = agenda;
		this.estabelecimentos = estabelecimentos;
		this.validator = validator;
		this.result = result;
	}

	@Get @Path("/espetaculos")
	public List<Espetaculo> lista() {
		result.include("estabelecimentos", estabelecimentos.todos());
		return agenda.espetaculos();
	}

	@Post @Path("/espetaculos")
	public void adiciona(Espetaculo espetaculo) {
		if (Strings.isNullOrEmpty(espetaculo.getNome())) {
			validator.add(new ValidationMessage("Nome do espet�culo n�o pode estar em branco", ""));
		}
		if (Strings.isNullOrEmpty(espetaculo.getDescricao())) {
			validator.add(new ValidationMessage("Descri��o do espet�culo n�o pode estar em branco", ""));
		}
		validator.onErrorRedirectTo(this).lista();

		agenda.cadastra(espetaculo);
		result.redirectTo(this).lista();
	}


	private boolean sessaoENula (Long id){
		sessao = agenda.sessao(id);
		return (sessao == null);
	}
	
	@Get @Path("/sessao/{id}")
	public void sessao(Long id) {
		if (sessaoENula(id)) {
			result.notFound();
		}
		result.include("sessao", sessao);
	}

	@Post @Path("/sessao/{sessaoId}/reserva")
	public void reserva(Long sessaoId, final Integer quantidade) {
		if (sessaoENula(sessaoId)) {
			result.notFound();
			return;
		}

		if (quantidade < 1) {
			validator.add(new ValidationMessage("Voc� deve escolher um lugar ou mais", ""));
		}

		if (!sessao.podeReservar(quantidade)) {
			validator.add(new ValidationMessage("N�o existem ingressos dispon�veis", ""));
		}

		validator.onErrorRedirectTo(this).sessao(sessao.getId());

		sessao.reserva(quantidade);

		BigDecimal precoTotal = sessao.getPreco().multiply(BigDecimal.valueOf(quantidade));

		result.include("message", "Sessao reservada com sucesso por " + CURRENCY.format(precoTotal));

		result.redirectTo(IndexController.class).index();
	}

	@Get @Path("/espetaculo/{espetaculoId}/sessoes")
	public void sessoes(Long espetaculoId) {
		espetaculo = carregaEspetaculo(espetaculoId);

		result.include("espetaculo", espetaculo);
	}


	@Post @Path("/espetaculo/{espetaculoId}/sessoes")
	public void cadastraSessoes(Long espetaculoId, LocalDate inicio, LocalDate fim, LocalTime horario, Periodicidade periodicidade) {
		espetaculo = carregaEspetaculo(espetaculoId);

		espetaculo.criaSessoes(inicio, fim, horario, periodicidade);
		
		agenda.agende(espetaculo.getSessoes());

		result.include("message", espetaculo.getSessoes().size() + " sess�es criadas com sucesso");
		result.redirectTo(this).lista();
	}

	private Espetaculo carregaEspetaculo(Long espetaculoId) {
		espetaculo = agenda.espetaculo(espetaculoId);
		if (espetaculo == null) {
			validator.add(new ValidationMessage("", ""));
		}
		validator.onErrorUse(status()).notFound();
		return espetaculo;
	}
}
