package br.com.adnavsystens.utils;

/**
 *  Servlet para obter  uma imagem do disco e jogá-la na saída do response
 * 
 * */


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.adnavsystens.connection.GenericDAO;
import br.com.adnavsystens.models.UsuarioArquivoModel;

@WebServlet("/LoadImageServlet")
public class LoadImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private GenericDAO<UsuarioArquivoModel> daoPessoa = new GenericDAO<UsuarioArquivoModel>();

	public LoadImageServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!request.getParameter("id").isBlank()) {
			Long id = Long.valueOf(request.getParameter("id"));
			UsuarioArquivoModel pessoa = new UsuarioArquivoModel();
			pessoa.setId(id);
			pessoa = (UsuarioArquivoModel) daoPessoa.pesquisar(pessoa);
			
			if(pessoa != null && !pessoa.getCaminhoDaImagem().isBlank()) {
				String caminho =  pessoa.getCaminhoDaImagem();
				File f = new File(caminho);
				FileInputStream fis = new FileInputStream(f);
				byte[] array = new byte[(int) f.length()];
				fis.read(array);
				fis.close();
				
				response.getOutputStream().write(array);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
