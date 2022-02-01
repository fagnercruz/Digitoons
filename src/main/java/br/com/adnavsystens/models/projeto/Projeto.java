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

import br.com.adnavsystens.enuns.Status;


@Entity
public class Projeto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "proj_generator")
	@SequenceGenerator(name = "proj_generator", allocationSize = 1)
	private Long id;
	private String titulo;
	private String resumo;
	private String descricao;
	private String autor;
	@ManyToOne()
	@JoinColumn(name = "grupo_id")
	private Grupo grupo;
	private String imagemCapa;
	private String imagemBanner;
	private Status status;
	@OneToMany(mappedBy = "projeto", cascade = CascadeType.ALL)
	private List<Capitulo> capitulos;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getResumo() {
		return resumo;
	}
	public void setResumo(String resumo) {
		this.resumo = resumo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public Grupo getGrupo() {
		return grupo;
	}
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
	public String getImagemCapa() {
		return imagemCapa;
	}
	public void setImagemCapa(String imagemCapa) {
		this.imagemCapa = imagemCapa;
	}
	public String getImagemBanner() {
		return imagemBanner;
	}
	public void setImagemBanner(String imagemBanner) {
		this.imagemBanner = imagemBanner;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public List<Capitulo> getCapitulos() {
		return capitulos;
	}
	public void setCapitulos(List<Capitulo> capitulos) {
		this.capitulos = capitulos;
	}
	
	
	
}
