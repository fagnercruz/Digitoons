package br.com.adnavsystens;



import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class TestExemplo {

//	@Test
//	public void testaConexao() {
//		EntityManager manager = HibernateUtils.getEntityManager();
//
//		assertThat(manager, null);
//
//	}
	
	
	@Test
	public void testaNumero() {
		int numero = 10;
		
		assertEquals(numero, 10);
	}
	
}
