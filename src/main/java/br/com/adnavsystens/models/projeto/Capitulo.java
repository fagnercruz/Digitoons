package br.com.adnavsystens.models.projeto;

import java.util.List;

import javax.persistence.OneToMany;

public class Capitulo {

	private Long id;
	private String tituloCapitulo;
	private int numCapitulo;
//	private List<String> paginas;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTituloCapitulo() {
		return tituloCapitulo;
	}
	public void setTituloCapitulo(String tituloCapitulo) {
		this.tituloCapitulo = tituloCapitulo;
	}
	public int getNumCapitulo() {
		return numCapitulo;
	}
	public void setNumCapitulo(int numCapitulo) {
		this.numCapitulo = numCapitulo;
	}
	
	
}
