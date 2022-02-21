package br.com.adnavsystens.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public abstract class MensagensUtils {

	public static void addMensagemSucesso(String titulo, String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, mensagem));
	}
	
	public static void addMensagemAlerta(String titulo, String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, titulo, mensagem));
	}
	
	public static void addMensagemErro(String titulo, String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, mensagem));
	}
	
	public static void addMensagemFatal(String titulo, String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_FATAL, titulo, mensagem));
	}
}
