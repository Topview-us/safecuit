package com.gdut.safecuit.device.web.filter;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Garson in 14:16 2018/1/12
 * Description : 跨域过滤器
 */
@Order(1) //越小顺序越前
@WebFilter(filterName = "crossDomainFilter",urlPatterns = "/*")
public class CrossDomainFilter implements Filter{
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("cross domain filter start");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		//如果发出的方法为OPTIONS且包含“origin”请求头，则为预检验（Preflight）请求，跨域请求
		/*if(request.getMethod().equalsIgnoreCase("OPTIONS")){
			response.addHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));//接受request.getHeader("Origin")网站发出请求
			response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
			response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
			response.addHeader("Access-Control-Max-Age", "3600");//1 hour
			response.addHeader("Access-Control-Allow-Credentials", "true");
		}*/
		response.addHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));//接受request.getHeader("Origin")网站发出请求
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		response.addHeader("Access-Control-Max-Age", "3600");//1 hour
		response.addHeader("Access-Control-Allow-Credentials", "true");

		filterChain.doFilter(request,response);
	}

	@Override
	public void destroy() {
		System.out.println("cross domain filter end");
	}
}
