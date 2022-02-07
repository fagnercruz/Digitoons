package br.com.adnavsystens.managebeans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import br.com.adnavsystens.connection.GenericDAO;
import br.com.adnavsystens.enuns.Status;
import br.com.adnavsystens.models.projeto.Capitulo;
import br.com.adnavsystens.models.projeto.Projeto;

@ManagedBean
public class CapituloMBean {

	private Long idProjeto;
	private Capitulo capitulo = new Capitulo();
	private GenericDAO<Capitulo> daoCapitulo = new GenericDAO<>();
	private GenericDAO<Projeto> daoProjeto = new GenericDAO<>();
	private List<Capitulo> listaCapitulos = new ArrayList<>();

	
	public String initCapitulo() {
		capitulo = new Capitulo();
		return "";
	}
	
	public String salvar() {
		/* capitulo pertence a um determinado projeto */
		if(idProjeto == null) {
			return "";
		}
		
		Projeto projeto = new Projeto();
		projeto.setId(idProjeto);
		projeto = (Projeto) daoProjeto.pesquisar(projeto);
		
		if(projeto == null) {
			return null;
		}
		
		capitulo.setProjeto(projeto);
		capitulo.setDataLancamento(Calendar.getInstance());
		capitulo = daoCapitulo.salvar(capitulo);
		
		if(capitulo.getId() != null) {
			projeto.setStatus(Status.EM_ANDAMENTO);
			daoProjeto.salvar(projeto);
		}
		listarCapitulos();
		return initCapitulo();
	}
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void listarCapitulos() {
		EntityManager manager = daoCapitulo.getEntityManager();
		
		listaCapitulos = (List<Capitulo>) manager.createQuery("from Capitulo c where c.projeto = :projeto order by c.id asc")
			.setParameter("projeto", (Projeto) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("projetoSessao"))
			.getResultList();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Long getIdProjeto() {
		return idProjeto;
	}
	public void setIdProjeto(Long idProjeto) {
		this.idProjeto = idProjeto;
	}
	public Capitulo getCapitulo() {
		return capitulo;
	}
	public void setCapitulo(Capitulo capitulo) {
		this.capitulo = capitulo;
	}

	public List<Capitulo> getListaCapitulos() {
		return listaCapitulos;
	}

	public void setListaCapitulos(List<Capitulo> listaCapitulos) {
		this.listaCapitulos = listaCapitulos;
	}
	
	
}
