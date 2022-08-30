package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class AuthTokenFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = ((HttpServletRequest) (request));

		String url = req.getRequestURL().toString();

		if (url.contains("/public/") || req.getMethod().toLowerCase().equals("options")) {
			chain.doFilter(request, response);
		} else {
			String authToken = req.getHeader("authToken");
			if (authToken == null || authToken.trim().length() != 16) {

				HttpServletResponse res = ((HttpServletResponse) response);
				response.setContentType("application/json");
				res.setStatus(404);
				response.getWriter().write("{'msg':'Please Login before access service'}");
			} else {

				System.out.println("user verfied....");
				chain.doFilter(request, response);
			}
		}
	}

}