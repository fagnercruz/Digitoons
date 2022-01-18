package br.com.adnavsystens;

import org.junit.jupiter.api.Test;

import br.com.adnavsystens.connection.HibernateUtils;

public class ClasseTeste {

	@Test
	public void testaConexao() {
		HibernateUtils.getEntityManager();
	}
}
