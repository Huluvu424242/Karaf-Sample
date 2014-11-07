package com.github.funthomas424242.ube.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.funthomas424242.lib.hateoas.servlet.LinkDescription;
import com.github.funthomas424242.lib.hateoas.servlet.LinkList;
import com.github.funthomas424242.lib.hateoas.servlet.NavigationServlet;

@WebServlet(urlPatterns = "/items/auflisten")
public class ShowItemsServlet extends NavigationServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Override
	protected void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {

		

		response.setCharacterEncoding("utf8");
		response.setContentType("text/html");
		
		
		final LinkList linkList=new LinkList(request);
		linkList.addLinkRequestURL("self", "GET", "Neu laden" );
		linkList.addLink("back","/index.html", "GET", "Zurueck" );
		linkList.addLink("next","/kategorien/auflisten", "GET", "Kategorien auflisten" );
	
		linkList.addLocationHeaderTo(response);
		linkList.addLinkHeaderTo(response);
		

		final PrintWriter writer = response.getWriter();

		final ServletContext context = getServletContext();

		final InputStream inStream = context
				.getResourceAsStream("/items/itemlist.html");
		int c = inStream.read();
		while (c != -1) {
			writer.print((char) c);
			c = inStream.read();
		}
		writer.flush();
		writer.close();
	}

}
