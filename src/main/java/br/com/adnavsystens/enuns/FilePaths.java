package br.com.adnavsystens.enuns;

public enum FilePaths {

	ROOT_PATH("C:\\digitoons\\"),
	USUARIO("files\\user\\"),
	GRUPO("files\\grupo\\"),
	PROJETO("files\\projetos"),
	CAPITULO("files\\capitulos");
	
	private String caminho;
	
	FilePaths(String caminho) {
		this.caminho = caminho;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	
	
}
