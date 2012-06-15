package com.epic.core.xml;

import java.util.HashMap;
import java.util.Map;

/**
 * Dictionary Model.
 * @author hymer
 *
 */
public class Dictionary {
	private String code; // code
	private String name; // May be Chinese.
	private Map<String, String> items = new HashMap<String, String>();

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getItems() {
		return items;
	}

	public void setItems(Map<String, String> items) {
		this.items = items;
	}

	public void put(String key, String value) {
		this.items.put(key, value);
	}

	public String get(String key) {
		return this.items.get(key);
	}

}
