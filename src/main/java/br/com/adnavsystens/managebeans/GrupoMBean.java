package br.com.adnavsystens.managebeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import org.apache.catalina.core.ApplicationPart;

import br.com.adnavsystens.connection.GenericDAO;
import br.com.adnavsystens.enuns.FilePaths;
import br.com.adnavsystens.models.Usuario;
import br.com.adnavsystens.models.projeto.Grupo;
import br.com.adnavsystens.models.projeto.Projeto;
import br.com.adnavsystens.utils.ArquivoUtils;
import br.com.adnavsystens.utils.MensagensUtils;


@ManagedBean
@ViewScoped
public class GrupoMBean {
	

	private Grupo grupo = new Grupo();
	private GenericDAO<Grupo> daoGrupo = new GenericDAO<Grupo>();
	private List<Grupo> grupos = new ArrayList<Grupo>();
	private ApplicationPart imagemGrupo;
	
	/* Atribui ao usuário da sessão a propriedade do grupo criado */
	private Usuario usuarioLogado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");
	private Long idGrupo;
	
	
		
	public String salvar() {
		grupo.setCriadorResponsavel(usuarioLogado);

		//teste se for um novo cadastro ou update
		if(grupo.getId() == null) {
			//cadastro
			try {
				grupo.setImagemLogo(ArquivoUtils.salvarArquivo(imagemGrupo, FilePaths.GRUPO));
				daoGrupo.salvar(grupo);
				MensagensUtils.addMensagemSucesso("Sucesso", "Grupo salvo");
			} catch (Exception e) {
				MensagensUtils.addMensagemErro("Erro", e.getLocalizedMessage());
			}
		} else {
			// update
			// verifica se tem upload de arquivo
			if(imagemGrupo != null) {
				//se TEM ARQUIVO UPADO, verifica se ja existe antes para remover e adiciona a nova
				Grupo aux = new Grupo();
				aux.setId(grupo.getId());
				aux = daoGrupo.pesquisar(aux);
				if(aux.getImagemLogo() != null) {
					ArquivoUtils.excluirArquivo(aux.getImagemLogo());
				}
				try {
					grupo.setImagemLogo(ArquivoUtils.salvarArquivo(imagemGrupo, FilePaths.GRUPO));
					daoGrupo.salvar(grupo);
					MensagensUtils.addMensagemSucesso("Sucesso", "Os dados foram atualizados.");
				} catch (Exception e) {
					MensagensUtils.addMensagemErro("Erro", e.getLocalizedMessage());
				}
			} else {
				// se NAO TEM ARQUIVO UPADO, resgasta a imagem ja existente para não perder a referencia dela
				Grupo aux = new Grupo();
				aux.setId(grupo.getId());
				aux = daoGrupo.pesquisar(aux);
				grupo.setImagemLogo(aux.getImagemLogo());
				try {
					daoGrupo.salvar(grupo);
					MensagensUtils.addMensagemSucesso("Sucesso", "Os dados foram atualizados.");
				} catch (Exception e) {
					MensagensUtils.addMensagemErro("Erro", e.getLocalizedMessage());
				}
			}
		}
		grupo = new Grupo();
		listarGruposUsuarioLogado();
		return "";
	}

	public void carregarDetalhes() {
		Grupo auxGp = new Grupo();
		auxGp.setId(idGrupo);
		grupo = daoGrupo.pesquisar(auxGp);
		List<Projeto> lista = listarProjetosDoGrupo(idGrupo);
		grupo.setProjetos(lista);
	}
	
	public String editarGrupo() {
		Grupo aux = new Grupo();
		aux.setId(Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idGrupo")));
		grupo = daoGrupo.pesquisar(aux);
		return "";
	}
	
	public String excluirGrupo() {
		Grupo aux = new Grupo();
		aux.setId(Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idGrupo")));
		aux = daoGrupo.pesquisar(aux);
		if(aux.getImagemLogo() != null) {
			ArquivoUtils.excluirArquivo(aux.getImagemLogo());
		}
		aux = daoGrupo.excluir(aux);
		if(aux != null) {
			MensagensUtils.addMensagemAlerta("Atenção", "O grupo " + aux.getNome() +" foi excluído com sucesso");
		} else {
			MensagensUtils.addMensagemErro("Erro", "Não foi possível excluir o grupo");
		}
		listarGruposUsuarioLogado();
		return "";
	}
	
	
	/* esse metodo faz mais sentido estando em usuario */
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void listarGruposUsuarioLogado() {
		if(usuarioLogado != null) {
			EntityManager manager = daoGrupo.getEntityManager();
			
			grupos = (List<Grupo>) manager
					.createQuery("from Grupo g where g.criadorResponsavel = :idUsuario order by g.id asc")
					.setParameter("idUsuario", usuarioLogado)
					.getResultList();
		}
	}

	/** 
	 * gambiarra para poder atualizar a lista de projetos após novos cadastros
	 * pois o Hibernate não está fazendo bem o serviço dele.
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

	public ApplicationPart getImagemGrupo() {
		return imagemGrupo;
	}

	public void setImagemGrupo(ApplicationPart imagemGrupo) {
		this.imagemGrupo = imagemGrupo;
	}

	

}
