package com.github.funthomas424242.ube.web;

public class LinkDescription {

	private String relation;
	private String href;
	private String verb;
	private String titel;

	public LinkDescription(final String relation, final String href, final String verb,
			final String titel) {
		this.relation = relation;
		this.href = href;
		this.verb = verb;
		this.titel = titel;
	}

	public String getRelation() {
		return relation;
	}

	public String getHref() {
		return href;
	}

	public String getVerb() {
		return verb;
	}

	public String getTitel() {
		return titel;
	}
	
	

}
