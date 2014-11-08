package com.github.funthomas424242.lib.hateoas.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HateoasBuilder {

	final List<LinkDescription> linkList = new ArrayList<LinkDescription>();

	final String requestURL;
	final String contextPath;
	final String referrer;
	

	public HateoasBuilder(HttpServletRequest request) {
		this.requestURL = request.getRequestURL().toString();
		this.contextPath = request.getContextPath();
		this.referrer = request.getHeader("referer");
	}

	public LinkDescription addLink(String relation, String href, String verb,
			String title) {
		final LinkDescription linkDescription = new LinkDescription(relation,
				contextPath + href, verb, title);
		linkList.add(linkDescription);
		return linkDescription;
	}

	public LinkDescription addLinkSELF( String verb,
			String title) {
		final LinkDescription linkDescription = new LinkDescription("self",
				requestURL, verb, title);
		linkList.add(linkDescription);
		return linkDescription;
	}
	public LinkDescription addLinkBACK( String verb,
			String title) {
		final LinkDescription linkDescription = new LinkDescription("back",
				referrer, verb, title);
		linkList.add(linkDescription);
		return linkDescription;
	}
	

	public void addLocationHeaderTo(HttpServletResponse response) {
		response.setHeader("location", requestURL);
	}

	public void addLinkHeaderTo(HttpServletResponse response) {
		response.setHeader("link", getLinkListValue());
	}

	public PlaceholderMap createPlaceholderMap() {
		
		final PlaceholderMap placeholderMap = new PlaceholderMap();
		for (LinkDescription link : linkList) {
			final String rel = link.getRelation();
			String key = "{{$" + rel + ".href}}";
			placeholderMap.put(key, link.getHref());
			key = "{{$" + rel + ".rel}}";
			placeholderMap.put(key, link.getRelation());
			key = "{{$" + rel + ".title}}";
			placeholderMap.put(key, link.getTitel());
			key = "{{$" + rel + ".verb}}";
			placeholderMap.put(key, link.getVerb());
		}
		return placeholderMap;
	}

	public StringBuffer buildPageContent(final ServletContext context,
			final PlaceholderMap placeholderMap, final String resourcePath)
			throws IOException {

		final InputStream inStream = context.getResourceAsStream(resourcePath);

		StringBuffer pageContent = new StringBuffer();
		int c = inStream.read();
		while (c != -1) {
			pageContent.append((char) c);
			c = inStream.read();
		}

		final Set<String> keys = placeholderMap.keySet();
		for (String key : keys) {
			pageContent = replacePlaceholder(pageContent, key,
					placeholderMap.get(key));
		}

		return pageContent;

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

	private String getLinkListValue() {
		final StringBuffer buf = new StringBuffer();
		boolean isNextLink = false;
		for (LinkDescription link : linkList) {
			if (isNextLink) {
				buf.append(",");
			}
			buf.append(link.toString() + "\n");
			isNextLink = true;
		}
		return buf.toString();
	}

}
