package com.abcenterprise.ppmtool.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abcenterprise.ppmtool.daoservice.BacklogDaoService;
import com.abcenterprise.ppmtool.domain.ProjectTask;
import com.abcenterprise.ppmtool.services.BacklogService;

@Service
public class BacklogServiceImpl implements BacklogService {

	@Autowired
	BacklogDaoService backlogDaoService;

	@Override
	public void addProjectTask(String projectIdentifier, ProjectTask projectask, String username) {

		backlogDaoService.addProjectTask(projectIdentifier, projectask, username);

	}

	@Override
	public Iterable<ProjectTask> findBacklogById(String projectIdentifier,  String username) {

		return backlogDaoService.findBacklogById(projectIdentifier, username);
	}

	@Override
	public ProjectTask findByProjectSequence(String backlog_id, String projectSequence,String username) {
		return backlogDaoService.findByProjectSequence(backlog_id, projectSequence, username);
	}

	@Override
	public ProjectTask updateProjectTaskByProjectSequence(ProjectTask projectTask, String backlog_id,
			String projectSequence, String username) {
		return backlogDaoService.updateProjectTaskByProjectSequence(projectTask, backlog_id, projectSequence, username);
	}

	@Override
	public void deleteProjectTaskByProjectSequence(String backlog_id, String projectSequence, String username) {
		backlogDaoService.deleteProjectTaskByProjectSequence(backlog_id, projectSequence, username);

	}

}
