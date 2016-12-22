package br.com.triadworks.lanceunico.service;


import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.triadworks.lanceunico.modelo.Cliente;
import br.com.triadworks.lanceunico.modelo.Lance;
import br.com.triadworks.lanceunico.modelo.Promocao;
import builders.CriadorDePromocao;

public class SorteioTest {

	private Sorteio sorteio;
	private Cliente rafael;
	private Cliente handerson;
	private Cliente rommel;
	private Cliente davi;

	@Before
	public void setUp(){
		this.sorteio = new Sorteio();
		this.rafael = new Cliente("Rafael");
		this.handerson = new Cliente("Handerson");
		this.rommel = new Cliente("Rommel");
		this.davi = new Cliente("Davi");
	}
	
	
	@Test
	public void deveSorteiarPromocaoComLancesEmOrdemCrescente() {

		//criação do cenario
		Promocao promocao = new CriadorDePromocao()
									.para("Xbox")
									.comLance(rommel, 230.0)
									.comLance(rafael, 350.0)
									.comLance(handerson, 570.0)
									.cria();
											
		//executa açao
		sorteio.sorteia(promocao);
		
		double maiorEsperado = 570.0;
		double menorEsperado = 230.0;
		
		//validacao
		assertEquals(maiorEsperado, sorteio.getMaiorLance(), 0.0001);
		assertEquals(menorEsperado, sorteio.getMenorLance(), 0.0001);
		
	}
	
	@Test
	public void deveSorteiarPromocaoComLancesEmOrdemDecrescente(){
		
		//criação do cenario
		Promocao promocao = new CriadorDePromocao()
									.para("Xbox")
									.comLance(rommel, 400.0)
									.comLance(rafael, 300.0)
									.comLance(handerson, 250.0)
									.cria();
		
		//executa açao
		sorteio.sorteia(promocao);
		
		double maiorEsperado = 400.0;
		double menorEsperado = 250.0;
				
		assertEquals(maiorEsperado, sorteio.getMaiorLance(), 0.0001);
		assertEquals(menorEsperado, sorteio.getMenorLance(), 0.0001);
		
	}
	
	@Test
	public void deveSortearPromocaoComApenasUmLance(){
		
		Promocao promocao = new CriadorDePromocao()
									.para("Forno Microondas")
									.comLance(rafael, 600.0)
									.cria();
		
		//executa açao
		sorteio.sorteia(promocao);
		
		assertEquals(600.00, sorteio.getMaiorLance(), 0.0001);
		assertEquals(600.00, sorteio.getMenorLance(), 0.0001);
		
	}

	@Test
	public void deveSortearPromocaoComTresMenoresLances() {
		
		//criação do cenario
		Promocao promocao = new CriadorDePromocao()
									.para("Computador")
									.comLance(rafael, 300.0)
									.comLance(handerson, 250.0)
									.comLance(rommel, 400.0)
									.comLance(davi, 500.0)
									.cria();
		
		//acao
		sorteio.sorteia(promocao);
		
		//validacao
		List<Lance> menores = sorteio.getTresMenoresLances(); 
		
		assertEquals(3, menores.size());
		assertEquals(250, menores.get(0).getValor(), 0.0001);
		assertEquals(300, menores.get(1).getValor(), 0.0001);
		assertEquals(400, menores.get(2).getValor(), 0.0001);
		
	}
	
	@Test
	public void deveSortearPromocaoComDoisDosTresMenoresLances(){
		
		//criação do cenario
		Promocao promocao = new CriadorDePromocao()
									.para("Computador")
									.comLance(rafael, 300.0)
									.comLance(handerson, 250.0)
									.comLance(rommel, 400.0)
									.comLance(davi, 500.0)
									.cria();

		//acao
		sorteio.sorteia(promocao);
		
		//validacao
		List<Lance> menores = sorteio.getTresMenoresLances(); 
		
		assertEquals(3, menores.size());
		assertEquals(250, menores.get(0).getValor(), 0.0001);
		assertEquals(300, menores.get(1).getValor(), 0.0001);

	}
	
	@Test(expected = RuntimeException.class)
	public void naoDeveSortearPromocaoComListaDosTresMenoresLancesVazia(){
		
		//criação do cenario
		Promocao promocao = new CriadorDePromocao()
										.para("Computador")
										.cria();
		
		//acao
		sorteio.sorteia(promocao);
		
		//validacao
		List<Lance> menores = sorteio.getTresMenoresLances(); 
		//assertEquals(0, menores.size());
				
	}

	@Test
	public void deveEncontrarOMenorLanceUnico(){
		
		Promocao promocao = new CriadorDePromocao()
									.para("Xbox")
									.comLance(davi, 1000.0)
									.comLance(handerson, 30.0)
									.comLance(rafael, 70)
									.comLance(rommel, 30)
									.cria();
		
		sorteio.sorteia(promocao);
		
		//assertEquals(sorteio.getMenorLanceUnico, actual);
									
		
	}

}
