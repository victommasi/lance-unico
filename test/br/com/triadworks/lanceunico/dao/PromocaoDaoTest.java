package br.com.triadworks.lanceunico.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.triadworks.lanceunico.modelo.Cliente;
import br.com.triadworks.lanceunico.modelo.Lance;
import br.com.triadworks.lanceunico.modelo.Promocao;
import br.com.triadworks.lanceunico.modelo.Status;
import br.com.triadworks.lanceunico.util.JPAUtil;
import builders.CriadorDePromocao;

public class PromocaoDaoTest {

	EntityManager em;

	@Before
	public void setUp(){
		em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();
	}
	
	@After
	public void tearDown(){
		em.getTransaction().rollback();
		em.close();
	}
	
	@Test
	public void deveEncontrarPromocoesEncerradas(){
		//cenario
		Promocao aberta = new CriadorDePromocao()
									.para("Xbox")
									.comStatus(Status.ABERTA)
									.cria();
		
		Promocao encerrada = new CriadorDePromocao()
									.para("Xbox")
									.comStatus(Status.ENCERRADA)
									.cria();
		em.persist(aberta);
		em.persist(encerrada);
		
		//acao
		PromocaoDao dao = new PromocaoDao(em);
		Long total = dao.totalDeEncerradas();
		
		//validacao
		Long totalEsperado = 1L;
		assertEquals(totalEsperado, total);
		
	}
	
	@Test
	public void naoDeveEncontrarPromocoesEncerradas(){
		//cenario
		Promocao aberta = new CriadorDePromocao()
									.para("Xbox")
									.comStatus(Status.ABERTA)
									.cria();
		
		em.persist(aberta);
		
		//acao
		PromocaoDao dao = new PromocaoDao(em);
		Long total = dao.totalDeEncerradas();
		
		//validacao
		Long totalEsperado = 0L;
		assertEquals(totalEsperado, total);
	}
	
	@Test
	public void deveRemoverUmaPromocao(){
		//cenario
		Promocao promocao = new CriadorDePromocao()
									.para("Xbox")
									.cria();
		em.persist(promocao);
		
		//acao
		PromocaoDao dao = new PromocaoDao(em);
		dao.remove(promocao);
		
		//validacao
		Promocao PromocaoDoBanco = dao.carrega(promocao.getId());
		assertNull(PromocaoDoBanco);
		
	}
	
	@Test
	public void deveRegistrarNovoLanceNaPromocao(){
		
		//cenario
		Cliente rafael = new Cliente("Rafael");
		Promocao promocao = new CriadorDePromocao()
									.para("Xbox")
									.cria();
		em.persist(rafael);
		em.persist(promocao);
		
		//acao
		Integer id = promocao.getId();
		Lance lance = new Lance(rafael, 100);
		
		PromocaoDao dao = new PromocaoDao(em);
		dao.registraLance(id, lance);
		
		//validacao
		Promocao promocaoDoBanco = dao.carrega(id);
		assertEquals(1, promocaoDoBanco.getLances().size());
	}
}
