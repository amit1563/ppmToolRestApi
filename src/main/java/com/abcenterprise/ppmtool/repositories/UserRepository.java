package com.abcenterprise.ppmtool.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.abcenterprise.ppmtool.domain.user.User;

@Repository()
public interface UserRepository extends CrudRepository<User, Long>{
	
	/*@Query("select u from users u where u.userName = :userName")
	User findByUsername(@Param("userName")String username);*/
	
	User findByUsername(String username);
	
	//@Query("select u from user u where u.id = :id")
	//User findByUserId(//@Param(value = "id") Long id);
	User getById(Long userId);
}
