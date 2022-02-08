package br.com.adnavsystens;

import org.junit.Test;

import br.com.adnavsystens.connection.HibernateUtils;
import junit.framework.TestCase;


public class ClasseTeste extends TestCase {

	@Test
	public void testaConexao() {
		HibernateUtils.getEntityManager();
	}
}
