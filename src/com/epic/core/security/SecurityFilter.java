package com.epic.core.security;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.epic.core.security.entity.Role;
import com.epic.core.security.entity.User;
import com.epic.core.security.service.ResourceService;

@Repository("securityFilter")
public class SecurityFilter implements Filter {
	Log log = LogFactory.getLog(getClass());

	@Autowired
	private ResourceService resourceService;

	// private static String LoginAction = "/j_security_check.do";
	// private static final String NO_SECURITY_PARAM_NAME = "security-resource";
	// private static final String LOGIN_ACTION_NAME = "LoginAction";

	public SecurityFilter() {
		super();
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		String uri = request.getRequestURI();
		String ctx = request.getContextPath();
		uri = uri.substring(ctx.length());
		System.out.println("uri=" + uri);
		boolean isAuthorized = false;
		boolean isNeedAuthorized = false;
		boolean isNotLogin = true;
		String currentResource = null;
		Pattern pattern = null;
		try {
			for (String resource : ResourceService.PROTECTED_RESOURCES) {
				try {
					pattern = Pattern.compile(resource);
					Matcher matcher = pattern.matcher(uri);
					if (matcher.matches()) {
						// then , need authorized.
						isNeedAuthorized = true;
						currentResource = resource;
						break;
					}
				} catch (PatternSyntaxException e) {
					log.error("资源路径正则表达式非法，请仔细检查。resource=" + resource, e);
					continue;
				}
			}

			if (isNeedAuthorized) {
				HttpSession session = request.getSession();
				String sessionId = session.getId();
				HttpSession userSession = SessionContext.getSession(sessionId);
				if (userSession == null) {
					isAuthorized = false;
				} else {
					User user = (User) userSession.getAttribute("user");
					String roleCode = (String) userSession
							.getAttribute("role_code");
					@SuppressWarnings("unchecked")
					Set<String> userAuthorizedUrls = (HashSet<String>) userSession
							.getAttribute("authorized_resources");
					if (user == null) {
						isAuthorized = false;
					} else {
						isNotLogin = false;
					}
					if (Role.SUPER_ROLE_FLAG.equals(roleCode)) {
						isAuthorized = true;
					} else {
						for (String auth : userAuthorizedUrls) {
							if (auth.equals(currentResource)) {
								isAuthorized = true;
								System.out.println("is authorized");
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// catch any exception, authorized failed.
			log.error("权限过滤出现异常，请仔细检查。", e);
			e.printStackTrace();
		}

		// do not need authorized or is authorized success, then let it pass.
		if ((!isNeedAuthorized) || isAuthorized) {
			chain.doFilter(request, response);
		} else {
			if (isNotLogin) {
				System.out.println("is not login.");
				((HttpServletResponse) response).sendRedirect(ctx
						+ "/login.html");
			} else {
				System.out.println("is not authorized");
				((HttpServletResponse) response).sendRedirect(ctx
						+ "/decline.html");
			}
			// PrintWriter out = response.getWriter();
			// out.print("is not authorized");
			return;
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		resourceService.refreshProtectedResources();
	}

}
