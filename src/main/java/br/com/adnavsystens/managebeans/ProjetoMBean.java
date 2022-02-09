package br.com.adnavsystens.managebeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import br.com.adnavsystens.connection.GenericDAO;
import br.com.adnavsystens.enuns.Status;
import br.com.adnavsystens.models.projeto.Grupo;
import br.com.adnavsystens.models.projeto.Projeto;

@ManagedBean
public class ProjetoMBean {

	private Projeto projeto = new Projeto();
	private Grupo grupo = new Grupo();
	
	private GenericDAO<Projeto> daoProjeto = new GenericDAO<>();
	
	/* atributos auxiliares */
	private Long idProjeto;
	private Long idGrupo;
	private List<Projeto> listaProjetos = new ArrayList<>();
		
	
	public void carregarDetalhesProjeto() {
		Projeto auxProj = new Projeto();
		auxProj.setId(idProjeto);
		projeto = daoProjeto.pesquisar(auxProj);
	}
	
	public String salvar() {
		/*O projeto precisa de um grupo assossiado */
		grupo = (Grupo) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("grupoNaSessao");
		projeto.setGrupo(grupo);
		projeto.setStatus(Status.CRIADO);
		daoProjeto.salvar(projeto);
		initProjeto();
		listarProjetos();
		return "";
		
	}
	
	public Projeto pesquisar() {
		Projeto proj = new Projeto();
		proj.setId(idProjeto);
		return daoProjeto.pesquisar(proj);
	}
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void listarProjetos(){
		Grupo gp = (Grupo) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("grupoNaSessao");
		EntityManager manager = daoProjeto.getEntityManager();
		listaProjetos = (List<Projeto>) manager.createQuery("from Projeto p where p.grupo = :grupo order by p.id asc").setParameter("grupo", gp).getResultList();
	}
	
	/* usado por outros MBs */
	@SuppressWarnings("unchecked")
	public List<Projeto> carregarProjetosDoGrupo(Long idGrupo){
		return (List<Projeto>) daoProjeto.getEntityManager().createQuery("from Projeto p where p.grupo = :grupo order by p.id asc").setParameter("grupo", idGrupo).getResultList();
	}

	
	public void excluir() {
		daoProjeto.excluir(projeto);
	}
	
	private void initProjeto() {
		projeto = new Projeto();
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

	public Long getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public List<Projeto> getListaProjetos() {
		return listaProjetos;
	}

	public void setListaProjetos(List<Projeto> listaProjetos) {
		this.listaProjetos = listaProjetos;
	}

}
