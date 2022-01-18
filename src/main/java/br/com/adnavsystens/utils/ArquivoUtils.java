package br.com.adnavsystens.utils;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.catalina.core.ApplicationPart;

/**
 * Classe utilitária para gravação e recuparação de arquivos
 * 
 * @author Fagner Cruz
 * 
 */
public abstract class ArquivoUtils {

	/**
	 * Método que salva uma imagem no disco e retorna o caminho absoluto do mesmo ou
	 * null em caso de erro
	 * 
	 * @param caminhoPasta
	 * @param arquivo
	 * 
	 * */
	public static String armazenaImagem(String caminhoPasta, ApplicationPart arquivo) {

		StringBuilder caminho = new StringBuilder();
		caminho.append(caminhoPasta);
		if (arquivo != null && arquivo.getSubmittedFileName() != null) {
			try {
				// completa o path
				caminho.append(arquivo.getSubmittedFileName());

				// transforma o arquivo em um array de bytes
				byte[] arrayBytes = new byte[(int) arquivo.getSize()];
				arquivo.getInputStream().read(arrayBytes);

				// Cria um link para o arquivo no disco
				File f = new File(caminho.toString());

				// Cria o FOS para gravar o array no arquivo
				FileOutputStream fos = new FileOutputStream(f);

				// Grava o array no fos (e consequentemente no arquivo setado no file)
				fos.write(arrayBytes);
				fos.close();

			} catch (Exception e) {
				e.printStackTrace();
				return null;
			} 
		}
		return caminho.toString();

	}// armazenaImagem()

}
