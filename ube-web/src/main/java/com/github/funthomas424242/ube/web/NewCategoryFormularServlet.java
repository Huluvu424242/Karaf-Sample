package com.github.funthomas424242.ube.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ops4j.pax.cdi.api.OsgiService;

import com.github.funthomas424242.lib.hateoas.servlet.HateoasBuilder;
import com.github.funthomas424242.lib.hateoas.servlet.HttpMethod;
import com.github.funthomas424242.lib.hateoas.servlet.NavigationServlet;
import com.github.funthomas424242.lib.hateoas.servlet.PlaceholderMap;

import de.inovex.javamagazin.jpa.InventoryRepository;

@WebServlet(urlPatterns = "/kategorien/new")
public class NewCategoryFormularServlet extends NavigationServlet {

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

		// Request auswerten
		final String categoryName = request.getParameter("category.name");
		final String categoryDescription = request
				.getParameter("category.description");

		inventoryRepository.addCategory(categoryName, categoryDescription);

		// Response erstellen
		befuelleResponse(request, response);
	}

	@Override
	protected void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {

		// Response erstellen
		befuelleResponse(request, response);

	}

	private void befuelleResponse(final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {
		final HateoasBuilder hateoasBuilder = new HateoasBuilder(request);
		hateoasBuilder.addLinkSELF(HttpMethod.GET, "Neu laden");
		hateoasBuilder.addLinkBACK(HttpMethod.GET, "Zur&uuml;ck");
		hateoasBuilder.addLink("next", "/items/auflisten", HttpMethod.GET,
				"Items auflisten");
		hateoasBuilder.addLink("home", "/index.html", HttpMethod.GET,
				"Startseite");
		hateoasBuilder.addLink("add", "/kategorien/new", HttpMethod.POST,
				"Kategorie hinzufügen");
		hateoasBuilder.addLink("new", "/kategorien/new", HttpMethod.GET,
				"Neue Kategorie erfassen");
		final PlaceholderMap placeholderMap = hateoasBuilder
				.createPlaceholderMap();
		final StringBuffer pageContent = hateoasBuilder.buildPageContent(
				getServletContext(), placeholderMap, "/kategorien/new.html");

		response.setCharacterEncoding("utf8");
		response.setContentType("text/html");
		hateoasBuilder.addLocationHeaderTo(response);
		hateoasBuilder.addLinkHeaderTo(response);

		final PrintWriter writer = response.getWriter();
		writer.print(pageContent.toString());
		writer.flush();
		writer.close();
	}

}
