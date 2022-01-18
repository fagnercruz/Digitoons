package br.com.adnavsystens.models.projeto;

import br.com.adnavsystens.models.Usuario;

public class Grupo {

	private Long id;
	private Usuario criadorResponsavel;
	private String nome;
	private String slogan;
	private String imagemLogo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Usuario getCriadorResponsavel() {
		return criadorResponsavel;
	}
	public void setCriadorResponsavel(Usuario criadorResponsavel) {
		this.criadorResponsavel = criadorResponsavel;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSlogan() {
		return slogan;
	}
	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}
	public String getImagemLogo() {
		return imagemLogo;
	}
	public void setImagemLogo(String imagemLogo) {
		this.imagemLogo = imagemLogo;
	}
	
	
}
