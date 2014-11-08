package com.github.funthomas424242.lib.hateoas.servlet;

import java.util.HashMap;
import java.util.Set;

public class PlaceholderMap {

	final private HashMap<String, String> map = new HashMap<String, String>();

	public PlaceholderMap() {

	}

	public String get(final String key) {
		return map.get(key);
	}

	public void put(final String key, final String value) {
		map.put(key, value);
	}

	public Set<String> keySet() {
		return map.keySet();
	}

}