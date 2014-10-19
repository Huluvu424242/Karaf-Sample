package com.github.funthomas424242.ube.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ops4j.pax.cdi.api.OsgiService;

import de.inovex.javamagazin.domain.InventoryCategory;
import de.inovex.javamagazin.domain.InventoryItem;
import de.inovex.javamagazin.jpa.InventoryRepository;

@WebServlet(urlPatterns = "/kategorien")
public class ShowCategoryServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	@OsgiService
	InventoryRepository inventoryRepository;

	@Override
	protected void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {

		final List<InventoryCategory> allItems = inventoryRepository
				.getAllCategories();

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<table>");
		// Header
		out.println("<tr>");
		out.println("<th>ID</th><th>Name</th><th>Description</th>");
		out.println("</tr>");

		// table
		for (InventoryCategory inventoryItem : allItems) {
			out.println("<tr>");
			// ID
			out.println("<td>");
			out.println(inventoryItem.getId());
			out.println("</td>");
			// Name
			out.println("<td>");
			out.println(inventoryItem.getCategoryName());
			out.println("</td>");
			// Description
			out.println("<td>");
			out.println(inventoryItem.getCategoryDescription());
			out.println("</td>");

			out.println("</tr>");
		}
		out.println("</table>");
		out.println("</body>");
		out.println("</html>");
	}

}
