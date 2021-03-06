package br.com.adnavsystens.managebeans;

import javax.faces.bean.ManagedBean;

import org.apache.catalina.core.ApplicationPart;

import br.com.adnavsystens.connection.GenericDAO;
import br.com.adnavsystens.enuns.FilePaths;
import br.com.adnavsystens.models.UsuarioArquivoModel;
import br.com.adnavsystens.utils.ArquivoUtils;
import br.com.adnavsystens.utils.MensagensUtils;

@ManagedBean
public class UsuarioArquivoBean {

	private UsuarioArquivoModel usuario = new UsuarioArquivoModel();
	private GenericDAO<UsuarioArquivoModel> daoUsuario = new GenericDAO<UsuarioArquivoModel>();
	private ApplicationPart arquivo;

	String caminho = "";
	public String salvar() {
		try {
			caminho = ArquivoUtils.salvarArquivo(arquivo, FilePaths.USUARIO);
		} catch (Exception e) {
			MensagensUtils.addMensagemFatal("Falha", e.getLocalizedMessage());
		}

		
		if(caminho != null) {
			usuario.setCaminhoDaImagem(caminho);
			try {
				usuario = daoUsuario.salvar(usuario);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "";
	}

	public UsuarioArquivoModel getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioArquivoModel usuario) {
		this.usuario = usuario;
	}

	public ApplicationPart getArquivo() {
		return arquivo;
	}

	public void setArquivo(ApplicationPart arquivo) {
		this.arquivo = arquivo;
	}
	
	public boolean isImageExist() {
		if(usuario == null ||(usuario.getCaminhoDaImagem() == null) || (usuario.getCaminhoDaImagem().isEmpty())) {
			return false;
		} 
		return true;
	
	}
	
	

}
