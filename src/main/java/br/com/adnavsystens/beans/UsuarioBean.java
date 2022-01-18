package br.com.adnavsystens.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import br.com.adnavsystens.connection.GenericDAO;
import br.com.adnavsystens.models.Usuario;

@ManagedBean
public class UsuarioBean {

	Usuario usuario = new Usuario();
	GenericDAO<Usuario> daousuario = new GenericDAO<Usuario>();
	List<Usuario> listaUsuarios = new ArrayList<Usuario>();
	
	public String salvar(Usuario u) {
		daousuario.salvar(u);
		return null;
	}
	
	public void buscar() {
		
	}
	
	@PostConstruct
	public void listar() {
		listaUsuarios = (List<Usuario>) daousuario.pesquisarTodos(Usuario.class);
	}
	
	public void remover(Usuario u) {
		daousuario.excluir(u);
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}
	
	
}
