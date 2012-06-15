package com.epic.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class CommonExceptionHandler implements HandlerExceptionResolver {

	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object arg2, Exception exception) {
		ModelAndView failModelAndView = new ModelAndView(
				BaseContoller.COMMON_FAIL_PAGE);
		if (exception instanceof MessageAlertable) {
			failModelAndView.addObject(BaseContoller.COMMON_FAIL_ALERT_KEY,
					exception.getMessage());
		} else {
			failModelAndView.addObject(BaseContoller.COMMON_FAIL_ALERT_KEY,
					"系统内部错误，请联系系统管理员");
		}
		return failModelAndView;
	}

}
