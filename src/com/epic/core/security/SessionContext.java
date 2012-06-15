package com.epic.core.security;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

/**
 * My Session Context.
 * @author hymer
 *
 */
public class SessionContext {

	private static final Map<String, HttpSession> sessions = new HashMap<String, HttpSession>();

	public synchronized static void addSession(HttpSession session) {
		if (session != null) {
			sessions.put(session.getId(), session);
		}
	}

	public synchronized static HttpSession getSession(String sessionId) {
		if (sessionId != null) {
			return sessions.get(sessionId);
		} else {
			return null;
		}
	}

	public synchronized static void deleteSession(HttpSession session) {
		if (session != null) {
			sessions.remove(session.getId());
		}
	}

}
