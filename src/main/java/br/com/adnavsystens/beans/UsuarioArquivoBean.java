package br.com.adnavsystens.beans;

import javax.faces.bean.ManagedBean;

import org.apache.catalina.core.ApplicationPart;

import br.com.adnavsystens.connection.GenericDAO;
import br.com.adnavsystens.models.UsuarioArquivoModel;
import br.com.adnavsystens.utils.ArquivoUtils;

@ManagedBean
public class UsuarioArquivoBean {

	private UsuarioArquivoModel usuario = new UsuarioArquivoModel();
	private GenericDAO<UsuarioArquivoModel> daoUsuario = new GenericDAO<UsuarioArquivoModel>();
	private ApplicationPart arquivo;

	public String salvar() {
		String caminho = ArquivoUtils.armazenaImagem("C:\\XXX\\YYY\\", arquivo);
		if(caminho != null) {
			usuario.setCaminhoDaImagem(caminho);
			usuario = daoUsuario.salvar(usuario);
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
