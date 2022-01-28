package br.com.adnavsystens.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.adnavsystens.connection.HibernateUtils;



@WebFilter(urlPatterns = {"/*"})
public class AuthenticatorFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String paginaRequisitada = httpRequest.getServletPath();
		
		boolean interceptar = true;
		
		
		if(interceptar && !paginaRequisitada.contains("/javax.faces.resource")) {
			//System.out.println(paginaRequisitada + " interceptada." );
			HttpSession session = httpRequest.getSession();
			
			if(session.getAttribute("usuarioLogado") == null && !(paginaRequisitada.contains("/login.") || paginaRequisitada.contains("/index."))) {
				session.setAttribute("urlDestino", paginaRequisitada);
				RequestDispatcher dispatcher = null;
				if(paginaRequisitada.contains("/admin")) {
					dispatcher = request.getRequestDispatcher("/login.jsf");
				} else {
					dispatcher = request.getRequestDispatcher("login.jsf");
				}
				dispatcher.forward(request, response);
			}
		} // fim do if-interceptar
		
		chain.doFilter(request, response);
	} // doFilter
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		HibernateUtils.getEntityManager();
	}

}
