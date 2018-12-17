package com.abcenterprise.ppmtool.security;

import static com.abcenterprise.ppmtool.security.SecurityConstants.HEADER_STRING;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.abcenterprise.ppmtool.domain.user.User;
import com.abcenterprise.ppmtool.serviceimpl.UserServiceImpl;;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	JwtTokenProvider jwtTokenProvider;
	@Autowired
	UserServiceImpl userServiceImpl;

	/**
	 * --- Only abstract method of OncePerRequestFilter actual definition from super
	 * abstract implementation Same contract as for {@code doFilter}, but guaranteed
	 * to be just invoked once per request within a single request thread. See
	 * {@link #shouldNotFilterAsyncDispatch()} for details.
	 * <p>
	 * Provides HttpServletRequest and HttpServletResponse arguments instead of the
	 * default ServletRequest and ServletResponse ones.
	 * 
	 * basiclly security context holder will hold the authenticated user details
	 * after successfull execution of this filter method
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			String token = getJwtFromReuest(request);
			boolean tokenValidation = jwtTokenProvider.validate(token);
			long id = jwtTokenProvider.getUserIdFromJwt(token);
			User userDetails = userServiceImpl.getByUserId(id);

			if (StringUtils.hasText(token) && tokenValidation) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, Collections.emptyList());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception ex) {
			logger.error("Could not set user authentication in security context", ex);
		}
		filterChain.doFilter(request, response);
	}

	private String getJwtFromReuest(HttpServletRequest request) {
		String bearerToken = request.getHeader(HEADER_STRING);

		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(bearerToken)) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}

}
