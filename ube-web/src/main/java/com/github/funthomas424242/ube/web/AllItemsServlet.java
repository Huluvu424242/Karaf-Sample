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

import de.inovex.javamagazin.domain.InventoryItem;
import de.inovex.javamagazin.jpa.InventoryRepository;

@WebServlet(urlPatterns = "/items/all")
public class AllItemsServlet extends HttpServlet {

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

		response.setCharacterEncoding("utf8");
		response.setContentType("application/json");
		final PrintWriter writer = response.getWriter();
		writer.print(getContent());
		writer.flush();
		writer.close();
	}

	
	private ArrayList<JSONObject> getContent() {

		final List<InventoryItem> geleseneItems = inventoryRepository
				.getAllItems();

		final ArrayList<JSONObject> items = konvertiereItemsInJSON(geleseneItems);
		return items;
	}

	@SuppressWarnings("unchecked")
	private ArrayList<JSONObject> konvertiereItemsInJSON(
			final List<InventoryItem> allItems) {
		
		final ArrayList<JSONObject> items = new ArrayList<JSONObject>();
		for (InventoryItem item : allItems) {
			final JSONObject obj = new JSONObject();
			obj.put("id", item.getId());
			obj.put("name", item.getItemName());
			obj.put("description", item.getItemDescription());
			obj.put("price", item.getItemPrice());
			items.add(obj);
		}
		return items;
	}

	
}
