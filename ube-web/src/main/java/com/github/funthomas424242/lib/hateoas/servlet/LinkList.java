package com.github.funthomas424242.lib.hateoas.servlet;

import java.util.ArrayList;
import java.util.HashMap;
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

	public LinkDescription addLinkRequestURL(String relation, String verb, String title) {
		final LinkDescription linkDescription = new LinkDescription(relation,
				requestURL, verb, title);
		linkList.add(linkDescription);
		return linkDescription;
	}

	public void addLocationHeaderTo(HttpServletResponse response) {
		response.setHeader("location", requestURL);
	}

	public void addLinkHeaderTo(HttpServletResponse response) {
		response.setHeader("link", getLinkListValue());
	}

	public HashMap<String,String> generatePlaceholderMap() {
		final HashMap<String,String> placeholderMap = new HashMap<String,String>();
		for(LinkDescription link:linkList){
			final String rel=link.getRelation();
			String key="{{$"+rel+".href}}";
			placeholderMap.put(key, link.getHref());
			key="{{$"+rel+".rel}}";
			placeholderMap.put(key, link.getRelation());
			key="{{$"+rel+".title}}";
			placeholderMap.put(key, link.getTitel());
			key="{{$"+rel+".verb}}";
			placeholderMap.put(key, link.getVerb());
		}
		return placeholderMap;
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
