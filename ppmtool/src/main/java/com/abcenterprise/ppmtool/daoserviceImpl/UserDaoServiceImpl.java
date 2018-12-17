package com.abcenterprise.ppmtool.daoserviceImpl;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.abcenterprise.ppmtool.daoservice.UserDaoService;
import com.abcenterprise.ppmtool.domain.user.User;
import com.abcenterprise.ppmtool.exception.userexception.UserExistExceptio;
import com.abcenterprise.ppmtool.exception.userexception.UserNotFoundExcetion;
import com.abcenterprise.ppmtool.repositories.UserRepository;

@Component
public class UserDaoServiceImpl implements UserDaoService {

	@Autowired
	private UserRepository repository;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public User findUserByUserName(String username) throws UserNotFoundExcetion {

		User user = repository.findByUsername(username);
		if (user == null) {
			throw new UserNotFoundExcetion("User Not Found Exception");
		} else {
			return user;
		}
	}

	public User save(@Valid User user) throws UserExistExceptio, UserNotFoundExcetion {
		User userObj = null;
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setConfirmPassword("");
		try {
			userObj = findUserByUserName(user.getUsername());
			throw new UserExistExceptio("Username is not avilable, please choose another");
		} catch (UserNotFoundExcetion unfe) {
			repository.save(user);
		}
		return userObj;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abcenterprise.ppmtool.daoservice.UserDaoService#getByUserId(java.lang.
	 * Long)
	 * 
	 * @Param - Long userId return User details object
	 */
	@Override
	public User getByUserId(Long userId) {

		User userDetails = repository.getById(userId);
		if (userDetails == null) {
			throw new UsernameNotFoundException("User Not Found for given userId :" + userId);
		}
		return repository.getById(userId);

	}

}
