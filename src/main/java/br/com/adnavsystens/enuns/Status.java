package br.com.adnavsystens.enuns;

public enum Status {

	CRIADO("Criado"),
	ATIVO("Ativo"),
	EM_ANDAMENTO("Em andamento"),
	PAUSADO("Pausado"),
	SUSPENSO("Suspenso"),
	FINALIZADO("Finalizado"),
	CANCELADO("Cancelado");
	
	private String descricao;
	
	
	Status(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
	
	
	
}
