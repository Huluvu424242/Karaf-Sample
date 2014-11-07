package com.github.funthomas424242.ube.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

		final LinkList linkList = new LinkList(request);
		linkList.addLinkRequestURL("self", "GET", "Neu laden");
		linkList.addLink("back", "/index.html", "GET", "Zurueck");
		linkList.addLink("next", "/kategorien/auflisten", "GET",
				"Kategorien auflisten");

		linkList.addLocationHeaderTo(response);
		linkList.addLinkHeaderTo(response);
		final HashMap<String, String> placeholderMap = linkList
				.generatePlaceholderMap();

		final ServletContext context = getServletContext();
		final InputStream inStream = context
				.getResourceAsStream("/items/itemlist.html");

		StringBuffer pageContent = new StringBuffer();
		int c = inStream.read();
		while (c != -1) {
			pageContent.append((char) c);
			c = inStream.read();
		}

		final Set<String>keys=placeholderMap.keySet();
		for(String key:keys){
			pageContent= replacePlaceholder(pageContent,key,placeholderMap.get(key));
		}
		

		final PrintWriter writer = response.getWriter();
		writer.print(pageContent.toString());
		writer.flush();
		writer.close();
	}

	private StringBuffer replacePlaceholder(final StringBuffer pageContent,
			final String key, final String replacement) {
		
		final String quotedKey = Pattern.quote(key);
		final Pattern pattern = Pattern.compile(quotedKey);
		final Matcher matcher = pattern.matcher(pageContent);
		final String quotedReplacement = Matcher.quoteReplacement(replacement);
		final StringBuffer buf = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(buf, quotedReplacement);
		}
		matcher.appendTail(buf);
		return buf;
	}

}
