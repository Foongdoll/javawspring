package com.spring.javawspring.interceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class OtherInterceptor extends HandlerInterceptorAdapter{

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		int level = request.getSession().getAttribute("sLevel") == null ? 99 : (int)request.getSession().getAttribute("sLevel"); 
		int sw = request.getParameter("sw") == null ? 0 : Integer.parseInt(request.getParameter("sw"));
		
		if(level > 3) {
			String viewPage = "";
			if(level == 99) viewPage = "/msg/memberNo";
			else if(level == 4) {
				if(sw == 1) viewPage = "/msg/memberLevel4No";
				else viewPage = "/msg/levelCheck";
			}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
			return false;
		}
		
		
		return true;
	}
	
}
