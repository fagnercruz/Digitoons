package br.com.adnavsystens.managebeans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.adnavsystens.connection.GenericDAO;
import br.com.adnavsystens.models.projeto.Projeto;

@ManagedBean
public class ProjetoMBean {

	private Projeto projeto = new Projeto();
	private GenericDAO<Projeto> daoProjeto = new GenericDAO<>();
	private Long idProjeto;
		
	@PostConstruct
	public String carregarDetalhesProjeto() {
		Projeto auxProj = new Projeto();
		auxProj.setId(idProjeto);
		projeto = daoProjeto.pesquisar(auxProj);
		return "admin/info_projetos";
	}
	
	public Projeto getProjeto() {
		return projeto;
	}
	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}
	public Long getIdProjeto() {
		return idProjeto;
	}
	public void setIdProjeto(Long idProjeto) {
		this.idProjeto = idProjeto;
	}
	
	
	
	
}
