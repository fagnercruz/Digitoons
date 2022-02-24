package br.com.adnavsystens.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
/**
 * Classe responsável por padronizar as mensagens do JSF
 * @author Fagner Cruz
 * 
 * */
public abstract class MensagensUtils {
	
	/**
	 * Atribui uma mensagem tipo INFO ao sistema
	 * 
	 * */
	public static void addMensagemSucesso(String titulo, String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, mensagem));
	}
	/**
	 * Atribui uma mensagem tipo ALERTA ao sistema
	 * 
	 * */
	public static void addMensagemAlerta(String titulo, String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, titulo, mensagem));
	}
	/**
	 * Atribui uma mensagem tipo ERRO ao sistema
	 * 
	 * */
	public static void addMensagemErro(String titulo, String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, mensagem));
	}
	/**
	 * Atribui uma mensagem tipo FATAL ao sistema
	 * 
	 * */
	public static void addMensagemFatal(String titulo, String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_FATAL, titulo, mensagem));
	}
}
