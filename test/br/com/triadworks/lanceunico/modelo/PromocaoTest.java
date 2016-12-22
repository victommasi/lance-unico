package br.com.triadworks.lanceunico.modelo;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import builders.CriadorDePromocao;

public class PromocaoTest {
	
	private Cliente rafael;
	private Cliente handerson;
	private Cliente rommel;
	private Cliente davi;
	
	@Before
	public void setUp(){
		this.rafael = new Cliente("Rafael");
		this.handerson = new Cliente("Handerson");
		this.rommel = new Cliente("Rommel");
		this.davi = new Cliente("Davi");
	}
	
	@Test
	public void deveRegistrarUmLance(){
		Promocao promocao = new CriadorDePromocao()
									.para("Xbox")
									.comLance(rafael, 500.0)
									.cria();
		
		assertEquals(1, promocao.getLances().size(), 0.0001);
		assertEquals(500.0, promocao.getLances().get(0).getValor(), 0.0001);
		
	}
	
	@Test
	public void deveRegistrarVariosLances(){
		Promocao promocao = new CriadorDePromocao()
									.para("Xbox")
									.comLance(davi, 400.0)
									.comLance(rafael, 500.0)
									.cria();
		
		assertEquals(2, promocao.getLances().size(), 0.0001);
		assertEquals(400.0, promocao.getLances().get(0).getValor(), 0.0001);
		assertEquals(500.0, promocao.getLances().get(1).getValor(), 0.0001);
		
		
	}

	@Test
	public void naoDeveRegistrarDoisLancesEmSequencia(){
		Promocao promocao = new CriadorDePromocao()
									.para("Xbox")
									.comLance(rafael, 500.0)
									.comLance(rafael, 600.0)
									.cria();
		
		assertEquals(1, promocao.getLances().size(), 0.0001);
		assertEquals(500.0, promocao.getLances().get(0).getValor(), 0.0001);
		
	}
	
	@Test
	public void naoDeveRegistrarMaisDe5LancesPorCliente(){
		Promocao promocao = new CriadorDePromocao()
									.para("Xbox")
									.comLance(rafael, 500.0)
									.comLance(handerson, 600.0)
									.comLance(rafael, 501.0)
									.comLance(handerson, 601.0)
									.comLance(rafael, 602.0)
									.comLance(handerson, 602.0)
									.comLance(rafael, 603.0)
									.comLance(handerson, 603.0)
									.comLance(rafael, 605.0)
									.comLance(handerson, 605.0)
									.cria();
		
		assertEquals(10, promocao.getLances().size(), 0.0001);
		assertEquals(605.0, promocao.getLances().get(9).getValor(), 0.0001);
		
	}
	
	@Test(expected = RuntimeException.class)
	public void naoDeveRegistrarLanceNegativo(){
		Promocao promocao = new CriadorDePromocao()
									.para("Xbox")
									.comLance(rafael, -300.0)
									.cria();
		
	}
	
	@Test(expected = RuntimeException.class)
	public void naoDeveExcederValorMaximoDaPromocao(){
		Promocao promocao = new CriadorDePromocao()
									.para("Xbox")
									.comValorMaximo(200)
									.comLance(rafael, 500)
									.cria();
	}
}

