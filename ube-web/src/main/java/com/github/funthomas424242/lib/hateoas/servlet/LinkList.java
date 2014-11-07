package com.github.funthomas424242.lib.hateoas.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LinkList {

	final List<LinkDescription> linkList = new ArrayList<LinkDescription>();

	final String requestURL;
	final String contextPath;

	public LinkList(HttpServletRequest request) {
		this.requestURL = request.getRequestURL().toString();
		this.contextPath=request.getContextPath();
	}

	public LinkDescription addLink(String relation, String href, String verb,
			String title) {
		final LinkDescription linkDescription = new LinkDescription(relation,
				contextPath+href, verb, title);
		linkList.add(linkDescription);
		return linkDescription;
	}

	public void addLinkRequestURL(String relation, String verb, String title) {
		this.addLink(relation, requestURL, verb, title);
	}

	public void addLocationHeaderTo(HttpServletResponse response) {
		response.setHeader("location", requestURL);
	}

	public void addLinkHeaderTo(HttpServletResponse response) {
		response.setHeader("link", getLinkListValue());
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
