package com.abcenterprise.ppmtool.services;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.abcenterprise.ppmtool.domain.user.User;
import com.abcenterprise.ppmtool.exception.userexception.UserExistExceptio;
import com.abcenterprise.ppmtool.exception.userexception.UserNotFoundExcetion;
import com.abcenterprise.ppmtool.restexception.InvalidCredentialException;

@Service
public interface UserService {

	User findUserByUserName(String userName);
	// User findUserByUserId(Long userId) throws UserNotFoundExcetion;

	User save(@Valid User user) throws UserExistExceptio, UserNotFoundExcetion;

	boolean findByLogin(String userName, String password) throws UserNotFoundExcetion, InvalidCredentialException;

	User getByUserId(Long id) throws UserNotFoundExcetion;
}
