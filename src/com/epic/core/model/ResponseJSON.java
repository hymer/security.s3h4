package com.epic.core.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Ajax请求返回的JSON数据标准格式
 * @author hymer
 *
 */
public class ResponseJSON {

	private boolean result = true;
	private String msg;
	private Map<String, Object> data = new HashMap<String, Object>();

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	public void put(String key, Object value) {
		this.data.put(key, value);
	}
	
		
}
