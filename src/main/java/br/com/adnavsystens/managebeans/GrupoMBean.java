package br.com.adnavsystens.managebeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
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
	
	
	public String imprime() {
		System.out.println(usuarioLogado);
		System.out.println(grupo);
		return "";
	}
	
	public String salvar() {
		grupo.setCriadorResponsavel(usuarioLogado);
		daoGrupo.salvar(grupo);
		grupo = new Grupo(); // limpa os campos
		return "";
	}
	
	public void listarTodosgrupos() {
		grupos = daoGrupo.pesquisarTodos(Grupo.class);
	}
	
	@PostConstruct
	public void listarTodosDoUsuario() {
		String hql = "from Grupo g where g.criadorResponsavel.id = :idUsuario";
		
		EntityManager manager = daoGrupo.getEntityManager();
		
		grupos = (List<Grupo>) manager.createQuery(hql)
				.setParameter("idUsuario", usuarioLogado.getId())
				.getResultList();

		
	}
	
	public void excluir() {
		daoGrupo.excluir(grupo);
	}


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

	
	
	
	
}
