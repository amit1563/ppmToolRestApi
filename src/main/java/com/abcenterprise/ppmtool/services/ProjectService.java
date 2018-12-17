package com.abcenterprise.ppmtool.services;

import org.springframework.stereotype.Service;

import com.abcenterprise.ppmtool.domain.Project;
import com.abcenterprise.ppmtool.exception.ProjectNotFoundException;

@Service
public interface ProjectService {

	public void saveOrUpdateProject(Project project, String username);

	public Project findByProjectIdentifier(String projectIdentifier, String username) throws ProjectNotFoundException;

	public Iterable findAllProjects(String username);

	public void deleteProject(String projectIdentifierName, String username);

	public boolean updateProject(Project project, String username);

}
