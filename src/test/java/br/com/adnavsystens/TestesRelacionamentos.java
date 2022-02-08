package br.com.adnavsystens;



import org.junit.Test;

import br.com.adnavsystens.connection.GenericDAO;
import br.com.adnavsystens.models.Login;
import br.com.adnavsystens.models.Usuario;

public class TestesRelacionamentos {

	GenericDAO<Usuario> daoUsuario = new GenericDAO<Usuario>();
	GenericDAO<Login> daoLogin = new GenericDAO<Login>();
	
	@Test
	public void testeUsuarioLogin() {
		Usuario user = new Usuario();
		Login login = new Login();
		
		login.setUsername("admin");
		login.setPassword("admin");
		
		user.setNome("Fagner Cruz");
		user.setEmail("fagnerccruz@gmail.com");
		
		user = daoUsuario.salvar(user);
		login.setUsuario(user);
		daoLogin.salvar(login);
		
		
	}
	//@Test
	public void resgatarUsuarioViaLogin() {
		Login login = new Login();
		login.setId(3L);
		login = daoLogin.pesquisar(login);
		
		System.out.println(login.getUsername() + " ---> " + login.getUsuario().getNome());
	}
	
	@Test
	public void resgatarLoginViaUsuario() {
		Usuario user = new  Usuario();
		user.setId(5L);
		user = (Usuario) daoUsuario.pesquisar(user);
		Login login = (Login) daoLogin.pesquisar(user.getLogin());
		
		System.out.println(user.getNome() + " --> " + login.getUsername());
	}
	
}
