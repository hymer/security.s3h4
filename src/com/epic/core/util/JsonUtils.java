package com.epic.core.util;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * JSON操作
 * @author hymer
 *
 */
public class JsonUtils {

	private static final ObjectMapper mapper = new ObjectMapper();

	public static <T> T fromJson(String jsonString, Class<T> type) throws JsonParseException, JsonMappingException, IOException {
		return mapper.readValue(jsonString, type);
	}
	
	public static String toJson(Object object) throws JsonGenerationException, JsonMappingException, IOException {
		return mapper.writeValueAsString(object);
	}

}
