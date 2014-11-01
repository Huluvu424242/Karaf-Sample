package com.github.funthomas424242.ube.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.ops4j.pax.cdi.api.OsgiService;

import de.inovex.javamagazin.domain.InventoryItem;
import de.inovex.javamagazin.jpa.InventoryRepository;

@WebServlet(urlPatterns = "/items/auflisten")
public class ShowItemsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	@OsgiService
	InventoryRepository inventoryRepository;

	@Override
	protected void doHead(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		final JSONObject pageParts = new JSONObject();
		pageParts.put("links", getLinkList());

		response.setCharacterEncoding("utf8");
		response.setContentType("application/json");
		final PrintWriter writer = response.getWriter();
		writer.print(pageParts);
		writer.flush();
		writer.close();
	}

	@SuppressWarnings("unchecked")
	private ArrayList<JSONObject> getLinkList() {

		final List<LinkDescription> allLinks = new ArrayList<LinkDescription>();
		allLinks.add(new LinkDescription("self", "http://test.org", "GET",
				"Aktuelle Seite"));
		allLinks.add(new LinkDescription("next", "http://test1.org", "GET",
				"Nächste Seite"));
		allLinks.add(new LinkDescription("all", "http://list.org", "GET",
				"Kategorienliste"));
		allLinks.add(new LinkDescription("new", "http://test.org", "POST",
				"Neue Kategorie"));

		final ArrayList<JSONObject> linkListe = new ArrayList<JSONObject>();
		for (LinkDescription linkDescription : allLinks) {
			final JSONObject obj = new JSONObject();
			obj.put("rel", linkDescription.getRelation());
			obj.put("href", linkDescription.getHref());
			obj.put("verb", linkDescription.getVerb());
			obj.put("title", linkDescription.getTitel());
			linkListe.add(obj);
		}
		return linkListe;
	}

	@Override
	protected void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {

		List<InventoryItem> allItems = inventoryRepository.getAllItems();

		response.setCharacterEncoding("utf8");
		response.setContentType("text/html");
		response.setHeader("location",
				"http://localhost:8181/ube/items/auflisten");
		response.setHeader(
				"link",
				"<http://localhost:8181/ube/index.html>;rel=\"next\";title=\"Homepage\"");

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
