package com.abcenterprise.ppmtool.serviceimpl;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.abcenterprise.ppmtool.daoservice.UserDaoService;
import com.abcenterprise.ppmtool.domain.user.User;
import com.abcenterprise.ppmtool.exception.userexception.UserExistExceptio;
import com.abcenterprise.ppmtool.exception.userexception.UserNotFoundExcetion;
import com.abcenterprise.ppmtool.restexception.InvalidCredentialException;
import com.abcenterprise.ppmtool.restexception.ResourceNotFound;
import com.abcenterprise.ppmtool.services.UserService;

@Component
@Service
public class UserServiceImpl implements UserService ,UserDetailsService {

	@Autowired
	UserDaoService daoService;

	@Override
	public User findUserByUserName(String userName) {

		try {
			return daoService.findUserByUserName(userName);
		} catch (Exception e) {
			throw new ResourceNotFound("User details does not exists");
		}
	}

	@Override
	public User save(@Valid User user) throws UserExistExceptio, UserNotFoundExcetion {
		User userObj = null;
		try {
			userObj = findUserByUserName(user.getUsername());
			throw new UserExistExceptio("username is not avilable, please choose another");

		} catch (ResourceNotFound unfe) {
			daoService.save(user);
		}
		return findUserByUserName(user.getUsername());
	}

	@Override
	public boolean findByLogin(String username, String password)
			throws UserNotFoundExcetion, InvalidCredentialException {

		User userObj = daoService.findUserByUserName(username);
		if (userObj == null) {
			throw new UserNotFoundExcetion("user dont exist !!");
		}
		if (userObj.getPassword().equals(password)) {
			return true;
		}

		return false;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails details= findUserByUserName(username);
		if(details == null) throw new UsernameNotFoundException("Username is not avilable, please choose another");
		return findUserByUserName(username);
	}

	@Override
	public User getByUserId(Long id) throws UserNotFoundExcetion {
		return daoService.getByUserId(id);
	}

}
