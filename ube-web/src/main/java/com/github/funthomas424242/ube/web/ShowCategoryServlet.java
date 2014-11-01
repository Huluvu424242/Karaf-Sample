package com.github.funthomas424242.ube.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.ops4j.pax.cdi.api.OsgiService;

import de.inovex.javamagazin.domain.InventoryCategory;
import de.inovex.javamagazin.jpa.InventoryRepository;

@WebServlet(urlPatterns = "/kategorien/auflisten") 
public class ShowCategoryServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	@OsgiService
	InventoryRepository inventoryRepository;

		
	
	@Override
	protected void doHead(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		final JSONObject pageParts = new JSONObject();
		pageParts.put("links",getLinkList());
		
		response.setCharacterEncoding("utf8");
		response.setContentType("application/json");
		final PrintWriter writer = response.getWriter();
		writer.print(pageParts);
		writer.flush();
		writer.close();
	}

	@SuppressWarnings("unchecked")
	private ArrayList<JSONObject> getLinkList() {

		final List<LinkDescription> allLinks=new ArrayList<LinkDescription>();
		allLinks.add(new LinkDescription("self","http://test.org","GET","Aktuelle Seite"));
		allLinks.add(new LinkDescription("next","http://test1.org","GET","Nächste Seite"));
		allLinks.add(new LinkDescription("all","http://list.org","GET","Kategorienliste"));
		allLinks.add(new LinkDescription("new","http://test.org","POST","Neue Kategorie"));
		
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

		// // Request als JSON Object auslesen
		// final StringBuffer sb = new StringBuffer();
		// try {
		// final BufferedReader reader = request.getReader();
		// String line = null;
		// while ((line = reader.readLine()) != null) {
		// sb.append(line);
		// }
		// } catch (final Exception e) {
		// e.printStackTrace();
		// }
		// final JSONParser parser = new JSONParser();
		// JSONObject joUser = null;
		// try {
		// joUser = (JSONObject) parser.parse(sb.toString());
		// } catch (final ParseException e) {
		// e.printStackTrace();
		// }

		response.setCharacterEncoding("utf8");
		response.setContentType("application/json");
		final PrintWriter writer = response.getWriter();
		writer.print(getContent());
		writer.flush();
		writer.close();
	}

	private ArrayList<JSONObject> getContent() {

		final List<InventoryCategory> geleseneKategorien = inventoryRepository
				.getAllCategories();

		final ArrayList<JSONObject> kategorien = konvertiereKategorienInJSON(geleseneKategorien);
		return kategorien;
	}

	@SuppressWarnings("unchecked")
	private ArrayList<JSONObject> konvertiereKategorienInJSON(
			final List<InventoryCategory> allItems) {
		
		final ArrayList<JSONObject> kategorien = new ArrayList<JSONObject>();
		for (InventoryCategory item : allItems) {
			final JSONObject obj = new JSONObject();
			obj.put("id", item.getId());
			obj.put("name", item.getCategoryName());
			obj.put("description", item.getCategoryDescription());
			kategorien.add(obj);
		}
		return kategorien;
	}

	

}
