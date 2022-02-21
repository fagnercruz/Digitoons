package br.com.adnavsystens.managebeans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

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
			if(!GenericDAO.isTransacaoAtiva()) {
				manager.getTransaction().begin();
			}
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
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login inválido:", e.getLocalizedMessage()));
			if(manager.getTransaction().isActive()) {
				manager.close();
			}
			return "";
		} 

		manager.getTransaction().commit();

		
		return paginaRequisitada != null ? paginaRequisitada:"index?faces-redirect=true";
	}
	
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuarioLogado");
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
//		String context = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
		if(GenericDAO.isTransacaoAtiva()) {
			daoLogin.getEntityManager().close();
		}
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
