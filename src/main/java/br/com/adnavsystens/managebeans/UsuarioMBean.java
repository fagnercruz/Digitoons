package br.com.adnavsystens.managebeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import br.com.adnavsystens.connection.GenericDAO;
import br.com.adnavsystens.models.Usuario;

@ViewScoped
@ManagedBean
public class UsuarioMBean {

	Usuario usuario = new Usuario();
	GenericDAO<Usuario> daousuario = new GenericDAO<Usuario>();
	List<Usuario> listaUsuarios = new ArrayList<Usuario>();
	
	public String salvar() {
		try {
			daousuario.salvar(usuario);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK!", "Usuário salvo"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro!", "Não foi possível salvar:  " + e.getLocalizedMessage()));
		}
		usuario = new Usuario();
//		refresh(); /* quando usar primefaces*/
		listar();
		return "";
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
	
	// chamar esse método para atualizar datatable do primefaces
	@SuppressWarnings("unused")
	private void refresh() {  
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();  
        ViewHandler viewHandler = application.getViewHandler();  
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());  
        context.setViewRoot(viewRoot);  
        context.renderResponse();  
    } 
	
}
