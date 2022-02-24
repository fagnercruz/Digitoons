package br.com.adnavsystens.managebeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.apache.catalina.core.ApplicationPart;

import br.com.adnavsystens.connection.GenericDAO;
import br.com.adnavsystens.enuns.FilePaths;
import br.com.adnavsystens.models.Usuario;
import br.com.adnavsystens.utils.ArquivoUtils;
import br.com.adnavsystens.utils.MensagensUtils;

@ManagedBean
public class UsuarioMBean {

	Usuario usuario = new Usuario();
	GenericDAO<Usuario> daousuario = new GenericDAO<Usuario>();
	List<Usuario> listaUsuarios = new ArrayList<Usuario>();
	private ApplicationPart arquivo;
	
	public String salvar() {
		try {
			if(usuario.getId() != null && arquivo == null) {
				Usuario aux = new Usuario();
				aux.setId(usuario.getId());
				aux = daousuario.pesquisar(aux);
				usuario.setCaminhoDaImagem(aux.getCaminhoDaImagem());
			} else {
				//Se for um update e o usuario estiver enviando um novo arquivo, tem q deletar o anterior se existir
				if(usuario.getId() != null && arquivo != null) {
					Usuario aux = new Usuario();
					aux.setId(usuario.getId());
					aux = daousuario.pesquisar(aux);
					ArquivoUtils.excluirArquivo(aux.getCaminhoDaImagem());
				}
				usuario.setCaminhoDaImagem(ArquivoUtils.salvarArquivo(arquivo, FilePaths.USUARIO));
			}
			daousuario.salvar(usuario);
			MensagensUtils.addMensagemSucesso("Sucesso", "usuário salvo.");
		} catch (Exception e) {
			MensagensUtils.addMensagemErro("Falha", "Não foi possível salvar usuário: " + e.getLocalizedMessage());
		}
		usuario = new Usuario();
//		refresh(); /* quando usar primefaces*/
		listar();
		return "";
	}
	
	public String editarUsuario() {
		Long id = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idusuario"));
		Usuario aux = new Usuario();
		aux.setId(id);
		usuario = daousuario.pesquisar(aux);
		listar();
		return "";
	}
	
	@PostConstruct
	public void listar() {
		listaUsuarios = (List<Usuario>) daousuario.pesquisarTodos(Usuario.class);
	}
	
	public String remover() {
		Long id = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idusuario"));
		Usuario aux = new Usuario();
		aux.setId(id);
		aux = daousuario.pesquisar(aux);
		if(!ArquivoUtils.excluirArquivo(aux.getCaminhoDaImagem())) {
			MensagensUtils.addMensagemErro("Erro", "Não foi possível remover: falha ao excluir foto do usuário");
			return "";
		}
		aux = daousuario.excluir(aux);
		if(aux != null) {
			MensagensUtils.addMensagemAlerta("Atenção", aux.getNome() + " foi removido do sistema com sucesso ");
		} else {
			MensagensUtils.addMensagemErro("Erro", "Não foi possível remover");
		}
		
		listar();
		return "";
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
	

	public ApplicationPart getArquivo() {
		return arquivo;
	}

	public void setArquivo(ApplicationPart arquivo) {
		this.arquivo = arquivo;
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
	
	public boolean isImageExist() {
		if(usuario == null ||(usuario.getCaminhoDaImagem() == null) || (usuario.getCaminhoDaImagem().isEmpty())) {
			return false;
		} 
		return true;
	}
	
}
