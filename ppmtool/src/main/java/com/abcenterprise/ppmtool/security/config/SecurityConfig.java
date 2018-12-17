package com.abcenterprise.ppmtool.security.config;

import static com.abcenterprise.ppmtool.security.SecurityConstants.SIGN_UP_URLS;
import static com.abcenterprise.ppmtool.security.SecurityConstants.H2_URL;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.abcenterprise.ppmtool.security.JwtAuthenticationEntryPoint;
import com.abcenterprise.ppmtool.security.JwtAuthenticationFilter;
import com.abcenterprise.ppmtool.serviceimpl.UserServiceImpl;;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		securedEnabled = true,
		prePostEnabled = true,
		jsr250Enabled = true
		)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	//JwtAuthenticationFilter bean which will activate the filter and will set security context for the user
	@Bean
	public JwtAuthenticationFilter JwtAuthenticationFilter () {
		return new JwtAuthenticationFilter();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userServiceImpl).passwordEncoder(bCryptPasswordEncoder);
		
	}
	@Override
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
           
	 @Override
	protected void configure(HttpSecurity http) throws Exception {
		 
		http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authorizeRequests()
		.antMatchers(
				"/",
                "/favicon.ico",
                "/**/*.png",
                "/**/*.gif",
                "/**/*.svg",
                "/**/*.jpg",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js"
				).permitAll()
		       .antMatchers(SIGN_UP_URLS).permitAll()
		       .antMatchers(H2_URL).permitAll()
		       .anyRequest().authenticated();
		http.addFilterBefore(JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		
	}
}
