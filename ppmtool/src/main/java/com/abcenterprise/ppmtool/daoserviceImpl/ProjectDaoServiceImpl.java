package com.abcenterprise.ppmtool.daoserviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abcenterprise.ppmtool.daoservice.ProjectDaoService;
import com.abcenterprise.ppmtool.domain.Backlog;
import com.abcenterprise.ppmtool.domain.Project;
import com.abcenterprise.ppmtool.exception.ProjectNotFoundException;
import com.abcenterprise.ppmtool.repositories.ProjectRepository;

@Component
public class ProjectDaoServiceImpl implements ProjectDaoService {

	@Autowired
	ProjectRepository projectRepo;

	@Override
	public boolean findProjectByProjectName(String projectName) throws ProjectNotFoundException {

		if (projectRepo.findByProjectName(projectName) == null)
			return false;
		return true;
	}

	@Override
	public boolean findProjectByProjectIdentifierName(String projectIdentifierName, String username) {
		 projectIdentifierName = projectIdentifierName.toUpperCase();
		if (projectRepo.findByProjectByIdentifierName(projectIdentifierName) == null)
			return false;
		String projectLeader = projectRepo.findByProjectByIdentifierName(projectIdentifierName).getProjectLeader();
		
		if(projectLeader != null && !projectLeader.equals(username)) {
			throw new ProjectNotFoundException("Project does not exist in this account for username" +username);
		}	
		return true;
	}

	@Override
	public Project findProjectByProjectId(String id) {
		Project project = projectRepo.findByProjectByIdentifierName(id);
		if (project != null)
			return project;
		return project;
	}

	@Override
	public void save(Project project , String username) {
		boolean projectExist = findProjectByProjectIdentifierName(project.getProjectIdentifier(), username);
		if (projectExist) {
			throw new RuntimeException();
		} else {
			Backlog backlog = new Backlog();
			project.setBacklog(backlog);
			backlog.setProject(project);
			backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			projectRepo.save(project);
		}
	}

	@Override
	public Iterable<?> findAll(String username) {
		Iterable<Project> itrable = projectRepo.findAll();
		List<Project> projectsList = new ArrayList<>();
		itrable.forEach(new Consumer<Project>() {

			@Override
			public void accept(Project project) {
				if(project.getProjectLeader().equals(username)) {
					projectsList.add(project);
				}
			}		
		});
		return projectsList;
	}

	@Override
	public void deleteProjectByProjectIdentifierName(String projectIdentifier, String username ) throws ProjectNotFoundException {
		//boolean flag = projectRepo.findByProjectByIdentifierName(projectIdentifier) != null;
		boolean flag = findProjectByProjectIdentifierName(projectIdentifier,username);
		if (!flag) {
			throw new ProjectNotFoundException("Project with given identifier name not exist.");
		}
		projectRepo.delete(projectRepo.findByProjectByIdentifierName(projectIdentifier.toUpperCase()));
	}

	@Override
	public boolean updateProject(Project project, String username) {
		boolean flag = findProjectByProjectIdentifierName(project.getProjectIdentifier(), username);
		Project projectTemp = projectRepo.findByProjectByIdentifierName(project.getProjectIdentifier().toUpperCase());
		//boolean flag = projectTemp != null;
		if (!flag) {
			throw new ProjectNotFoundException("Can't Update : Project with given identifier name : "
					+ project.getProjectIdentifier() + " not exist.");
		}
		project.setId(projectTemp.getId());		
		projectRepo.save(project);
		return true;
	}
}
