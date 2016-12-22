package br.com.triadworks.lanceunico.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.triadworks.lanceunico.modelo.Cliente;
import br.com.triadworks.lanceunico.util.JPAUtil;

public class ClienteDaoTest {
	
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
	public void deveBuscarClientePorEmail(){
		
		//cenario
		Cliente cliente = new Cliente("cliente", "email@gmail.com");
		em.persist(cliente);
		
		//ação
		ClienteDao dao = new ClienteDao(em);
		Cliente clienteDoBanco = dao.buscaPorEmail("email@gmail.com");
		
		//validacao
		assertEquals(cliente.getEmail(), clienteDoBanco.getEmail());
		assertEquals(cliente.getNome(), clienteDoBanco.getNome());
	
	}
	
	@Test
	public void naoDeveEncontrarClientePorEmail(){
		
		//cenario
		Cliente cliente = new Cliente("cliente", "email@gmail.com");
		em.persist(cliente);
		
		//ação
		ClienteDao dao = new ClienteDao(em);
		Cliente clienteDoBanco = dao.buscaPorEmail("wesley@gmail.com");
		
		//validacao
		assertNull(clienteDoBanco);
	
	}
}
