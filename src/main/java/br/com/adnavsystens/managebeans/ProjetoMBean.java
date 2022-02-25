package br.com.adnavsystens.managebeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import org.apache.catalina.core.ApplicationPart;

import br.com.adnavsystens.connection.GenericDAO;
import br.com.adnavsystens.enuns.FilePaths;
import br.com.adnavsystens.enuns.Status;
import br.com.adnavsystens.models.projeto.Capitulo;
import br.com.adnavsystens.models.projeto.Grupo;
import br.com.adnavsystens.models.projeto.Projeto;
import br.com.adnavsystens.utils.ArquivoUtils;
import br.com.adnavsystens.utils.MensagensUtils;

@ManagedBean
@ViewScoped
public class ProjetoMBean {

	private Projeto projeto = new Projeto();
	private Grupo grupo = new Grupo();
	
	private GenericDAO<Projeto> daoProjeto = new GenericDAO<>();
	private GenericDAO<Grupo> daoGrupo = new GenericDAO<>();
	
	/* atributos auxiliares */
	private Long idProjeto;
	private Long idGrupo;
	private List<Projeto> listaProjetos = new ArrayList<>();
	private ApplicationPart imgCapa;
	private ApplicationPart imgBanner;
		
	private void initProjeto() {
		projeto = new Projeto();
	}
	
	/**
	 *Método chamado para popular os dados do Projeto na view grupo_detalhes.jsf
	 */
	public void carregarDetalhesProjeto() {
		Projeto auxProj = new Projeto();
		auxProj.setId(idProjeto);
		projeto = daoProjeto.pesquisar(auxProj);
		List<Capitulo> capitulosAtualizados = listarCapitulosDoProjeto(idProjeto); // puxa a lista atualizada (depois analizar)
		projeto.setCapitulos(capitulosAtualizados);
	}
	
	public String editarProjeto() {
		Projeto aux = new Projeto();
		aux.setId(Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idProjeto")));
		projeto = daoProjeto.pesquisar(aux);
		return "";
	}
	
	public String excluirProjeto() {
				
		Projeto aux = new Projeto();
		Grupo auxGrupo = new Grupo();
		
		// foi optado por fazer uma consulta sql pois o hibernate não devolvia os dados do objeto atualizados
		aux.setId(Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idProjeto")));
		aux = daoProjeto.pesquisarSQL(aux);

		
		auxGrupo.setId(Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idGrupo")));
		auxGrupo = daoGrupo.pesquisar(auxGrupo);
		
		//remove as imagens do projeto
		if(!ArquivoUtils.excluirArquivo(aux.getImagemBanner())) {
			System.out.println("Banner não encontrado: " + aux.getImagemBanner());
		}
		if(!ArquivoUtils.excluirArquivo(aux.getImagemCapa())) {
			System.out.println("Capa não encontrada: " + aux.getImagemCapa());
		}
		
		
		// remove o projeto da lista e atualiza o pai para a remoção acontecer em cascata
		if(auxGrupo.getProjetos().remove(aux)) {
			try {
				daoGrupo.salvar(auxGrupo);
				MensagensUtils.addMensagemAlerta("Atenção", "O projeto " + aux.getTitulo() + " foi removido completamente" );
			} catch (Exception e) {
				MensagensUtils.addMensagemErro("Erro", "Não foi possível remover: " + e.getLocalizedMessage());
			}
		}
		return "";
	}
	
	public String salvar() {
		
		Grupo grupoAux = new Grupo();
		idGrupo = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idGrupo"));
		grupoAux.setId(idGrupo);
		grupo = daoGrupo.pesquisar(grupoAux);

		// se o projeto ja existe (id != null) so pega os dado do form (estado do bean) e joga no dao
		if(projeto.getId() == null) { 
			projeto.setGrupo(grupo);
			projeto.setStatus(Status.CRIADO);
			try {
				if(imgCapa != null && imgBanner != null) {
					projeto.setImagemCapa(ArquivoUtils.salvarArquivo(imgCapa, FilePaths.PROJETO));
					projeto.setImagemBanner(ArquivoUtils.salvarArquivo(imgBanner, FilePaths.PROJETO));
				} else {
					MensagensUtils.addMensagemErro("Erro", "Arquivo inválido");
					return "";
				}
				
			} catch (Exception e) {
				MensagensUtils.addMensagemErro("Erro", "Não foi possível salvar: " + e.getLocalizedMessage());
			}
			projeto.setCapitulos(new ArrayList<Capitulo>()); // <-- prevenindo NPE quando cria o projeto e na exibição chama o getQtdeCapitulos
		} else {
			//verifica se tem upload e tbm se já não tem arquivos no registro a ser atualizado
			Projeto auxProjeto = new Projeto();
			auxProjeto.setId(projeto.getId());
			auxProjeto = daoProjeto.pesquisar(auxProjeto);
			
			if(imgCapa == null) {
				projeto.setImagemCapa(auxProjeto.getImagemCapa());
			} else {
				if(auxProjeto.getImagemCapa() != null) {
					ArquivoUtils.excluirArquivo(auxProjeto.getImagemCapa());
				}
				try {
					projeto.setImagemCapa(ArquivoUtils.salvarArquivo(imgCapa, FilePaths.PROJETO));
				} catch (Exception e) {
					MensagensUtils.addMensagemErro("Erro", "Não foi possível salvar o arquivo:" + e.getLocalizedMessage());
				}
			}
			
			if(imgBanner == null) {
				projeto.setImagemBanner(auxProjeto.getImagemBanner());
			} else {
				if(auxProjeto.getImagemBanner() != null) {
					ArquivoUtils.excluirArquivo(auxProjeto.getImagemBanner());
				}
				try {
					projeto.setImagemBanner(ArquivoUtils.salvarArquivo(imgBanner, FilePaths.PROJETO));
				} catch (Exception e) {
					MensagensUtils.addMensagemErro("Erro", "Não foi possível salvar o arquivo:" + e.getLocalizedMessage());
				}
			}
			
			projeto.setGrupo(grupo);
			projeto.setCapitulos(listarCapitulosDoProjeto(projeto.getId()));
		}
		
		try {
			daoProjeto.salvar(projeto);
			if(projeto.getId() == null) {
				MensagensUtils.addMensagemSucesso("Sucesso", "Projeto salvo");
			}else {
				MensagensUtils.addMensagemSucesso("Sucesso", projeto.getTitulo() + " foi atualizado com sucesso.");
			}
			initProjeto();
		} catch (Exception e) {
			MensagensUtils.addMensagemErro("Falha", "Não foi possível salvar: " + e.getLocalizedMessage());
		}
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
		return (List<Capitulo>) daoCapitulo.getEntityManager().createQuery("from Capitulo c where c.projeto.id = :idProjeto order by c.id asc").setParameter("idProjeto", idProjeto).getResultList();
	}
	
	/* usado por outros MBs */
	@SuppressWarnings("unchecked")
	public List<Projeto> carregarProjetosDoGrupo(Long idGrupo){
		return (List<Projeto>) daoProjeto.getEntityManager().createQuery("from Projeto p where p.grupo = :grupo order by p.id asc").setParameter("grupo", idGrupo).getResultList();
	}

	
	public void excluir() {
		daoProjeto.excluir(projeto);
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

	public ApplicationPart getImgCapa() {
		return imgCapa;
	}

	public void setImgCapa(ApplicationPart imgCapa) {
		this.imgCapa = imgCapa;
	}

	public ApplicationPart getImgBanner() {
		return imgBanner;
	}

	public void setImgBanner(ApplicationPart imgBanner) {
		this.imgBanner = imgBanner;
	}

}
