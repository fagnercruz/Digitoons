package br.com.adnavsystens.models.projeto;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import br.com.adnavsystens.models.Usuario;

@Entity
public class Grupo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "grupo_seq")
	@SequenceGenerator(name = "grupo_seq", allocationSize = 1)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario criadorResponsavel;
	private String nome;
	private String slogan;
	private String imagemLogo;
	@OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL)
	private List<Projeto> projetos;
	
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
	
	public List<Projeto> getProjetos() {
		return projetos;
	}
	public void setProjetos(List<Projeto> projetos) {
		this.projetos = projetos;
	}
	@Override
	public String toString() {
		return nome + " - " + slogan;
	}
}
