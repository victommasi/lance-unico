package br.com.triadworks.lanceunico.service;


import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import static org.mockito.Mockito.*;
import br.com.triadworks.lanceunico.dao.PromocaoDao;
import br.com.triadworks.lanceunico.modelo.Promocao;
import br.com.triadworks.lanceunico.modelo.Status;
import br.com.triadworks.lanceunico.util.DateUtils;
import builders.CriadorDePromocao;

public class EncerradorDePromocoesTest {
	
	private Date antiga = DateUtils.novaData("01/01/1990");
	private Date ontem = DateUtils.novaData("16/12/2016");
	private Date hoje = DateUtils.novaData("17/12/2016");
	
	@Test
	public void deveEncerrarPromocaoVencida(){
		Promocao xbox = new CriadorDePromocao()
									.para("Xbox")
									.naData(antiga)
									.cria();
		List<Promocao> promocoes = Arrays.asList(xbox);
		
		PromocaoDao daoFalso = mock(PromocaoDao.class);
		when(daoFalso.abertas()).thenReturn(promocoes);
		
		
		
		EncerradorDePromocoes encerrador = new EncerradorDePromocoes(daoFalso);
		encerrador.encerra();
		
		assertEquals(Status.ENCERRADA, xbox.getStatus());
		assertEquals(1, promocoes.size());
		
	}
	
	@Test
	public void naoDeveEncerrarPromocaoVencida(){
		Promocao xbox = new CriadorDePromocao()
									.para("Xbox")
									.naData(antiga)
									.cria();
		
		Promocao ps4 = new CriadorDePromocao()
									.para("Ps4")
									.naData(ontem)
									.cria();
		
		Promocao ps3 = new CriadorDePromocao()
									.para("Ps3")
									.naData(hoje)
									.cria();
		
		List<Promocao> promocoes = Arrays.asList(xbox, ps4, ps3);
		
		PromocaoDao daoFalso = mock(PromocaoDao.class);
		when(daoFalso.abertas()).thenReturn(promocoes);
		
		EncerradorDePromocoes encerrador = new EncerradorDePromocoes(daoFalso);
		encerrador.encerra();
		
		assertEquals(Status.ABERTA, ps3.getStatus());
		assertEquals(Status.ABERTA, ps4.getStatus());
		assertEquals(Status.ENCERRADA, xbox.getStatus());
		assertEquals(1, encerrador.encerra());
		
	}

	@Test
	public void deveAtualizarPromocoesEncerradas(){
		
		Promocao xbox = new CriadorDePromocao()
								.para("Xbox")
								.naData(antiga)
								.cria();
		
		List<Promocao> promocoes = Arrays.asList(xbox);
		
		PromocaoDao daoFalso = mock(PromocaoDao.class);
		when(daoFalso.abertas()).thenReturn(promocoes);
		
		EncerradorDePromocoes encerrador =  new EncerradorDePromocoes(daoFalso);
		encerrador.encerra();
		
		verify(daoFalso).atualiza(xbox);
		verify(daoFalso, times(1)).atualiza(xbox);
		
	}

	@Test
	public void deveEncerrarPromocoesRestantesMesmoEmCasoDeFalha(){
		
		Promocao p1 = new CriadorDePromocao()
								.para("Xbox")
								.naData(antiga)
								.cria();
		Promocao p2 = new CriadorDePromocao()
								.para("Ps4")
								.naData(antiga)
								.cria();

		List<Promocao> promocoes = Arrays.asList(p1, p2);
		
		PromocaoDao dao = mock(PromocaoDao.class);
		when(dao.abertas()).thenReturn(promocoes);
		
		doThrow(new RuntimeException()).when(dao).atualiza(p1);
		
		EncerradorDePromocoes encerrador = new EncerradorDePromocoes(dao);
		int qtdEncerrados = encerrador.encerra();
		
		verify(dao).atualiza(p2);
		assertEquals(1, qtdEncerrados);
		
		
	}

}
