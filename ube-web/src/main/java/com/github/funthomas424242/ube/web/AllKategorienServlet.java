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

@WebServlet(urlPatterns = "/kategorien/all")
public class AllKategorienServlet extends HttpServlet {

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

		final List<InventoryCategory> geleseneKategorien = inventoryRepository
				.getAllCategories();

		final ArrayList<JSONObject> kategorien = konvertiereKategorienInJSON(geleseneKategorien);
		return kategorien;
	}

	@SuppressWarnings("unchecked")
	private ArrayList<JSONObject> konvertiereKategorienInJSON(
			final List<InventoryCategory> allItems) {
		
		final ArrayList<JSONObject> kategorien = new ArrayList<JSONObject>();
		for (InventoryCategory category : allItems) {
			final JSONObject obj = new JSONObject();
			obj.put("id", category.getId());
			obj.put("name", category.getCategoryName());
			obj.put("description", category.getCategoryDescription());
			kategorien.add(obj);
		}
		return kategorien;
	}

	
}
