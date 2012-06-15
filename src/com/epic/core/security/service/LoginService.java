package com.epic.core.security.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epic.core.CommonService;
import com.epic.core.security.MD5Utils;
import com.epic.core.security.SessionContext;
import com.epic.core.security.dao.UserDAO;
import com.epic.core.security.entity.Authority;
import com.epic.core.security.entity.Resource;
import com.epic.core.security.entity.User;

@Service
public class LoginService extends CommonService {

	@Autowired
	private UserDAO userDAO;
	
	public User login(String userName, String password, HttpSession session) {
		User user =  null;
		List<User> users = userDAO.getByProperty("userName", userName);
		if ( ! users.isEmpty()) {
			User temp = users.get(0);
			if (temp.isDisabled()) {
				throw new RuntimeException("该用户账号已停用。");
			} else if (temp.getRole().isDisabled()) {
				throw new RuntimeException("该角色已停用。");
			}
			String encodePassword = MD5Utils.encode(password);
			if (encodePassword.equals(temp.getPassword())) {
				user = users.get(0);
				//在此,将角色/权限/资源都取出来，防止filter中出懒加载错。
				Set<Authority> authorities = user.getRole().getAuthorities();
				Set<String> userAuthorizedUrls = new HashSet<String>();
				for (Authority authority : authorities) {
					for (Resource resource : authority.getResources()) {
						userAuthorizedUrls.add(resource.getUrl());
					}
				}
				session.setAttribute("user", user);
				session.setAttribute("role_code", user.getRole().getCode());
				session.setAttribute("authorized_resources", userAuthorizedUrls);
				SessionContext.addSession(session);
			}
		}
		return user;
	}
	
}
