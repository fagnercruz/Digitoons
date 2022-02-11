package br.com.adnavsystens.managebeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import br.com.adnavsystens.connection.GenericDAO;
import br.com.adnavsystens.enuns.Status;
import br.com.adnavsystens.models.projeto.Capitulo;
import br.com.adnavsystens.models.projeto.Grupo;
import br.com.adnavsystens.models.projeto.Projeto;

@ManagedBean
public class ProjetoMBean {

	private Projeto projeto = new Projeto();
	private Grupo grupo = new Grupo();
	
	private GenericDAO<Projeto> daoProjeto = new GenericDAO<>();
	private GenericDAO<Grupo> daoGrupo = new GenericDAO<>();
	
	/* atributos auxiliares */
	private Long idProjeto;
	private Long idGrupo;
	private List<Projeto> listaProjetos = new ArrayList<>();
		
	
	public void carregarDetalhesProjeto() {
		Projeto auxProj = new Projeto();
		auxProj.setId(idProjeto);
		projeto = daoProjeto.pesquisar(auxProj);
		List<Capitulo> capitulosAtualizados = listarCapitulosDoProjeto(idProjeto); // puxa a lista atualizada (depois analizar)
		projeto.setCapitulos(capitulosAtualizados);
	}
	
	public String salvar() {
		/*O projeto precisa de um grupo assossiado */
		Grupo grupoAux = new Grupo();
		idGrupo = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idGrupo"));
		grupoAux.setId(idGrupo);
		grupo = daoGrupo.pesquisar(grupoAux);
		projeto.setGrupo(grupo);
		projeto.setStatus(Status.CRIADO);
		projeto.setCapitulos(new ArrayList<Capitulo>()); // <-- prevenindo NPE quando cria o projeto e na exibição chama o getQtdeCapitulos
		daoProjeto.salvar(projeto);
		initProjeto();
		return "";
		
	}
	
	public Projeto pesquisar() {
		Projeto proj = new Projeto();
		proj.setId(idProjeto);
		return daoProjeto.pesquisar(proj);
	}
	
	@SuppressWarnings("unchecked")
	public List<Projeto> listarProjetos(Long idgrupo){
		EntityManager manager = daoProjeto.getEntityManager();
		return listaProjetos = (List<Projeto>) manager.createQuery("from Projeto p where p.grupo.id = :idGrupo order by p.id asc").setParameter("idGrupo", idgrupo).getResultList();
	}
	
	/** Gambiarra para atualizar a lista de capítulo que o Hibernate fez o favor de trazer sempre desatualizado */
	@SuppressWarnings("unchecked")
	public List<Capitulo> listarCapitulosDoProjeto(Long idProjeto){
		GenericDAO<Capitulo> daoCapitulo = new GenericDAO<>();
		Projeto proj = new Projeto();
		proj.setId(idProjeto);
		return (List<Capitulo>) daoCapitulo.getEntityManager().createQuery("from Capitulo c where c.projeto.id = :idProjeto").setParameter("idProjeto", idProjeto).getResultList();
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
