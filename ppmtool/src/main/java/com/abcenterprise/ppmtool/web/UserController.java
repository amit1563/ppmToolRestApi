package com.abcenterprise.ppmtool.web;

import static com.abcenterprise.ppmtool.security.SecurityConstants.TOKEN_PREFIX;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abcenterprise.ppmtool.domain.user.User;
import com.abcenterprise.ppmtool.exception.userexception.UserExistExceptio;
import com.abcenterprise.ppmtool.payload.JWTLoginSuccessResponse;
import com.abcenterprise.ppmtool.payload.UserLoginRequest;
import com.abcenterprise.ppmtool.restexception.DefaultRestException;
import com.abcenterprise.ppmtool.security.JwtTokenProvider;
import com.abcenterprise.ppmtool.services.UserService;
import com.abcenterprise.ppmtool.validationerrorhandler.FieldErrorHandler;
import com.abcenterprise.ppmtool.validator.UserValidator;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	UserService service;
	@Autowired
	FieldErrorHandler fieldErrorHandler;
	@Autowired
	UserValidator userValidator;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JwtTokenProvider jwtTokenProvider;

	/*
	 * ##### Sequence of methods which is going to be called for authentication
	 * 
	 * UsernamePasswordAuthenticationToken extends AbstractAuthenticationToken
	 * AbstractAuthenticationToken implements Authentication --- reason we are
	 * passing UsernamePasswordAuthenticationToken to the
	 * authenticationManager.authenticate(---) => Authentication
	 * authenticate(Authentication authentication) throws AuthenticationException;
	 * 
	 * Authentication org.springframework
	 * .security.authentication.ProviderManager.authenticate(Authentication
	 * authentication) throws AuthenticationException
	 * 
	 * org.springframework.security.authentication.dao.
	 * AbstractUserDetailsAuthenticationProvider. authenticate(Authentication
	 * authentication) throws AuthenticationException
	 * 
	 * UserDetails
	 * org.springframework.security.authentication.dao.DaoAuthenticationProvider.
	 * retrieveUser(String username, UsernamePasswordAuthenticationToken
	 * authentication) throws AuthenticationException
	 * 
	 */

	@PostMapping("/login")
	ResponseEntity<?> login(@Valid @RequestBody UserLoginRequest userLoginRequest, BindingResult result) {

		ResponseEntity<?> errorMap = fieldErrorHandler.mapValidationError(result);
		Authentication authentication;
		String token = null;

		if (errorMap != null) {
			return errorMap;
		}
		try {
			authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					userLoginRequest.getUsername(), userLoginRequest.getPassword()));
		} catch (Exception ex) {
			throw new DefaultRestException("Invalid Credentials please try again !!");
		}

		if (authentication != null)
			token = TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);

		return new ResponseEntity<JWTLoginSuccessResponse>(new JWTLoginSuccessResponse(true, token), HttpStatus.OK);
	}

	@RequestMapping(value = "/register")
	public ResponseEntity<?> signup(@Valid @RequestBody User user, BindingResult result) {
		ResponseEntity<?> errorMap = fieldErrorHandler.mapValidationError(result);
		userValidator.validate(user);
		User userObj;
		if (errorMap != null) {
			return errorMap;
		}
		if (!userValidator.getValidationErrorMap().isEmpty()) {
			return new ResponseEntity<Map<String, String>>(userValidator.getValidationErrorMap(),
					HttpStatus.BAD_REQUEST);
		}

		try {
			userObj = service.save(user);

		} catch (UserExistExceptio userExistException) {
			throw new DefaultRestException(userExistException.getMessage());
		}
		return new ResponseEntity<User>(userObj, HttpStatus.CREATED);
	}
}
