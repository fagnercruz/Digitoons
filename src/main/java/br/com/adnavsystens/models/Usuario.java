package br.com.adnavsystens.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import br.com.adnavsystens.models.projeto.Grupo;

@Entity
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "usuario_generator")
	@SequenceGenerator(name = "usuario_generator", allocationSize = 1)
	private Long id;
	@OneToOne(mappedBy = "usuario",cascade = CascadeType.ALL)
	private Login login;
	private String nome;
	private String email;
//	private List<String> permissoes;
	private String caminhoDaImagem;
	@OneToMany(mappedBy = "criadorResponsavel")
	private List<Grupo> grupos;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
//	public List<String> getPermissoes() {
//		return permissoes;
//	}
//	public void setPermissoes(List<String> permissoes) {
//		this.permissoes = permissoes;
//	}
	public String getCaminhoDaImagem() {
		return caminhoDaImagem;
	}
	public void setCaminhoDaImagem(String caminhoDaImagem) {
		this.caminhoDaImagem = caminhoDaImagem;
	}
	public Login getLogin() {
		return login;
	}
	public void setLogin(Login login) {
		this.login = login;
	}
	
	@Override
	public String toString() {
		return this.nome + " - " + this.email;
	}
	

}
