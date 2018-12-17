package com.abcenterprise.ppmtool.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.abcenterprise.ppmtool.security.responsehandler.invlaidresponsehandler.InvalidLoginResponse;
import com.google.gson.Gson;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, 
			AuthenticationException authenticationException)
			throws IOException, ServletException {
		 InvalidLoginResponse invalidLoginResponse = new InvalidLoginResponse();
		 
		String jsonReponseString = new Gson().toJson(invalidLoginResponse);
		
		httpServletResponse.setContentType("application/json");
		httpServletResponse.setStatus(401);
		httpServletResponse.getWriter().print(jsonReponseString);
		
		
	}

}
