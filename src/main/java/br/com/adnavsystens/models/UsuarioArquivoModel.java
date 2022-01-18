package br.com.adnavsystens.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;


@Entity
public class UsuarioArquivoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id_teste_arq")
	@SequenceGenerator(name = "id_teste_arq", initialValue = 1, allocationSize = 1)
	private Long id;
	private String nome;
	private String caminhoDaImagem;
	
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
	public String getCaminhoDaImagem() {
		return caminhoDaImagem;
	}
	public void setCaminhoDaImagem(String caminhoDaImagem) {
		this.caminhoDaImagem = caminhoDaImagem;
	}
	
	
}
