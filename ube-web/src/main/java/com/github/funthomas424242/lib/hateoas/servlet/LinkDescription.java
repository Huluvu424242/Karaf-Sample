package com.github.funthomas424242.lib.hateoas.servlet;

public class LinkDescription {

	private String relation;
	private String href;
	private String verb;
	private String titel;

	public LinkDescription(final String relation, final String href,
			final String verb, final String titel) {
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

	@Override
	public String toString() {
		final StringBuffer buf = new StringBuffer();
		buf.append("<");
		buf.append(getHref());
		buf.append(">;rel=\"");
		buf.append(getRelation());
		buf.append("\";title=\"");
		buf.append(getTitel());
		buf.append("\";verb=\"");
		buf.append(getVerb());
		buf.append("\"");
		return buf.toString();
	}

}
