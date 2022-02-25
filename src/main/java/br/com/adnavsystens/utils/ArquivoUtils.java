package br.com.adnavsystens.utils;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.catalina.core.ApplicationPart;

import br.com.adnavsystens.enuns.FilePaths;

/**
 * Classe utilitária manipulação de arquivos
 * 
 * @author Fagner Cruz
 * 
 */
public abstract class ArquivoUtils {

	/**
	 * Método que salva uma imagem no disco e retorna o caminho absoluto do mesmo ou
	 * null em caso de erro
	 * 
	 * @param
	 * @throws Exception 
	 * 
	 */
	public static String salvarArquivo(ApplicationPart arquivo, FilePaths path) throws Exception {

		StringBuilder caminho = new StringBuilder();
		caminho.append(FilePaths.ROOT_PATH.getCaminho());
		caminho.append(path.getCaminho());

		File f = new File(caminho.toString());

		// Montando a estrutura de pastas caso não exista
		if (criaDiretorios(f, caminho)) {
			// continua o processo
			if (arquivo != null && arquivo.getSubmittedFileName() != null) {
				try {
					//verifica a existencia do arquivo e renomeia se necessario
					String nomeAtualizado = verificaNomeDoArquivo(caminho.toString(), arquivo.getSubmittedFileName());
					
					// completa o path
					caminho.append(nomeAtualizado);

					// transforma o arquivo em um array de bytes
					byte[] arrayBytes = new byte[(int) arquivo.getSize()];
					arquivo.getInputStream().read(arrayBytes);

					// Cria um link para o arquivo no disco
					f = new File(caminho.toString());
					
				

					// Cria o FOS para gravar o array no arquivo
					FileOutputStream fos = new FileOutputStream(f);

					// Grava o array no fos (e consequentemente no arquivo setado no file)
					fos.write(arrayBytes);
					fos.close();
					
					return caminho.toString();

				} catch (Exception e) {
					throw new Exception(e.getMessage());
				}

			} else {
				// aborta o processo
				throw new Exception("O arquivo ou seu nome são inválidos no sistema.");
			}
		}
		throw new Exception("Falha ao tentar criar diretórios de destino");
	}

		
	private static boolean criaDiretorios(File f, StringBuilder caminho) {
		if (!f.exists()) {
			try {
				f.mkdirs();
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}
	
	private static String verificaNomeDoArquivo(String path, String nomeArquivo) throws Exception {
		
		String nomeAtualizado = nomeArquivo;
		File arquivo = new File(path + nomeArquivo);
		
		//testa a principio e o nome do arquivo é valido
		if(nomeAtualizado.split("\\.").length > 2) {
			throw new Exception("O nome do arquivo é inválido: Dois ou mais '.'");
		}
		
		int i = 1;
		while(arquivo.exists()) {
			nomeAtualizado = renameFile(nomeAtualizado, i);
			arquivo = new File(path + nomeAtualizado);
			i++;
		}
		
		return nomeAtualizado;
	}
	
	private static String renameFile(String nomeArquivo, int i) throws Exception {
		String[] arqName = nomeArquivo.split("\\.");
		
		// TODO analisar se esse trecho abaixo é necessário pois já existe essa mesma validação dentro de verificaNomeDoArquivo()
//		if(arqName.length > 2) {
//			throw new Exception("O nome do arquivo é inválido: Dois ou mais '.'");
//		}
		
		//limpa possiveis renomeações anteriores para atualizar novamente
		if(arqName.length == 2) {
			// OK
			if(arqName[0].endsWith(")")) {
				String[] nomeOriginal = arqName[0].split("\\(");
				arqName[0] = nomeOriginal[0];
			}
		}
		
		String nome = arqName[0] + "(" + i + ")";
		String extensao = "." + arqName[arqName.length - 1]; // previne pegar a extensao corretamente mesmo q o nome tenha pontos
		
		return nome + extensao;
	}
	
	public static boolean excluirArquivo(String path) {
		File f = new File(path);
		if(f.exists()) {
			if(f.delete()) {
				return true;
			}
		}
		return false;
	}
	
	//TODO Criar método de redimensionar imagem e de salvar tamanhos diferentes de imagens
}
