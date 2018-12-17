package com.abcenterprise.ppmtool.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abcenterprise.ppmtool.daoservice.ProjectDaoService;
import com.abcenterprise.ppmtool.daoservice.UserDaoService;
import com.abcenterprise.ppmtool.domain.Project;
import com.abcenterprise.ppmtool.domain.user.User;
import com.abcenterprise.ppmtool.exception.ProjectIdException;
import com.abcenterprise.ppmtool.exception.ProjectNotFoundException;
import com.abcenterprise.ppmtool.services.ProjectService;

@Component
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	ProjectDaoService daoService;
	@Autowired
	UserDaoService userDaoService;

	public void saveOrUpdateProject(Project project, String username) {
		try {
			String projectIdentifier = project.getProjectIdentifier().toUpperCase();
			project.setProjectIdentifier(projectIdentifier);
			// get user object by providing username provide by Principle object of spring
			// security and set the project state accordingly
			User user = userDaoService.findUserByUserName(username);
			project.setUser(user);
			project.setProjectLeader(user.getUsername());
			daoService.save(project, username);
		} 
		catch(ProjectNotFoundException pnfe) {
			throw new ProjectIdException(pnfe.getMessage());
		}
		catch (Exception e) {
			throw new ProjectIdException("Project id : " + project.getProjectIdentifier() + " alredy exist!");
		}
	}

	@Override
	public Project findByProjectIdentifier(String projectIdentifier, String username) throws ProjectNotFoundException {
		boolean flag = daoService.findProjectByProjectIdentifierName(projectIdentifier, username);
		if (flag)
			return daoService.findProjectByProjectId(projectIdentifier);
		else
			throw new ProjectNotFoundException("Project " + projectIdentifier + "is not present");
	}

	@Override
	public Iterable findAllProjects(String username) {
		return daoService.findAll(username);
	}

	@Override
	public void deleteProject(String projectIdentifierName,String username) {
		try {
			daoService.deleteProjectByProjectIdentifierName(projectIdentifierName, username);
		}
		catch(ProjectNotFoundException pnfe) {
			throw new ProjectIdException(pnfe.getMessage());
		}
	}

	@Override
	public boolean updateProject(Project project,String username) {
		try {
			User user = userDaoService.findUserByUserName(username);
			project.setProjectLeader(user.getUsername());
			daoService.updateProject(project, username);
		}catch(ProjectNotFoundException pnfe) {
			throw new ProjectIdException(pnfe.getMessage());
		}
		return true;
	}
}
