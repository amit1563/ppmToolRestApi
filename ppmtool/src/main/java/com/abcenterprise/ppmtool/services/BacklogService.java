package com.abcenterprise.ppmtool.services;

import com.abcenterprise.ppmtool.domain.ProjectTask;

public interface BacklogService {

	void addProjectTask(String projectIdentifier, ProjectTask projectask, String username);

	Iterable<ProjectTask> findBacklogById(String projectIdentifier, String username);

	ProjectTask findByProjectSequence(String backlog_id, String projectSequence, String username);

	ProjectTask updateProjectTaskByProjectSequence(ProjectTask projectTask, String backlog_id, String projectSequence, String username);

	void deleteProjectTaskByProjectSequence(String backlog_id, String projectSequence, String username);

}
