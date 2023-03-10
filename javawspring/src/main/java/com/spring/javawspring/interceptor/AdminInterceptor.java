package com.spring.javawspring.interceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AdminInterceptor extends HandlerInterceptorAdapter {

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		int level = request.getSession().getAttribute("sLevel") == null ? 99 : (int)request.getSession().getAttribute("sLevel");
		
		if(level != 0) { // 관리자가 아닌경우는 메세지를 통해서 무조건 초기화면창으로 보내준다.
			RequestDispatcher dispatcher = request.getRequestDispatcher("/msg/adminNo");
			dispatcher.forward(request, response);
			return false;
		}
		
		
		return true;
	}

	
}
