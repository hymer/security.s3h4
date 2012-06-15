package com.epic.core.xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * @author hymer
 * 
 */
public class DictionaryUtils {
	private static final String DICTS_XML_PATH = "dicts.xml";
	private static List<Dictionary> DICTS = new ArrayList<Dictionary>();

	static {
		parseExports(DICTS_XML_PATH, DICTS);
	}

	/**
	 * Get Dictionary By Code.
	 * 
	 * @param code
	 * @return
	 */
	public static Dictionary getDictionary(String code) {
		for (Dictionary dictionary : DICTS) {
			if (dictionary.getCode().equals(code)) {
				return dictionary;
			}
		}
		return null;
	}

	/**
	 * @param xmlPath
	 * @param dicts
	 */
	private static void parseExports(String xmlPath, List<Dictionary> dicts) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			XMLReader reader = factory.newSAXParser().getXMLReader();
			DictionaryHandler handler = new DictionaryHandler(dicts);
			reader.setContentHandler(handler);
			reader.parse(new InputSource(DictionaryUtils.class.getClassLoader()
					.getResourceAsStream(xmlPath)));
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
