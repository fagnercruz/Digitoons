package br.com.adnavsystens.managebeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import br.com.adnavsystens.connection.GenericDAO;
import br.com.adnavsystens.models.Usuario;
import br.com.adnavsystens.models.projeto.Grupo;


@ManagedBean
public class GrupoMBean {
	

	private Grupo grupo = new Grupo();
	private GenericDAO<Grupo> daoGrupo = new GenericDAO<Grupo>();
	private List<Grupo> grupos = new ArrayList<Grupo>();
	
	/* Atribui ao usuário da sessão a propriedade do grupo criado */
	private Usuario usuarioLogado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");
	private Long idGrupo;
	
	private Long idDeTeste;
	
		
	public String salvar() {
		grupo.setCriadorResponsavel(usuarioLogado);
		daoGrupo.salvar(grupo);
		grupo = new Grupo(); // limpa os campos
		listarGruposUsuarioLogado();
		return "";
	}

	public String carregarDetalhes() {
		Grupo auxGp = new Grupo();
		auxGp.setId(idGrupo);
		grupo = daoGrupo.pesquisar(auxGp);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("grupoNaSessao", grupo);
		return "grupo_detalhes";
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

	public Long getIdDeTeste() {
		return idDeTeste;
	}

	public void setIdDeTeste(Long idDeTeste) {
		this.idDeTeste = idDeTeste;
	}

}
