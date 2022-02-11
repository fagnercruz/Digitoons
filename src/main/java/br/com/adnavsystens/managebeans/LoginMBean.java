package br.com.adnavsystens.managebeans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import antlr.debug.MessageAdapter;
import br.com.adnavsystens.connection.GenericDAO;
import br.com.adnavsystens.models.Login;
import br.com.adnavsystens.models.Usuario;

@ManagedBean
public class LoginMBean {

	private GenericDAO<Login> daoLogin = new GenericDAO<Login>();
	private Login login = new Login();
	private String paginaRequisitada = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("urlDestino");

	public String logar() {
		
		EntityManager manager = daoLogin.getEntityManager();
		
		try {
			
			manager.getTransaction().begin();
			Login loginRetornado = (Login) manager.createQuery("from " + login.getClass().getSimpleName() +" where username = :usuario and password = :senha" )
				.setParameter("usuario", login.getUsername())
				.setParameter("senha", login.getPassword())
				.getSingleResult();
			
			
			if(loginRetornado != null) {
				Usuario usuario = loginRetornado.getUsuario();
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogado", usuario);
			}
			
			
		} catch (Exception e) {
			manager.getTransaction().commit();
			System.err.println(e.getMessage());
			login = new Login();
			FacesMessage mensagem = new FacesMessage("Login Inválido");
			mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
			mensagem.setDetail(e.getLocalizedMessage());
			FacesContext.getCurrentInstance().addMessage(null, mensagem);
			return "";
		}
		manager.getTransaction().commit();

		
		return paginaRequisitada != null ? paginaRequisitada:"index?faces-redirect=true";
	}
	
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuarioLogado");
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
//		String context = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
		return "/index?faces-redirect=true";
		
	}

	public GenericDAO<Login> getDaoLogin() {
		return daoLogin;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}
}
