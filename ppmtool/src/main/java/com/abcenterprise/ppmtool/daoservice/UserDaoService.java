package com.abcenterprise.ppmtool.daoservice;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.abcenterprise.ppmtool.domain.user.User;
import com.abcenterprise.ppmtool.exception.userexception.UserExistExceptio;
import com.abcenterprise.ppmtool.exception.userexception.UserNotFoundExcetion;
@Service
public interface UserDaoService {
	
	User findUserByUserName(String username) throws UserNotFoundExcetion;
	User save(@Valid User user) throws UserExistExceptio, UserNotFoundExcetion;
	User getByUserId (Long id) throws UserNotFoundExcetion; 
}
