package com.epic.core.security;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.epic.core.security.entity.User;

/**
 * 认证令牌
 * 
 * @author hymer
 * 
 */
public final class AuthenticationToken implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private long invalidLimit;
	private Map<String, Object> data = new HashMap<String, Object>();

	public AuthenticationToken(String sessionId, User user) {
		this.id = sessionId;
		this.invalidLimit = System.currentTimeMillis() + 30 * 60 * 1000;
		this.putData("role_code", user.getRole().getCode());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getInvalidLimit() {
		return invalidLimit;
	}

	public void setInvalidLimit(long invalidLimit) {
		this.invalidLimit = invalidLimit;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void putData(String key, Object value) {
		this.data.put(key, value);
	}

	public Object get(String key) {
		return this.data.get(key);
	}

}
