package br.com.adnavsystens.models.projeto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import br.com.adnavsystens.models.Usuario;

//@Entity
public class Projeto {

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "proj_generator")
//	@SequenceGenerator(name = "proj_generator", allocationSize = 1)
	private Long id;
	private String titulo;
	private String resumo;
	private String descricao;
	private String autor;
	private String publisher;
	private String imagemCapa;
	private String imagemBanner;
	
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
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
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
	
	
	
}
