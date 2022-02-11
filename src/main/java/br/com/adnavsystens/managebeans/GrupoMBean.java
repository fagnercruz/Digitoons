package br.com.adnavsystens.managebeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import br.com.adnavsystens.connection.GenericDAO;
import br.com.adnavsystens.models.Usuario;
import br.com.adnavsystens.models.projeto.Grupo;
import br.com.adnavsystens.models.projeto.Projeto;


@ManagedBean
public class GrupoMBean {
	

	private Grupo grupo = new Grupo();
	private GenericDAO<Grupo> daoGrupo = new GenericDAO<Grupo>();
	private List<Grupo> grupos = new ArrayList<Grupo>();
	
	/* Atribui ao usuário da sessão a propriedade do grupo criado */
	private Usuario usuarioLogado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");
	private Long idGrupo;
	
	
		
	public String salvar() {
		grupo.setCriadorResponsavel(usuarioLogado);
		try {
			daoGrupo.salvar(grupo);
			String nomegrupo = grupo.getNome();
			grupo = new Grupo(); // limpa os campos
			listarGruposUsuarioLogado();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso! ", nomegrupo + " foi salvo"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro. ","Não foi possível salvar: " + e.getLocalizedMessage()));
			e.printStackTrace();
		}
		
		return "";
	}

	public void carregarDetalhes() {
		Grupo auxGp = new Grupo();
		auxGp.setId(idGrupo);
		grupo = daoGrupo.pesquisar(auxGp);
		List<Projeto> lista = listarProjetosDoGrupo(idGrupo);
		grupo.setProjetos(lista);
	}
	
	
	/* esse metodo faz mais sentido estando em usuario */
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void listarGruposUsuarioLogado() {
		if(usuarioLogado != null) {
			EntityManager manager = daoGrupo.getEntityManager();
			
			grupos = (List<Grupo>) manager
					.createQuery("from Grupo g where g.criadorResponsavel = :idUsuario")
					.setParameter("idUsuario", usuarioLogado)
					.getResultList();
		}
	}
	/** gambiarra para poder atualizar a lista de projetos após novos cadastros
	 * 	pois o Hibernate não está fazendo bem o srviço dele
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public List<Projeto> listarProjetosDoGrupo(Long idgrupo){
		return (List<Projeto>) daoGrupo.getEntityManager().createQuery("from Projeto p where p.grupo.id = :idGrupo").setParameter("idGrupo", idgrupo).getResultList();
	}
	
	public void excluir() {
		daoGrupo.excluir(grupo);
	}

	public void resetGrupoSessao() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("grupoNaSessao");
	}
		
	/* Getters e Setter */
	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
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

}
