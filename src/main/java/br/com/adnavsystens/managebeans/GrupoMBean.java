package br.com.adnavsystens.managebeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import br.com.adnavsystens.connection.GenericDAO;
import br.com.adnavsystens.enuns.Status;
import br.com.adnavsystens.models.Usuario;
import br.com.adnavsystens.models.projeto.Grupo;
import br.com.adnavsystens.models.projeto.Projeto;

@ManagedBean
@ViewScoped
public class GrupoMBean {

	private Grupo grupo = new Grupo();
	private Projeto projeto = new Projeto();
	
	private GenericDAO<Grupo> daoGrupo = new GenericDAO<Grupo>();
	private GenericDAO<Projeto> daoProjeto = new GenericDAO<Projeto>();
	
	private List<Grupo> grupos = new ArrayList<Grupo>();
	private List<Projeto> projetos = new ArrayList<Projeto>();
	
	/* Atribui ao usuário da sessão a propriedade do grupo criado */
	private Usuario usuarioLogado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");
	private Long idGrupo;

	public String imprime() {
		System.out.println(usuarioLogado);
		System.out.println(grupo);
		return "";
	}
	
	public String salvar() {
		grupo.setCriadorResponsavel(usuarioLogado);
		daoGrupo.salvar(grupo);
		grupo = new Grupo(); // limpa os campos
		listarTodosDoUsuario();
		return "";
	}
	public String salvarProjeto() {
		Grupo gpAux = new Grupo();
//		gpAux.setId((Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idGrupoDetalhes"));
		gpAux.setId(idGrupo);
		projeto.setGrupo((Grupo) daoGrupo.pesquisar(gpAux));
		projeto.setStatus(Status.CRIADO);
		daoProjeto.salvar(projeto);
		projeto = new Projeto();
		grupo = daoGrupo.pesquisar(gpAux); // evita a perda do grupo atualizado após voltar para a tela.
		listarProjetos();
		return "";
	}
	
	public String carregarDetalhes() {
		Grupo auxGp = new Grupo();
		auxGp.setId(idGrupo);
		grupo = daoGrupo.pesquisar(auxGp);
		//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idGrupoDetalhes", grupo.getId());
		listarProjetos();
		return "/admin/info_grupo";
	}
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void listarTodosDoUsuario() {
		if(usuarioLogado != null) {
			String hql = "from Grupo g where g.criadorResponsavel = :idUsuario";
			
			EntityManager manager = daoGrupo.getEntityManager();
			
			grupos = (List<Grupo>) manager.createQuery(hql)
					.setParameter("idUsuario", usuarioLogado)
					.getResultList();
		}
	}
	
	public void excluir() {
		daoGrupo.excluir(grupo);
	}

	/* métodos auxiliares */
	
	@SuppressWarnings("unchecked")
	//@PostConstruct 
	public void listarProjetos() {
		String hql = "from Projeto p where p.grupo = :grupo";
		EntityManager manager = daoProjeto.getEntityManager();
		projetos = (List<Projeto>) manager.createQuery(hql).setParameter("grupo", grupo).getResultList();
	}
	
	public boolean possuiProjetos() {
		return false;
	}
	
	
	/* Getters e Setter */
	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	public Long getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}

	public List<Projeto> getProjetos() {
		return projetos;
	}

	public void setProjetos(List<Projeto> projetos) {
		this.projetos = projetos;
	}

}
