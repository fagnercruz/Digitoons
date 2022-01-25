package br.com.adnavsystens.enuns;

public enum Status {

	CRIADO("criado"),
	ATIVO("ativo"),
	PAUSADO("pausado"),
	SUSPENSO("suspenso"),
	FINALIZADO("finalizado"),
	CANCELADO("cancelado");
	
	private String descricao;
	
	
	Status(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
	
	
	
}
