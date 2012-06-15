package com.epic.core.xml;

import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author hymer
 * 
 */
public class DictionaryHandler extends DefaultHandler {
	private List<Dictionary> dicts = null;
	private Dictionary dict = null;
	private String tagName = null;
	private static final String DICT_TAGNAME = "dict";
	private static final String ITEM_TAGNAME = "item";
	private String key = null;
	private String value = null;

	public DictionaryHandler(List<Dictionary> dicts) {
		this.dicts = dicts;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String temp = new String(ch, start, length);
		// System.out.println("characters");
		// System.out.println("tagName=" + this.tagName);
		// System.out.println("temp=" + temp);
		// System.out.println("------------------\n\n");

		if ("code".equals(tagName)) {
			dict.setCode(temp);
		} else if ("name".equals(tagName)) {
			dict.setName(temp);
		} else if ("key".equals(tagName)) {
			key = temp;
		} else if ("value".equals(tagName)) {
			value = temp;
		}
	}

	@Override
	public void endDocument() throws SAXException {

	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
//		System.out.println("endElement");
//		System.out.println("uri=" + uri);
//		System.out.println("localName=" + localName);
//		System.out.println("qName=" + qName);
//		System.out.println("----------\n\n");
	
		if (ITEM_TAGNAME.equals(qName)) {
			this.dict.put(key, value);
			key = null;
			value = null;
		} else if (DICT_TAGNAME.equals(qName)) {
			this.dicts.add(dict);
		}
		this.tagName = "";
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// System.out.println("startElement");
		// System.out.println("uri=" + uri);
		// System.out.println("localName=" + localName);
		// System.out.println("qName=" + qName);
		// System.out.println("----------\n\n");
		tagName = qName;
		if (DICT_TAGNAME.equals(tagName)) {
			dict = new Dictionary();
		}
	}

}
