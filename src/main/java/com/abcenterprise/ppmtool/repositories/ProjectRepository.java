package com.abcenterprise.ppmtool.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abcenterprise.ppmtool.domain.Project;
import com.abcenterprise.ppmtool.exception.ProjectNotFoundException;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

	@Override
	 Iterable<Project> findAllById(Iterable<Long> iterable) ;
	
	Project findByProjectName(String projectNmae) throws ProjectNotFoundException;
	@Query("select p from Project p where p.projectIdentifier = :projectIdentifier")
	Project findByProjectByIdentifierName(@Param (value = "projectIdentifier") String projectIdentifier);
	
	/*@Query("select u from User u where u.userId = :userId")
	Project findByUserId(@Param(value = "userId") Long userId) throws ProjectNotFoundExcetion;*/
	
}
