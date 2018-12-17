package com.abcenterprise.ppmtool.daoservice;

import org.springframework.stereotype.Service;

import com.abcenterprise.ppmtool.domain.Project;
import com.abcenterprise.ppmtool.exception.ProjectNotFoundException;
@Service
public interface ProjectDaoService {
	
	boolean findProjectByProjectName(String userName) throws ProjectNotFoundException;
	Project findProjectByProjectId(String id) throws ProjectNotFoundException;
	
	void save (Project project, String username);
	boolean findProjectByProjectIdentifierName(String projectIdentifier, String username) throws ProjectNotFoundException;
	
	Iterable<?> findAll(String username);
	
	void deleteProjectByProjectIdentifierName(String projectIdentifier,String username) throws ProjectNotFoundException;
	
	boolean updateProject(Project project,String username);

	
	
}
