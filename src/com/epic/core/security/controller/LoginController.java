package com.epic.core.security.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.epic.core.BaseContoller;
import com.epic.core.ServiceException;
import com.epic.core.security.entity.User;
import com.epic.core.security.service.LoginService;

@Controller
public class LoginController extends BaseContoller {

	@Autowired
	private LoginService loginService;

	@RequestMapping(value = "/j_security_check.do", method = RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request,
			HttpServletResponse response) throws ServiceException {
		ModelAndView modelAndView = new ModelAndView("redirect:/");
		try {
			// get client cookies, implements the function "remember me".
			boolean isRemembered = false;
			String userName = null;
			String password = null;
//			Cookie[] cookies = request.getCookies();
//			for (Cookie cookie : cookies) {
//				System.out.println("cookie:" + cookie.getName() + ",maxAge:"
//						+ cookie.getMaxAge());
//				if (cookie.getName().equals("j_username")
//						&& cookie.getMaxAge() > 0) {
//					isRemembered = true;
//					userName = cookie.getValue();
//					System.out.println("userName:" + userName);
//					break;
//				}
//			}
			if (isRemembered) {

			} else {
				userName = request.getParameter("j_username");
				password = request.getParameter("j_password");
			}
			HttpSession session = request.getSession();
			User user = loginService.login(userName, password, session);
			if (user != null) {
//				String rememberMeString = request.getParameter("remember_me");
//				System.out.println("remember_me=" + rememberMeString);
//				if ("on".equals(rememberMeString)) {
//					Cookie cook = new Cookie("j_username", userName);
//					cook.setMaxAge(3600); // one hour
//					// cook.setMaxAge(30); // 30 seconds
//					response.addCookie(cook);
//				}

			} else {
				throw new RuntimeException("用户名或密码错误!");
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return modelAndView;
	}

	@RequestMapping(value = "/j_security_logout.do", method = RequestMethod.GET)
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			session.invalidate();
			response.sendRedirect(request.getContextPath() + "/login.html");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
