package br.com.adnavsystens.managebeans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import br.com.adnavsystens.connection.GenericDAO;
import br.com.adnavsystens.enuns.Status;
import br.com.adnavsystens.models.projeto.Capitulo;
import br.com.adnavsystens.models.projeto.Projeto;

@ManagedBean
public class CapituloMBean {

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
		Long idProjeto = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idProjeto"));
		
		System.out.println(idProjeto);
		
		if(idProjeto == null || idProjeto == 0 ) {
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
		try {
			capitulo = daoCapitulo.salvar(capitulo);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Não foi possível salvar capítulo: " + e.getLocalizedMessage()));
		}
		
		if(capitulo.getId() != null) {
			projeto.setStatus(Status.EM_ANDAMENTO);
			try {
				daoProjeto.salvar(projeto);
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro!", "Não foi possível finalizar a operação:  " + e.getLocalizedMessage()));
				return initCapitulo();
			}
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ok!", "Capítulo foi salvo com sucesso."));
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
