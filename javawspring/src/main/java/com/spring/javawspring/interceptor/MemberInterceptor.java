package com.spring.javawspring.interceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MemberInterceptor extends HandlerInterceptorAdapter{

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		int level = request.getSession().getAttribute("sLevel") == null ? 99 : (int)request.getSession().getAttribute("sLevel"); 
		
		if(level > 4) {
			String viewPage = "";
			if(level == 99) viewPage = "/msg/memberNo";
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
			return false;
		}
		
		
		return true;
	}
	
}
