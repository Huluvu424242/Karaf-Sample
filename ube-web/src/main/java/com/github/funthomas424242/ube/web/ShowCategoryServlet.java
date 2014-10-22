package com.github.funthomas424242.ube.web;

import java.io.BufferedReader;
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
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.ops4j.pax.cdi.api.OsgiService;

import de.inovex.javamagazin.domain.InventoryCategory;
import de.inovex.javamagazin.domain.InventoryItem;
import de.inovex.javamagazin.jpa.InventoryRepository;

@WebServlet(urlPatterns = "/kategorien.json")
public class ShowCategoryServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	@OsgiService
	InventoryRepository inventoryRepository;

	@Override
	protected void doPost(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {

//		// Request als JSON Object auslesen
//		final StringBuffer sb = new StringBuffer();
//		try {
//			final BufferedReader reader = request.getReader();
//			String line = null;
//			while ((line = reader.readLine()) != null) {
//				sb.append(line);
//			}
//		} catch (final Exception e) {
//			e.printStackTrace();
//		}
//		final JSONParser parser = new JSONParser();
//		JSONObject joUser = null;
//		try {
//			joUser = (JSONObject) parser.parse(sb.toString());
//		} catch (final ParseException e) {
//			e.printStackTrace();
//		}

		response.setCharacterEncoding("utf8");
		response.setContentType("application/json");
		final PrintWriter writer = response.getWriter();
		
		final JSONObject obj = new JSONObject();
		obj.put("id", 3);
		obj.put("name","horst");
		obj.put("description", "blauer");
		final ArrayList list=new ArrayList();
		list.add(obj);
		writer.print(list);
		writer.flush();
		writer.close();
	}

	private String getContent() {

		final List<InventoryCategory> allItems = inventoryRepository
				.getAllCategories();

		final StringBuffer out = new StringBuffer();
		// out.append("liste=[");
		out.append("[");

		boolean firstRun = true;
		// array
		for (InventoryCategory inventoryItem : allItems) {
			if (!firstRun) {
				out.append(",");
			}
			out.append("{id:'");
			out.append(inventoryItem.getId());
			out.append("',name:'");
			out.append(inventoryItem.getCategoryName());
			out.append("',description:'");
			out.append(inventoryItem.getCategoryDescription());
			out.append("'}");
			firstRun = false;
		}
		out.append("]");

		return out.toString();

	}
}
