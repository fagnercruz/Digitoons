package br.com.adnavsystens.enuns;

public enum Status {

	CRIADO("Criado","badge badge-primary"),
	ATIVO("Ativo","badge badge-info"),
	EM_ANDAMENTO("Em andamento","badge badge-success"),
	PAUSADO("Pausado","badge badge-secondary"),
	SUSPENSO("Suspenso","badge badge-warning"),
	FINALIZADO("Finalizado","badge badge-light"),
	CANCELADO("Cancelado","badge badge-danger");
	
	private String descricao, classeBootstrap;
	
	
	Status(String descricao, String bootStrapClass) {
		this.descricao = descricao;
		this.classeBootstrap = bootStrapClass;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public String getClasseBootstrap() {
		return classeBootstrap;
	}
	
	
	
}
