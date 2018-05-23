/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import bean.sesionUBean;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author garo1
 */
public class FiltroWeb implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	@SuppressWarnings("empty-statement")
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		sesionUBean session = (sesionUBean) ((HttpServletRequest) request).getSession().getAttribute("sesionUBean");
		String url = ((HttpServletRequest) request).getRequestURI();
		String contextPath = ((HttpServletRequest) request).getContextPath();
		String[] params = url.split("/");
		String pagina = (params.length > 3 ? (params[3] == null ? "" : params[3].trim()) : "");

		if (session == null || !session.isLogged()) {
			if (url.toUpperCase().endsWith("XHTML")) {//SI NO ESTA LOGGEADO SOLO PUEDE VER INDEX Y MODERADOR

				if (!pagina.toUpperCase().equals("INDEX.XHTML") && !pagina.toUpperCase().equals("MODERADOR.XHTML") && !pagina.toUpperCase().equals("FORBIDDEN.XHTML")) {
					((HttpServletResponse) response).sendRedirect(contextPath + "/web/forbidden.xhtml");
				} else {
					chain.doFilter(request, response);
				}
			} else {
				chain.doFilter(request, response);
			};
		}
		else{
		 if(url.toUpperCase().endsWith("XHTML")){//SI NO ESTA LOGGEADO SOLO PUEDE VER INDEX Y MODERADOR
		 if(!session.accesoXRol(pagina)){
		 ((HttpServletResponse)response).sendRedirect(contextPath + "/web/forbidden.xhtml");
		 }else{
		 chain.doFilter(request, response);
		 }
		 }else{
		 chain.doFilter(request, response);
		 };
		 }
		 
	}

	@Override
	public void destroy() {

	}

}
